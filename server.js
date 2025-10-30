import fs from "fs";
import https from "https";
import express from "express";
import axios from "axios";
import dotenv from "dotenv";

dotenv.config();
const app = express();
app.use(express.json());

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

// 🔗 Registrar Webhook Efí Pay
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

  // 🧾 Monta o corpo da cobrança
  const body = {
    calendario: { expiracao: 3600 },
    devedor: {
      nome: "Cliente Teste",
      cpf: "12345678909" // ⚠️ CPF obrigatório ou CNPJ
    },
    valor: { original: valor.toString() }, // Valor como string
    chave: PIX_KEY, // Sua chave Pix cadastrada na Efí
    solicitacaoPagador: "Pagamento via App"
  };

  // 🔥 Cria a cobrança
  const cobranca = await axios.post(`${BASE_URL}/v2/cob`, body, {
    headers: { Authorization: `Bearer ${token}` },
    httpsAgent: agent
  });

  const idLoc = cobranca.data.loc.id;

  console.log("✅ Cobrança criada:", cobranca.data.txid);

  // 🎯 Busca o QR Code usando o id da cobrança
  const qr = await axios.get(`${BASE_URL}/v2/loc/${idLoc}/qrcode`, {
    headers: { Authorization: `Bearer ${token}` },
    httpsAgent: agent
  });

  console.log("✅ QR Code gerado com sucesso");

  // Retorna o txid e o código para o app
  return {
    txid: cobranca.data.txid,
    qrCode: qr.data.qrcode, // código Pix Copia e Cola
    imagemQrcode: qr.data.imagemQrcode // imagem em base64
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

// 🔁 Endpoint para gerar QR Code Pix
app.get("/pix/:valor", async (req, res) => {
  const valor = req.params.valor;
  try {
    const pixData = await criarPix(valor);
    res.json({
      txid: pixData.txid,
      qrCode: pixData.qrcode,
    });
  } catch (err) {
    console.error("Erro ao gerar Pix:", err.response?.data || err.message);
    res.status(500).json({ error: "Erro ao gerar Pix" });
  }
});

// 🔍 Endpoint para consultar status Pix
app.get("/pix/status/:txid", async (req, res) => {
  const txid = req.params.txid;
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
app.post("/efipay/webhook", (req, res) => {
  console.log("📩 PIX RECEBIDO:", JSON.stringify(req.body, null, 2));
  res.status(200).json({ ok: true });
});

// 🧭 Endpoint de teste
app.get("/", (req, res) => res.json({ ok: true, msg: "Servidor Efí ativo" }));

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`🚀 Servidor rodando na porta ${PORT}`);
  registerWebhook();
});
