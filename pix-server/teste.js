const fetch = global.fetch; // garante compatibilidade no Termux

// 🔗 Seu Webhook Google Apps Script
const WEBHOOK_URL = "https://script.google.com/macros/s/AKfycbxxN_72Fewi2Plw5YYuQk85Z8NsSu-6u9wVUduqNeCllGRof0v6ef717lssm4327FybbQ/exec";

// 🔢 Simula alguns Pix diferentes
const pixList = [
  { id: "1", valor: 10.50, pagador: "Jean", chave: "jean@pix", status: "OK" },
  { id: "2", valor: 25.00, pagador: "Maria", chave: "maria@pix", status: "OK" },
  { id: "3", valor: 7.90, pagador: "Carlos", chave: "carlos@pix", status: "OK" },
  { id: "4", valor: 150.00, pagador: "Loja X", chave: "lojax@pix", status: "OK" },
];

async function enviarPix(pix) {
  try {
    const res = await fetch(WEBHOOK_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ pix: [pix] })
    });
    const data = await res.json();
    console.log(`✅ Pix ID ${pix.id} enviado:`, data.message);
  } catch (err) {
    console.error(`❌ Erro no Pix ID ${pix.id}:`, err.message);
  }
}

(async () => {
  console.log("🚀 Enviando Pixs para o Webhook...");
  for (const pix of pixList) {
    await enviarPix(pix);
  }
  console.log("🎯 Finalizado com sucesso!");
})();
