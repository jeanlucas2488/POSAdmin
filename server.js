import fs from "fs";
import https from "https";
import express from "express";
import axios from "axios";
import dotenv from "dotenv";

dotenv.config();

const {
  CLIENT_ID,
  CLIENT_SECRET,
  PIX_KEY,
  ENVIRONMENT,
  WEBHOOK_URL,
  CERT_PATH,
  CERT_PASSPHRASE,
  PORT
} = process.env;

if (!CLIENT_ID || !CLIENT_SECRET || !PIX_KEY || !CERT_PATH || !WEBHOOK_URL) {
  console.error("❌ Variáveis faltando no .env");
  process.exit(1);
}

const isSandbox = ENVIRONMENT === "sandbox";
const BASE_URL = isSandbox
  ? "https://pix-h.api.efipay.com.br"
  : "https://pix.api.efipay.com.br";

const app = express();
app.use(express.json());

// TLS mútuo usando arquivo .p12
const httpsOptions = {
  pfx: fs.readFileSync(CERT_PATH),
  passphrase: CERT_PASSPHRASE || "",
  requestCert: true,      // TLS mútuo
  rejectUnauthorized: true
};

const server = https.createServer(httpsOptions, app);

// 🔐 Função para obter token OAuth2
let cachedToken = null;
let tokenExpiresAt = 0;
async function getAccessToken() {
  const now = Date.now();
  if (cachedToken && now < tokenExpiresAt - 5000) return cachedToken;

  const auth = Buffer.from(`${CLIENT_ID}:${CLIENT_SECRET}`).toString("base64");
  const res = await axios.post(
    `${BASE_URL}/oauth/token`,
    { grant_type: "client_credentials" },
    { headers: { Authorization: `Basic ${auth}` }, httpsAgent: new https.Agent({ pfx: fs.readFileSync(CERT_PATH), passphrase: CERT_PASSPHRASE || "" }) }
  );

  cachedToken = res.data.access_token;
  tokenExpiresAt = now + res.data.expires_in * 1000;
  return cachedToken;
}

// 🔗 Registrar webhook na Efí Pay
async function registerWebhook() {
  try {
    const token = await getAccessToken();
    const res = await axios.put(
      `${BASE_URL}/v2/webhook/${PIX_KEY}`,
      { webhookUrl: WEBHOOK_URL },
      { headers: { Authorization: `Bearer ${token}` }, httpsAgent: new https.Agent({ pfx: fs.readFileSync(CERT_PATH), passphrase: CERT_PASSPHRASE || "" }) }
    );
    console.log("✅ Webhook registrado na Efí Pay:", res.data);
  } catch (err) {
    console.error("❌ Erro ao registrar webhook:", err.response?.data || err.message);
  }
}

// 🔁 Endpoint do webhook
app.post("/efipay/webhook", (req, res) => {
  console.log("📥 Callback recebido:", req.body);
  res.json({ success: true, message: "Pix recebido", data: req.body.pix || [] });
});

// 🔄 Teste simples
app.get("/", (req, res) => res.send("Servidor Efí Pay ativo 🚀"));

// 🔁 Registrar webhook ao iniciar
registerWebhook().catch(console.error);

server.listen(PORT || 8443, () => {
  console.log(`🚀 Servidor rodando com TLS mútuo na porta ${PORT || 8443}`);
});
