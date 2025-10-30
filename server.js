import fs from "fs";
import https from "https";
import express from "express";
import axios from "axios";
import dotenv from "dotenv";
import bodyParser from "body-parser";

dotenv.config();
const app = express();
app.use(express.json());

// --------------------
// Configuração do ambiente
// --------------------
const {
  CLIENT_ID,
  CLIENT_SECRET,
  PIX_KEY,
  CERT_PATH,
  ENVIRONMENT,
  WEBHOOK_URL,
} = process.env;

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
    console.error(
      "❌ Erro ao registrar webhook:",
      err.response?.data || err.message
    );
  }
}

// 💰 Criar Pix e gerar QR Code
async function criarPix(valor) {
  const token = await getAccessToken();

  const body = {
    calendario: { expiracao: 3600 },
    devedor: {
      nome: "Cliente Teste",
      cpf: "12345678909"
    },
    valor: { original: valor.toString() },
    chave: PIX_KEY,
    solicitacaoPagador: "Pagamento via App"
  };

  const cobranca = await axios.post(`${BASE_URL}/v2/cob`, body, {
    headers: { Authorization: `Bearer ${token}` },
    httpsAgent: agent
  });

  const idLoc = cobranca.data.loc.id;

  const qr = await axios.get(`${BASE_URL}/v2/loc/${idLoc}/qrcode`, {
    headers: { Authorization: `Bearer ${token}` },
    httpsAgent: agent
  });

  return {
    txid: cobranca.data.txid,
    qrCode: qr.data.qrcode,
    imagemQrcode: qr.data.imagemQrcode
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
// Endpoints
// --------------------

// 🔁 Endpoint para gerar QR Code Pix
app.get("/pix/:valor", async (req, res) => {
  const valor = req.params.valor;
  try {
    const pixData = await criarPix(valor);

    res.json({
      txid: pixData.txid,
      qrCode: pixData.qrCode,
      imagemQrcode: pixData.imagemQrcode
    });
  } catch (err) {
    console.error("Erro ao gerar Pix:", err.response?.data || err.message);
    res.status(500).json({ error: "Erro ao gerar Pix" });
  }
});

// 🔍 Endpoint para consultar status Pix
app.get("/pix/status/:txid", async (req, res) => {
  const txid = req.params.txid;

  if (pixStatusMap[txid]) {
    return res.json({
      txid,
      status: pixStatusMap[txid].status,
      valor: pixStatusMap[txid].valor
    });
  }

  try {
    const statusData = await consultarPix(txid);
    res.json({
      txid: statusData.txid,
      status: statusData.status,
      valor: statusData.valor.original,
    });
  } catch (err) {
    console.error("Erro ao consultar status:", err.response?.data || err.message);
    res.status(500).json({ error: "Erro ao consultar status do Pix" });
  }
});

// 📩 Webhook para receber notificações Efí Pay
app.post("/efipay/webhook", async (req, res) => {
  const pixList = req.body.pix || [];

  for (const pix of pixList) {
    console.log("📩 PIX RECEBIDO via webhook:", pix);

    try {
      // 🔎 Consulta o status real da cobrança
      const statusData = await consultarPix(pix.txid);

      pixStatusMap[pix.txid] = {
        status: statusData.status,        // ✅ Agora vem da Efí
        valor: statusData.valor.original, // valor confirmado
      };

      console.log(`✅ Status atualizado: ${pix.txid} = ${statusData.status}`);
    } catch (err) {
      console.error(
        "❌ Erro ao consultar status Pix:",
        err.response?.data || err.message
      );
    }
  }

  // ✅ Retorna 200 para confirmar recebimento à Efí
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
