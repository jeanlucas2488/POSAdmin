import express from "express";
import axios from "axios";
import dotenv from "dotenv";
import QRCode from "qrcode";

dotenv.config();

const app = express();
app.use(express.json());

const {
  CLIENT_ID,
  CLIENT_SECRET,
  PIX_KEY,
  ENVIRONMENT,
  WEBHOOK_URL,
  GAS_WEBHOOK_URL
} = process.env;

if (!CLIENT_ID || !CLIENT_SECRET || !PIX_KEY || !WEBHOOK_URL || !GAS_WEBHOOK_URL) {
  console.error("❌ Variáveis faltando no .env");
  process.exit(1);
}

const isSandbox = ENVIRONMENT === "sandbox";
const BASE_URL = isSandbox
  ? "https://pix-h.api.efipay.com.br"
  : "https://pix.api.efipay.com.br";

// 🔐 Token OAuth2
let cachedToken = null;
let tokenExpiresAt = 0;

async function getAccessToken() {
  const now = Date.now();
  if (cachedToken && now < tokenExpiresAt - 5000) return cachedToken;

  const auth = Buffer.from(`${CLIENT_ID}:${CLIENT_SECRET}`).toString("base64");
  const res = await axios.post(
    `${BASE_URL}/oauth/token`,
    { grant_type: "client_credentials" },
    { headers: { Authorization: `Basic ${auth}` } }
  );

  cachedToken = res.data.access_token;
  tokenExpiresAt = now + res.data.expires_in * 1000;
  console.log("✅ Token obtido");
  return cachedToken;
}

// 🔗 Registrar Webhook Efí Pay
async function registerWebhook() {
  try {
    const token = await getAccessToken();
    const res = await axios.put(
      `${BASE_URL}/v2/webhook/${PIX_KEY}`,
      { webhookUrl: WEBHOOK_URL },
      { headers: { Authorization: `Bearer ${token}` } }
    );
    console.log("✅ Webhook registrado na Efí Pay:", res.data);
  } catch (err) {
    console.error("❌ Erro ao registrar webhook:", err.response?.data || err.message);
  }
}

// 🔁 Endpoint para receber callbacks da Efí Pay
app.post("/efipay/webhook", async (req, res) => {
  console.log("📥 Callback recebido:", req.body);

  try {
    await axios.post(GAS_WEBHOOK_URL, req.body, { headers: { "Content-Type": "application/json" } });
    console.log("➡️ Dados enviados para Apps Script");
  } catch (err) {
    console.error("❌ Erro enviando para Apps Script:", err.message);
  }

  res.json({ success: true, message: "Pix recebido", data: req.body.pix || [] });
});

// Endpoint para gerar QR Code Pix para Android
app.get("/pix/:valor", async (req, res) => {
  const valor = req.params.valor;

  try {
    // Aqui você deve gerar o payload Pix real via Efí Pay
    const pixPayload = `000201...${valor}...52040000`; // placeholder

    // Gerar QR Code em string (Data URL opcional)
    const qrCodeString = pixPayload;

    // Retorna JSON para o Android
    res.json({
      txid: "TESTE12345",  // você pode gerar um txid real
      qrCode: qrCodeString
    });

  } catch (err) {
    console.error(err);
    res.status(500).json({ error: "Erro ao gerar Pix" });
  }
});
// 🔄 Endpoint teste
app.get("/", (req, res) => res.send("Servidor Efí Pay ativo 🚀"));

// 🔁 Registrar webhook ao iniciar
registerWebhook().catch(console.error);

// Porta Render ou local
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`🚀 Servidor rodando na porta ${PORT}`));
