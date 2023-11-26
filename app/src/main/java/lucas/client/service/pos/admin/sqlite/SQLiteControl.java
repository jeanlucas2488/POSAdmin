package lucas.client.service.pos.admin.sqlite;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.graphics.*;
import java.io.*;
import java.util.*;
import lucas.client.service.pos.admin.etc.*;
import java.nio.file.attribute.*;

public class SQLiteControl
{
	
	SQLiteDatabase db;
	
	public SQLiteControl(Context c){
		SQLiteHelper sql = new SQLiteHelper(c);
		db = sql.getWritableDatabase();
	}
	public void upCategory(util us){
		ContentValues cv = new ContentValues();
		cv.put("paginas", us.getCategory());
		db.update("Paginas", cv, "id = ?", new String[]{String.valueOf(us.getId())});
	}
	public void insertCategory(util us){
		ContentValues cv = new ContentValues();
		cv.put("paginas", us.getCategory());
		db.insert("Paginas", null, cv);
	}
	public void userUp(util us){
		ContentValues cv = new ContentValues();
		cv.put("userId", us.getUserId());
		cv.put("usuario", us.getUsuario());
		cv.put("senha", us.getSenha());
		db.update("senhas", cv, "userId = ?", new String[]{String.valueOf(us.getUserId())});
	}
	public void userIn(util us){
		ContentValues cv = new ContentValues();
		cv.put("usuario", us.getUsuario());
		cv.put("senha", us.getSenha());
		db.insert("senhas", null, cv);
	}
	public void post(util us){
		ContentValues ct = new ContentValues();
		ct.put("prod", us.getProd());
		ct.put("quant", us.getQuant());
		ct.put("valor", us.getValor());
		ct.put("forn", us.getForn());
		
		ct.put("etotal", us.getETotal());
		ct.put("dataIn", us.getDataIn());
		ct.put("dataOut", us.getDataOut());
		ct.put("tipo", us.getTipo());
		ct.put("image", us.getImage());
		db.insert(us.getTable(), null, ct);
	}
	public void postUp(util us){
		ContentValues ct = new ContentValues();
		ct.put("id", us.getId());
		ct.put("prod", us.getProd());
		ct.put("quant", us.getQuant());
		ct.put("valor", us.getValor());
		ct.put("forn", us.getForn());
		ct.put("etotal", us.getETotal());
		ct.put("dataIn", us.getDataIn());
		ct.put("dataOut", us.getDataOut());
		ct.put("tipo", us.getTipo());
		ct.put("image", us.getImage());
		db.update(us.getTable(), ct, "id = ?", new String[]{String.valueOf(us.getId())});
	}
	public void del(util us){
		db.execSQL("delete from " + us.getTable());
	}
	public void delete(util us){
		db.delete(us.getTable(), "id = ?", new String[]{String.valueOf(us.getId())});
	}
	public void setContasPagar(util us){
		ContentValues ct = new ContentValues();
		ct.put("codigo", us.getContasCodigo());
		ct.put("classificacao", us.getClassificacao());
		ct.put("valor_pagar", us.getValor_pagar());
		ct.put("data_vencimento", us.getData_vencimento());
		ct.put("empresa", us.getEmpresa());
		ct.put("conta_bancaria", us.getConta_bancaria());
		ct.put("valor_pagar_ag", us.getValor_pagar_ag());
		ct.put("data_ag", us.getData_ag());
		ct.put("pessoa", us.getPessoa());
		ct.put("data_comp", us.getData_comp());
		ct.put("desc_ag", us.getDesc_ag());
		ct.put("comentarios", us.getComentarios());
		ct.put("status", us.getStatus());
		ct.put("valor_pago", us.getValor_pago());
		ct.put("saldo_pagar", us.getSaldo_pagar());
		db.insert("ContasPagar", null, ct);
	}
	public void upContasPagar(util us){
		ContentValues ct = new ContentValues();
		ct.put("codigo", us.getContasCodigo());
		ct.put("classificacao", us.getClassificacao());
		ct.put("valor_pagar", us.getValor_pagar());
		ct.put("data_vencimento", us.getData_vencimento());
		ct.put("empresa", us.getEmpresa());
		ct.put("conta_bancaria", us.getConta_bancaria());
		ct.put("valor_pagar_ag", us.getValor_pagar_ag());
		ct.put("data_ag", us.getData_ag());
		ct.put("pessoa", us.getPessoa());
		ct.put("data_comp", us.getData_comp());
		ct.put("desc_ag", us.getDesc_ag());
		ct.put("comentarios", us.getComentarios());
		ct.put("status", us.getStatus());
		ct.put("valor_pago", us.getValor_pago());
		ct.put("saldo_pagar", us.getSaldo_pagar());
		db.update("ContasPagar", ct, "id = ?", new String[]{String.valueOf(us.getContasId())});
	}
	public List<util> getContas(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "codigo", "classificacao", "valor_pagar", "data_vencimento", "empresa",
				"conta_bancaria", "valor_pagar_ag", "data_ag", "pessoa", "data_comp", "desc_ag",
				"comentarios", "status", "valor_pago", "saldo_pagar"};

