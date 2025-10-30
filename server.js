import fs from "fs";
import https from "https";
import express from "express";
import axios from "axios";
import dotenv from "dotenv";

dotenv.config();
const app = express();
app.use(express.json());

// --------------------
// Configuração do ambiente
// --------------------
const { CLIENT_ID, CLIENT_SECRET, PIX_KEY, CERT_PATH, ENVIRONMENT, WEBHOOK_URL } = process.env;

if (!CLIENT_ID || !CLIENT_SECRET || !PIX_KEY || !CERT_PATH || !WEBHOOK_URL) {
  console.error("❌ Variáveis faltando no .env");
  process.exit(1);
}

const isSandbox = ENVIRONMENT === "sandbox";
const BASE_URL = isSandbox
  ? "https://pix-h.api.efipay.com.br"
  : "https://pix.api.efipay.com.br";

const agent = new https.Agent({
  pfx: fs.readFileSync(CERT_PATH),
  rejectUnauthorized: false,
});

let cachedToken = null;
let tokenExpiresAt = 0;

// --------------------
// Funções auxiliares
// --------------------

// 🔐 Obter token OAuth2
async function getAccessToken() {
  const now = Date.now();
  if (cachedToken && now < tokenExpiresAt - 5000) return cachedToken;

  const auth = Buffer.from(`${CLIENT_ID}:${CLIENT_SECRET}`).toString("base64");
  const res = await axios.post(
    `${BASE_URL}/oauth/token`,
    { grant_type: "client_credentials" },
    { headers: { Authorization: `Basic ${auth}` }, httpsAgent: agent }
  );

  cachedToken = res.data.access_token;
  tokenExpiresAt = now + res.data.expires_in * 1000;
  return cachedToken;
}

// 🔗 Registrar Webhook Efipay
async function registerWebhook() {
  try {
    const token = await getAccessToken();

    const res = await axios.put(
      `${BASE_URL}/v2/webhook/${PIX_KEY}`,
      { webhookUrl: WEBHOOK_URL },
      { headers: { Authorization: `Bearer ${token}` }, httpsAgent: agent }
    );

    console.log("✅ Webhook registrado com sucesso:", res.data);
  } catch (err) {
    console.error("❌ Erro ao registrar webhook:", err.response?.data || err.message);
  }
}

// 💰 Criar Pix e gerar QR Code
async function criarPix(valor) {
  const token = await getAccessToken();

  const body = {
    calendario: { expiracao: 3600 },
    devedor: { nome: "Cliente Teste", cpf: "12345678909" },
    valor: { original: valor.toString() },
    chave: PIX_KEY,
    solicitacaoPagador: "Pagamento via App",
  };

  const cobranca = await axios.post(`${BASE_URL}/v2/cob`, body, {
    headers: { Authorization: `Bearer ${token}` },
    httpsAgent: agent,
  });

  console.log(`💰 Pix gerado: TXID ${cobranca.data.txid} | Valor: R$${valor}`);

  const idLoc = cobranca.data.loc.id;
  const qr = await axios.get(`${BASE_URL}/v2/loc/${idLoc}/qrcode`, {
    headers: { Authorization: `Bearer ${token}` },
    httpsAgent: agent,
  });

  // Inicia monitoramento automático caso webhook não chegue
  monitorarPix(cobranca.data.txid);

  // Armazena status inicial
  pixStatusMap[cobranca.data.txid] = { status: "ATIVA", valor: valor };

  return {
    txid: cobranca.data.txid,
    qrCode: qr.data.qrcode,
    imagemQrcode: qr.data.imagemQrcode,
  };
}

// 💬 Consultar status do Pix (TXID)
async function consultarPix(txid) {
  const token = await getAccessToken();
  const res = await axios.get(`${BASE_URL}/v2/cob/${txid}`, {
    headers: { Authorization: `Bearer ${token}` },
    httpsAgent: agent,
  });
  return res.data;
}

// --------------------
// Armazenamento simples em memória
// --------------------
const pixStatusMap = {}; // txid -> { status, valor }

// --------------------
// Polling automático caso webhook não chegue
// --------------------
function monitorarPix(txid, interval = 5000, timeout = 3600000) {
  const start = Date.now(); // marca início do monitoramento
  const timer = setInterval(async () => {
    if (Date.now() - start > timeout) {
      console.log(`⏰ Timeout: Pix ${txid} não recebeu pagamento em ${timeout / 1000}s`);
      clearInterval(timer);
      return;
    }

    try {
      const data = await consultarPix(txid);
      if (!pixStatusMap[txid] || pixStatusMap[txid].status !== data.status) {
        pixStatusMap[txid] = {
          status: data.status,
          valor: data.valor.original,
        };
        console.log(`🔄 Status atualizado via polling: ${txid} = ${data.status}`);
      }

      if (data.status === "CONCLUIDO") {
        console.log(`✅ Pix ${txid} foi pago (polling)`);
        clearInterval(timer);
      }
    } catch (err) {
      console.error(`❌ Erro ao consultar Pix ${txid}:`, err.message);
    }
  }, interval);
}

// --------------------
// Endpoints
// --------------------

// 🔁 Endpoint para gerar QR Code Pix
app.get("/pix/:valor", async (req, res) => {
  const valor = req.params.valor;
  try {
    const pixData = await criarPix(valor);
    res.json(pixData);
  } catch (err) {
    console.error("Erro ao gerar Pix:", err.response?.data || err.message);
    res.status(500).json({ error: "Erro ao gerar Pix" });
  }
});

// 🔍 Endpoint para consultar status Pix
app.get("/pix/status/:txid", async (req, res) => {
  const txid = req.params.txid;
  const data = pixStatusMap[txid] || { status: "ATIVA", valor: 0 };
  res.json({ txid, status: data.status, valor: data.valor });
});

// 📩 Webhook para receber notificações Efipay
app.post("/efipay/webhook", async (req, res) => {
  const pixList = req.body.pix || [];
  for (const pix of pixList) {
    console.log("📩 PIX RECEBIDO via webhook:", pix);
    try {
      const statusData = await consultarPix(pix.txid);
      pixStatusMap[pix.txid] = {
        status: statusData.status,
        valor: statusData.valor.original,
      };
      console.log(`✅ Status atualizado via webhook: ${pix.txid} = ${statusData.status}`);
    } catch (err) {
      console.error(`❌ Erro ao consultar Pix ${pix.txid}:`, err.message);
    }
  }
  res.status(200).json({ ok: true });
});

// 🧭 Endpoint de teste
app.get("/", (req, res) => res.json({ ok: true, msg: "Servidor Efí ativo" }));

// --------------------
// Inicialização
// --------------------
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`🚀 Servidor rodando na porta ${PORT}`);
  registerWebhook();
});
