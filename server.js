import fs from "fs";
import https from "https";
import express from "express";
import axios from "axios";
import dotenv from "dotenv";

dotenv.config();
const app = express();
app.use(express.json());

// --------------------
// 🔧 Configuração do ambiente
// --------------------
const {
  CLIENT_ID,
  CLIENT_SECRET,
  PIX_KEY,
  CERT_PATH,
  WEBHOOK_URL,
} = process.env;

if (!CLIENT_ID || !CLIENT_SECRET || !PIX_KEY || !CERT_PATH || !WEBHOOK_URL) {
  console.error("❌ Variáveis faltando no .env");
  process.exit(1);
}

// ⚙️ Produção (sem sandbox)
const BASE_URL = "https://pix.api.efipay.com.br";

// Configura o certificado pfx
const agent = new https.Agent({
  pfx: fs.readFileSync(CERT_PATH),
  rejectUnauthorized: false,
});

// --------------------
// 💾 Armazenamento simples em memória
// --------------------
const pixStatusMap = {}; // txid -> { status, valor, webhookRecebido }
let cachedToken = null;
let tokenExpiresAt = 0;

// --------------------
// 🔐 Função — obter token OAuth2
// --------------------
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

// --------------------
// 🌐 Registrar Webhook (com retry automático)
// --------------------
async function registerWebhook(maxRetries = 5, delay = 8000) {
  for (let attempt = 1; attempt <= maxRetries; attempt++) {
    try {
      const token = await getAccessToken();
      const res = await axios.put(
        `${BASE_URL}/v2/webhook/${PIX_KEY}`,
        { webhookUrl: WEBHOOK_URL },
        { headers: { Authorization: `Bearer ${token}` }, httpsAgent: agent }
      );
      console.log("✅ Webhook registrado com sucesso:", res.data);
      return true;
    } catch (err) {
      const msg = err.response?.data || err.message;
      console.error(`❌ Tentativa ${attempt} ao registrar webhook falhou:`, msg);
      if (attempt < maxRetries) {
        console.log(`⏳ Aguardando ${delay / 1000}s para tentar novamente...`);
        await new Promise((r) => setTimeout(r, delay));
      } else {
        console.error("🚨 Falha definitiva ao registrar webhook após várias tentativas.");
      }
    }
  }
}

// --------------------
// 💰 Criar Pix + QR Code
// --------------------
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

  const idLoc = cobranca.data.loc.id;
  const qr = await axios.get(`${BASE_URL}/v2/loc/${idLoc}/qrcode`, {
    headers: { Authorization: `Bearer ${token}` },
    httpsAgent: agent,
  });

  pixStatusMap[cobranca.data.txid] = { status: "ATIVA", valor, webhookRecebido: false };
  monitorarPix(cobranca.data.txid);

  console.log(`💰 Pix gerado: TXID ${cobranca.data.txid} | Valor: R$${valor}`);
  return {
    txid: cobranca.data.txid,
    qrCode: qr.data.qrcode,
    imagemQrcode: qr.data.imagemQrcode,
  };
}

// --------------------
// 🔍 Consultar status do Pix
// --------------------
async function consultarPix(txid) {
  const token = await getAccessToken();
  const res = await axios.get(`${BASE_URL}/v2/cob/${txid}`, {
    headers: { Authorization: `Bearer ${token}` },
    httpsAgent: agent,
  });
  return res.data;
}

// --------------------
// 🔄 Polling de fallback (caso webhook não chegue)
// --------------------
function monitorarPix(txid, interval = 5000, timeout = 3600000, initialDelay = 45000) {
  setTimeout(() => {
    const start = Date.now();
    const timer = setInterval(async () => {
      if (pixStatusMap[txid]?.webhookRecebido) {
        clearInterval(timer);
        return;
      }

      if (Date.now() - start > timeout) {
        console.log(`⏰ Timeout: Pix ${txid} sem pagamento após ${timeout / 1000}s`);
        clearInterval(timer);
        return;
      }

      try {
        const data = await consultarPix(txid);
        if (pixStatusMap[txid].status !== data.status) {
          pixStatusMap[txid].status = data.status;
          console.log(`🔄 Polling atualizou ${txid}: ${data.status}`);
        }
        if (data.status === "CONCLUIDO") {
          console.log(`✅ Pix ${txid} pago (polling)`);
          clearInterval(timer);
        }
      } catch (err) {
        console.error(`❌ Erro no polling ${txid}:`, err.message);
      }
    }, interval);
  }, initialDelay);
}

// --------------------
// 🌍 Endpoints
// --------------------

// 📦 Gerar Pix
app.get("/pix/:valor", async (req, res) => {
  try {
    const data = await criarPix(req.params.valor);
    res.json(data);
  } catch (err) {
    console.error("❌ Erro ao gerar Pix:", err.response?.data || err.message);
    res.status(500).json({ error: "Erro ao gerar Pix" });
  }
});

// 🔎 Consultar status
app.get("/pix/status/:txid", (req, res) => {
  const info = pixStatusMap[req.params.txid] || { status: "ATIVA", valor: 0 };
  res.json({ txid: req.params.txid, ...info });
});

// 📬 Webhook EfíPay
app.post("/efipay/webhook", (req, res) => {
  console.log("📥 Webhook recebido:", JSON.stringify(req.body));
  res.status(200).json({ ok: true });

  const pixList = req.body.pix || [];
  for (const pix of pixList) {
    (async () => {
      try {
        const statusData = await consultarPix(pix.txid);
        pixStatusMap[pix.txid] = {
          status: statusData.status,
          valor: statusData.valor.original,
          webhookRecebido: true,
        };
        console.log(`✅ Webhook confirmou pagamento: ${pix.txid} = ${statusData.status}`);
      } catch (err) {
        console.error("❌ Erro ao processar webhook:", err.response?.data || err.message);
      }
    })();
  }
});

// 🧭 Status servidor
app.get("/", (req, res) => res.json({ ok: true, msg: "Servidor EfíPay rodando (produção)" }));

// --------------------
// 🚀 Inicialização
// --------------------
const PORT = process.env.PORT || 3000;
app.listen(PORT, async () => {
  console.log(`🚀 Servidor rodando na porta ${PORT}`);
  await registerWebhook(); // faz retry automático
});
