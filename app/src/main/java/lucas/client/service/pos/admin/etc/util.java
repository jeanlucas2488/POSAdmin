package lucas.client.service.pos.admin.etc;
import java.sql.*;

public class util
{
	public String money, supVal,forn, eval, etotal;
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
