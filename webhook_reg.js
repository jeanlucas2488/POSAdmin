import fs from "fs";
import https from "https";
import axios from "axios";
import dotenv from "dotenv";

dotenv.config();

const {
  CLIENT_ID,
  CLIENT_SECRET,
  PIX_KEY,
  CERT_PATH,
  ENVIRONMENT,
  SERVER_WEBHOOK_URL
} = process.env;

if (!CLIENT_ID || !CLIENT_SECRET || !PIX_KEY || !CERT_PATH || !SERVER_WEBHOOK_URL) {
  console.error("❌ Variáveis faltando no .env");
  process.exit(1);
}

const isSandbox = ENVIRONMENT === "sandbox";
const BASE_URL = isSandbox
  ? "https://pix-h.api.efipay.com.br"
  : "https://pix.api.efipay.com.br";

const agent = new https.Agent({
  pfx: fs.readFileSync(CERT_PATH),
  rejectUnauthorized: true
});

let cachedToken = null;
let tokenExpiresAt = 0;

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

async function registerWebhook() {
  try {
    const token = await getAccessToken();
    const res = await axios.put(
      `${BASE_URL}/v2/webhook/${PIX_KEY}`,
      { webhookUrl: SERVER_WEBHOOK_URL },
      { headers: { Authorization: `Bearer ${token}` }, httpsAgent: agent }
    );

    console.log("✅ Webhook registrado na Efí:", res.data);
  } catch (err) {
    console.error("❌ Erro ao registrar webhook:", err.response?.data || err.message);
  }
}

registerWebhook();