		Cursor cs = db.query("ContasPagar", cl, null, null, null, null, "codigo ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setContasId(Integer.parseInt(cs.getString(0)));
				us.setContasCodigo(cs.getString(1));
				us.setClassificacao(cs.getString(2));
				us.setValor_pagar(cs.getString(3));
				us.setData_vencimento(cs.getString(4));
				us.setEmpresa(cs.getString(5));
				us.setConta_bancaria(cs.getString(6));
				us.setValor_pagar_ag(cs.getString(7));
				us.setData_ag(cs.getString(8));
				us.setPessoa(cs.getString(9));
				us.setData_comp(cs.getString(10));
				us.setDesc_ag(cs.getString(11));
				us.setComentarios(cs.getString(12));
				us.setStatus(cs.getString(13));
				us.setValor_pago(cs.getString(14));
				us.setSaldo_pagar(cs.getString(15));
				arr.add(us);
			} while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> getUsers(){
		ArrayList<util> ar = new ArrayList<util>();
		String[] cl = {"userId", "usuario", "senha"};
		Cursor cs = db.query("senhas", cl, null, null, null, null, "usuario ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setUserId(Integer.parseInt(cs.getString(0)));
				us.setUsuario(cs.getString(1));
				us.setSenha(cs.getString(2));
				ar.add(us);
			} while(cs.moveToNext());
		}
		return ar;
	}
	public List<util> suFind(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "sup"};
		Cursor cs = db.query("supSom", cl, null, null, null, null, "sup ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setSupVal(cs.getString(1));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}


	public List<util> saFind(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "sangria"};
		Cursor cs = db.query("saldo", cl, null, null, null, null, "sangria ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setSangria(cs.getString(1));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}



	public util supFind(long id){
		util us = new util();
		Cursor cs = db.rawQuery("select * from Suprimentos WHERE id="+id+"", null);
		if(cs.moveToFirst()){

			do{
				us.setSupId(cs.getLong(cs.getColumnIndex("id")));
				us.setSupVal(cs.getString(cs.getColumnIndex("suprimento_val")));
				us.setSupDesc(cs.getString(cs.getColumnIndex("suprimento_mot")));
			}while(cs.moveToNext());
		}
		return us;
	}
	public util sanFind(long id){
		util us = new util();
		Cursor cs = db.rawQuery("select * from Sangrias WHERE id="+id+"", null);
		if(cs.moveToFirst()){

			do{
				us.setSanId(cs.getLong(cs.getColumnIndex("id")));
				us.setSanVal(cs.getString(cs.getColumnIndex("sangria_val")));
				us.setSanMot(cs.getString(cs.getColumnIndex("sangria_mot")));
			}while(cs.moveToNext());
		}
		return us;
	}
	public List<util> fechamento(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "data", "operador", "fundo", "dinheiro",
		               "eloD", "eloC", "visaD", "visaC", "masterD", "masterC", "hiper", "hiperc", "cabal", "pix", "verde", "person", "soro", "ouro",
					   "banrisul", "banric", "banes", "americ"};
		Cursor cs = db.query("Fechamento", cl, null, null, null, null, "data ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setData(cs.getString(1));
				us.setOperador(cs.getString(2));
				us.setFundo(cs.getString(3));
				us.setDinheiro(cs.getString(4));
				us.setEloD(cs.getString(5));
				us.setEloC(cs.getString(6));
				us.setVisaD(cs.getString(7));
				us.setVisaC(cs.getString(8));
				us.setMasterD(cs.getString(9));
				us.setMasterC(cs.getString(10));
				us.setHiper(cs.getString(11));
				us.setHiperC(cs.getString(12));
				us.setCabal(cs.getString(13));
				us.setPix(cs.getString(14));
				us.setVerde(cs.getString(15));
				us.setPerson(cs.getString(16));
				us.setSoro(cs.getString(17));
				us.setOuro(cs.getString(18));
				us.setBanric(cs.getString(19));
				us.setBanriC(cs.getString(20));
				us.setBanes(cs.getString(21));
				us.setAmeric(cs.getString(22));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public util getCategory(long id){
		util us = new util();
		Cursor cs = db.rawQuery("select paginas from Paginas WHERE id ="+id+"", null);
		if(cs.moveToFirst()){
			do{
				us.setCategory(cs.getString(cs.getColumnIndex("paginas")));
			}while(cs.moveToNext());
		}
		return us;
	}
	public List<util> category(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "paginas"};
		Cursor cs = db.query("Paginas", cl, null, null, null, null, "paginas ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setCategory(cs.getString(1));
				
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> findP10(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "prod", "quant", "valor", "image", "forn", "dataIn", "dataOut", "etotal", "tipo"};
		Cursor cs = db.query("paginaTen", cl, null, null, null, null, "prod ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setProd(cs.getString(1));
				us.setQuant(cs.getString(2));
				us.setValor(cs.getString(3));
				us.setImage(cs.getBlob(4));
				us.setForn(cs.getString(5));
				us.setDataIn(cs.getString(6));
				us.setDataOut(cs.getString(7));
				us.setETotal(cs.getString(8));
				us.setTipo(cs.getString(9));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> findP9(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "prod", "quant", "valor", "image", "forn", "dataIn", "dataOut", "etotal", "tipo"};
		Cursor cs = db.query("paginaNine", cl, null, null, null, null, "prod ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setProd(cs.getString(1));
				us.setQuant(cs.getString(2));
				us.setValor(cs.getString(3));
				us.setImage(cs.getBlob(4));
				us.setForn(cs.getString(5));
				us.setDataIn(cs.getString(6));
				us.setDataOut(cs.getString(7));
				us.setETotal(cs.getString(8));
				us.setTipo(cs.getString(9));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> findP8(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "prod", "quant", "valor", "image", "forn", "dataIn", "dataOut", "etotal", "tipo"};
		Cursor cs = db.query("paginaEight", cl, null, null, null, null, "prod ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setProd(cs.getString(1));
				us.setQuant(cs.getString(2));
				us.setValor(cs.getString(3));
				us.setImage(cs.getBlob(4));
				us.setForn(cs.getString(5));
				us.setDataIn(cs.getString(6));
				us.setDataOut(cs.getString(7));
				us.setETotal(cs.getString(8));
				us.setTipo(cs.getString(9));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> findP7(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "prod", "quant", "valor", "image", "forn", "dataIn", "dataOut", "etotal", "tipo"};
		Cursor cs = db.query("paginaSeven", cl, null, null, null, null, "prod ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setProd(cs.getString(1));
				us.setQuant(cs.getString(2));
				us.setValor(cs.getString(3));
				us.setImage(cs.getBlob(4));
				us.setForn(cs.getString(5));
				us.setDataIn(cs.getString(6));
				us.setDataOut(cs.getString(7));
				us.setETotal(cs.getString(8));
				us.setTipo(cs.getString(9));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> findP6(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "prod", "quant", "valor", "image", "forn", "dataIn", "dataOut", "etotal", "tipo"};
		Cursor cs = db.query("paginaSix", cl, null, null, null, null, "prod ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setProd(cs.getString(1));
				us.setQuant(cs.getString(2));
				us.setValor(cs.getString(3));
				us.setImage(cs.getBlob(4));
				us.setForn(cs.getString(5));
				us.setDataIn(cs.getString(6));
				us.setDataOut(cs.getString(7));
				us.setETotal(cs.getString(8));
				us.setTipo(cs.getString(9));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> findP5(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "prod", "quant", "valor", "image", "forn", "dataIn", "dataOut", "etotal", "tipo"};
		Cursor cs = db.query("paginaFive", cl, null, null, null, null, "prod ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setProd(cs.getString(1));
				us.setQuant(cs.getString(2));
				us.setValor(cs.getString(3));
				us.setImage(cs.getBlob(4));
				us.setForn(cs.getString(5));
				us.setDataIn(cs.getString(6));
				us.setDataOut(cs.getString(7));
				us.setETotal(cs.getString(8));
				us.setTipo(cs.getString(9));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> findP4(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "prod", "quant", "valor", "image", "forn", "dataIn", "dataOut", "etotal", "tipo"};
		Cursor cs = db.query("paginaFour", cl, null, null, null, null, "prod ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setProd(cs.getString(1));
				us.setQuant(cs.getString(2));
				us.setValor(cs.getString(3));
				us.setImage(cs.getBlob(4));
				us.setForn(cs.getString(5));
				us.setDataIn(cs.getString(6));
				us.setDataOut(cs.getString(7));
				us.setETotal(cs.getString(8));
				us.setTipo(cs.getString(9));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> findP3(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "prod", "quant", "valor", "image", "forn", "dataIn", "dataOut", "etotal", "tipo"};
		Cursor cs = db.query("paginaThree", cl, null, null, null, null, "prod ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setProd(cs.getString(1));
				us.setQuant(cs.getString(2));
				us.setValor(cs.getString(3));
				us.setImage(cs.getBlob(4));
				us.setForn(cs.getString(5));
				us.setDataIn(cs.getString(6));
				us.setDataOut(cs.getString(7));
				us.setETotal(cs.getString(8));
				us.setTipo(cs.getString(9));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> findP2(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "prod", "quant", "valor", "image", "forn", "dataIn", "dataOut", "etotal", "tipo"};
		Cursor cs = db.query("paginaTwo", cl, null, null, null, null, "prod ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setProd(cs.getString(1));
				us.setQuant(cs.getString(2));
				us.setValor(cs.getString(3));
				us.setImage(cs.getBlob(4));
				us.setForn(cs.getString(5));
				us.setDataIn(cs.getString(6));
				us.setDataOut(cs.getString(7));
				us.setETotal(cs.getString(8));
				us.setTipo(cs.getString(9));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
	public List<util> findP1(){
		ArrayList<util> arr = new ArrayList<util>();
		String[] cl = {"id", "prod", "quant", "valor", "image", "forn", "dataIn", "dataOut", "etotal", "tipo"};
		Cursor cs = db.query("paginaOne", cl, null, null, null, null, "prod ASC");
		if(cs.getCount() >0){
			cs.moveToFirst();
			do{
				util us = new util();
				us.setId(Integer.parseInt(cs.getString(0)));
				us.setProd(cs.getString(1));
				us.setQuant(cs.getString(2));
				us.setValor(cs.getString(3));
				us.setImage(cs.getBlob(4));
				us.setForn(cs.getString(5));
				us.setDataIn(cs.getString(6));
				us.setDataOut(cs.getString(7));
				us.setETotal(cs.getString(8));
				us.setTipo(cs.getString(9));
				arr.add(us);
			}while(cs.moveToNext());
		}
		return arr;
	}
}
