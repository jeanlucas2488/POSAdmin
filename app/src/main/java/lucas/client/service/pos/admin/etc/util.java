package lucas.client.service.pos.admin.etc;
import java.sql.*;

public class util
{
	public String nfehtml;
	public long supId, sanId, contasId, bolId;
	public byte[] imagem;
	public String bdata, bvencimento, btipo, bstatus,  bvalor, bdescricao;
	public String money, supVal, supDesc, sanVal, sanMot,forn, eval, etotal;
	public String sangria;
	public String fundo;
	public String op;
	public String eloD;
	public String usuario, senha;
	public String eloC;
	public String visaD;
	public String visaC;
	public String masterD;
	public String masterC;
	public String cabal;
	public String pix;
	public String hiper;
	public String hiperC;
	public String ouro;
	public String verde;
	public String soro;
	public String person;
	public String banrisul;
	public String banriC;
	public String banes;
	public String americ;
	public String data;
	private String table, cat, pagina, prod, quant, valor, dataIn,
	dataOut, tipo;
	private byte[] image;
	private long id;
	private long userId;
	public String codigo, classificacao, valor_pagar, data_vencimento, empresa,
			conta_bancaria , valor_pagar_ag, data_ag, pessoa, data_comp, desc_ag,
			comentarios, status, valor_pago, saldo_pagar;

	public String docto, cliente, dataCadastro, valRecebido, valDoc, desconto, acrescimo, saldo, crstatus;
	public long contasReceberId;
	public String getNfehtml(){
		return nfehtml;
	}
	public void setNfehtml(String nfe){
		this.nfehtml = nfe;
	}
	public String getCRStatus(){
		return crstatus;
	}
	public void setCrstatus(String st){
		this.crstatus = st;
	}
	public String getDocto(){
		return docto;
	}
	public void setDocto(String doc){
		this.docto = doc;
	}
	public String getCliente(){
		return cliente;
	}
	public void setCliente(String cli){
		this.cliente = cli;
	}
	public String getDataCadastro(){
		return dataCadastro;
	}
	public void setDataCadastro(String cad){
		this.dataCadastro = cad;
	}
	public String getValRecebido(){
		return valRecebido;
	}
	public void setValRecebido(String val){
		this.valRecebido = val;
	}
	public String getValDoc(){
		return valDoc;
	}
	public void setValDoc(String valdoc){
		this.valDoc = valdoc;
	}
	public String getDesconto(){
		return desconto;
	}
	public void setDesconto(String desco){
		this.desconto = desco;
	}
	public String getAcrescimo(){
		return acrescimo;
	}
	public void setAcrescimo(String acre){
		this.acrescimo = acre;
	}
	public String getSaldo(){
		return saldo;
	}
	public void setSaldo(String sal){
		this.saldo = sal;
	}
	public long getContasReceberId(){
		return contasReceberId;
	}
	public void setContasReceberId(long id){
		this.contasReceberId = id;
	}
	public String getBstatus(){
		return bstatus;
	}
	public void setBstatus(String st){
		this.bstatus = st;
	}
	public String getBdescricao(){
		return bdescricao;
	}
	public void setBdescricao(String bdesc){
		this.bdescricao = bdesc;
	}
	public String getBdata(){
		return bdata;
	}
	public void setBdata(String bdata1){
		this.bdata = bdata1;
	}
	public String getBvencimento(){
		return  bvencimento;
	}
	public void setBvencimento(String bvencimento1){
		this.bvencimento = bvencimento1;
	}
	public String getBtipo(){
		return btipo;
	}
	public void setBtipo(String btipo1){
		this.btipo = btipo1;
	}
	public byte[] getBImagem(){
		return imagem;
	}
	public void setBImagem(byte[] im){
		this.imagem = im;
	}
	public String getBvalor(){
		return bvalor;
	}
	public void setBvalor(String bval){
		this.bvalor = bval;
	}
	public long getBolId(){
		return bolId;
	}
	public void setBolId(long id){
		this.bolId = id;
	}
	public long getContasId(){
		return contasId;
	}
	public void setContasId(long id){
		this.contasId = id;
	}
	public String getSaldo_pagar(){
		return saldo_pagar;
	}
	public void setSaldo_pagar(String salp){
		this.saldo_pagar = salp;
	}
	public String getValor_pago(){
		return valor_pago;
	}
	public void setValor_pago(String valpag){
		this.valor_pago = valpag;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String stat){
		this.status = stat;
	}
	public String getComentarios(){
		return comentarios;
	}
	public void setComentarios(String come){
		this.comentarios = come;
	}
	public String getDesc_ag(){
		return desc_ag;
	}
	public void setDesc_ag(String des){
		this.desc_ag = des;
	}
	public String getData_comp(){
		return data_comp;
	}
	public void setData_comp(String com){
		this.data_comp = com;
	}
	public String getPessoa(){
		return pessoa;
	}
	public void setPessoa(String pes){
		this.pessoa = pes;
	}
	public String getData_ag(){
		return data_ag;
	}
	public void setData_ag(String dag){
		this.data_ag = dag;
	}
	public String getValor_pagar_ag(){
		return valor_pagar_ag;
	}
	public void setValor_pagar_ag(String val){
		this.valor_pagar_ag = val;
	}
	public String getConta_bancaria(){
		return conta_bancaria;
	}
	public void setConta_bancaria(String cb){
		this.conta_bancaria = cb;
	}
	public String getEmpresa(){
		return empresa;
	}
	public void setEmpresa(String emp){
		this.empresa = emp;
	}
	public String getData_vencimento(){
		return data_vencimento;
	}
	public void setData_vencimento(String ven){
		this.data_vencimento = ven;
	}

