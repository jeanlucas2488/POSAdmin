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
  GAS_WEBHOOK_URL,
  ENVIRONMENT
} = process.env;

if (!CLIENT_ID || !CLIENT_SECRET || !PIX_KEY || !CERT_PATH || !GAS_WEBHOOK_URL) {
  console.error("❌ Variáveis faltando no .env");
  process.exit(1);
}

const isSandbox = ENVIRONMENT === "sandbox";
const BASE_URL = isSandbox
  ? "https://pix-h.api.efipay.com.br"
  : "https://pix.api.efipay.com.br";

// 🔐 HTTPS Agent com certificado PFX (mTLS)
const agent = new https.Agent({
  pfx: Buffer.from(process.env.CERT_PFX_BASE64, "base64"),
  passphrase: "", // sem senha
  rejectUnauthorized: false,
});

let cachedToken = null;
let tokenExpiresAt = 0;

// Token OAuth2
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
  console.log("✅ Token obtido");
  return cachedToken;
}

// Registrar Webhook com mTLS
async function registerWebhook() {
  const token = await getAccessToken();

  const webhookUrl = process.env.SERVER_WEBHOOK_URL;
  console.log("🔗 Registrando Webhook:", webhookUrl);

  const res = await axios.put(
    `${BASE_URL}/v2/webhook/${PIX_KEY}`,
    { webhookUrl },
    { headers: { Authorization: `Bearer ${token}` }, httpsAgent: agent }
  );

  console.log("✅ Webhook registrado:", res.data);
}

// Endpoint para callbacks Pix
app.post("/efipay/webhook", async (req, res) => {
  console.log("📥 Callback recebido:", req.body);

  try {
    await axios.post(GAS_WEBHOOK_URL, req.body);
    console.log("✅ Enviado ao Google Apps Script");
  } catch (err) {
    console.error("❌ Erro ao enviar ao GAS:", err.message);
  }

  res.sendStatus(200);
});

app.get("/", (_, res) => res.send("Servidor Efí ativo ✅"));

// Criar servidor HTTPS com certificado PFX
const PORT = 8443;
https.createServer({ pfx: fs.readFileSync(CERT_PATH), passphrase: "" }, app)
  .listen(PORT, async () => {
    console.log(`🚀 Servidor rodando em https://localhost:${PORT}`);
    await registerWebhook().catch(e => {
      console.error("❌ Erro ao registrar webhook:", e.response?.data || e.message);
    });
  });
