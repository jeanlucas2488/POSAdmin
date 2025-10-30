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
} = process.env;

if (!CLIENT_ID || !CLIENT_SECRET || !PIX_KEY || !CERT_PATH) {
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

// 🔗 Gerar Pix via Efí Pay
async function criarPix(valor) {
  const token = await getAccessToken();

  const res = await axios.post(
    `${BASE_URL}/v2/pix`,
    {
      calendario: { expiracao: 3600 }, // 1 hora
      devedor: { nome: "Cliente Teste" },
      valor: { original: valor },
      chave: PIX_KEY,
      solicitacaoPagador: "Pagamento via App",
    },
    { headers: { Authorization: `Bearer ${token}` }, httpsAgent: agent }
  );

  return res.data;
}

// 🔁 Endpoint para gerar QR Code Pix
app.get("/pix/:valor", async (req, res) => {
  const valor = req.params.valor;

  try {
    const pixData = await criarPix(valor);

    // Retorna JSON com txid e qrCode
    res.json({
      txid: pixData.txid,
      qrCode: pixData.qrcode, // ⚠️ string para gerar QR no app Android
    });
  } catch (err) {
    console.error(err.response?.data || err.message);
    res.status(500).json({ error: "Erro ao gerar Pix" });
  }
});

// 🔄 Teste simples
app.get("/", (req, res) => res.json({ ok: true, message: "Servidor Efí ativo" }));

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`🚀 Servidor rodando na porta ${PORT}`));