	public String getContasCodigo(){
		return codigo;
	}
	public void setContasCodigo(String cod){
		this.codigo = cod;
	}
	public String getClassificacao(){
		return classificacao;
	}
	public void setClassificacao(String clas){
		this.classificacao = clas;
	}
	public String getValor_pagar(){
		return valor_pagar;
	}
	public void setValor_pagar(String va){
		this.valor_pagar = va;
	}


	public long getSupId(){
		return supId;
	}
	public void setSupId(long id){
		this.supId = id;
	}
	public long getSanId(){
		return sanId;
	}
	public void setSanId(long id){
		this.sanId = id;
	}
	public String getSupDesc(){
		return supDesc;
	}
	public void setSupDesc(String sup){
		this.supDesc = sup;
	}
	public String getSanMot(){
		return sanMot;
	}
	public void setSanMot(String mot){
		this.sanMot = mot;
	}
	public String getSanVal(){
		return sanVal;
	}
	public void setSanVal(String val){
		this.sanVal = val;
	}
	public long getUserId(){
		return userId;
	}
	public void setUserId(long user){
		this.userId = user;
	}
	public String getUsuario(){
		return usuario;
	}
	public void setUsuario(String dat){
		this.usuario = dat;
	}
	
	public String getSenha(){
		return senha;
	}
	public void setSenha(String dat){
		this.senha = dat;
	}
	
	public String getETotal(){
		return etotal;
	}
	public void setETotal(String dat){
		this.etotal = dat;
	}
	public String getEVal(){
		return eval;
	}
	public void setEVal(String dat){
		this.eval = dat;
	}
	public String getForn(){
		return forn;
	}
	public void setForn(String dat){
		this.forn = dat;
	}
	public String getTipo(){
		return tipo;
	}
	public void setTipo(String dat){
		this.tipo = dat;
	}
	public String getDataOut(){
		return dataOut;
	}
	public void setDataOut(String dat){
		this.dataOut = dat;
	}
	public String getDataIn(){
		return dataIn;
	}
	public void setDataIn(String dat){
		this.dataIn = dat;
	}
	public String getData(){
		return data;
	}
	public void setData(String dat){
		this.data = dat;
	}
	public String getSupVal(){
		return supVal;
	}
	public void setSupVal(String in){
		this.supVal = in;
	}
	public String getDinheiro(){
		return money;
	}
	public void setDinheiro(String mo){
		this.money = mo;
	}
	public String getFundo(){
		return fundo;
	}
	public void setFundo(String fun){
		this.fundo = fun;
	}
	public String getOperador(){
		return op;
	}
	public void setOperador(String op){
		this.op = op;
	}
	public String getSangria(){
		return sangria;
	}
	public void setSangria(String san){
		this.sangria = san;
	}
	public String getEloD(){
		return eloD;
	}
	public void setEloD(String eloD){
		this.eloD = eloD;
	}
	public String getEloC(){
		return eloC;
	}
	public void setEloC(String eloC){
		this.eloC = eloC;
	}
	public String getVisaD(){
		return visaD;
	}
	public void setVisaD(String visaD){
		this.visaD = visaD;
	}
	public String getVisaC(){
		return visaC;
	}
	public void setVisaC(String visaC){
		this.visaC = visaC;
	}
	public String getMasterD(){
		return masterD;
	}
	public void setMasterD(String masterD){
		this.masterD = masterD;
	}
	public String getMasterC(){
		return masterC;
	}
	public void setMasterC(String masterC){
		this.masterC = masterC;
	}
	public String getHiper(){
		return hiper;
	}
	public void setHiper(String hiper){
		this.hiper = hiper;
	}
	public String getHiperC(){
		return hiperC;
	}
	public void setHiperC(String hiperC){
		this.hiperC = hiperC;
	}
	public String getCabal(){
		return cabal;
	}
	public void setCabal(String cabal){
		this.cabal = cabal;
	}
	public String getPix(){
		return pix;
	}
	public void setPix(String pix){
		this.pix = pix;
	}
	public String getOuro(){
		return ouro;
	}
	public void setOuro(String ouro){
		this.ouro = ouro;
	}
	public String getVerde(){
		return verde;
	}
	public void setVerde(String verde){
		this.verde = verde;
	}
	public String getSoro(){
		return soro;
	}
	public void setSoro(String soro){
		this.soro = soro;
	}
	public String getPerson(){
		return person;
	}
	public void setPerson(String person){
		this.person = person;
	}
	public String getbanric(){
		return banrisul;
	}
	public void setBanric(String banric){
		this.banrisul = banric;
	}
	public String getBanriC(){
		return banriC;
	}
	public void setBanriC(String banriC){
		this.banriC = banriC;
	}
	public String getBanes(){
		return banes;
	}
	public void setBanes(String banes){
		this.banes = banes;
	}
	public String getAmeric(){
		return americ;
	}
	public void setAmeric(String americ){
		this.americ = americ;
	}
	public String getCategory(){
		return cat;
	}
	public void setCategory(String pg){
		this.cat = pg;
	}
	public String getPagina(){
		return pagina;
	}
	public void setPagina(String pg){
		this.pagina = pg;
	}
	public String getTable(){
		return table;
	}
	public void setTable(String tb){
		this.table = tb;
	}
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	public String getProd(){
		return prod;
	}
	public void setProd(String prod){
		this.prod = prod;
	}
	public String getQuant(){
		return quant;
	}
	public void setQuant(String quan){
		this.quant = quan;
	}
	public String getValor(){
		return valor;
	}
	public void setValor(String val){
		this.valor = val;
	}
	public byte[] getImage(){
		return image;
	}
	public void setImage(byte[] image){
		this.image = image;
	}
}
