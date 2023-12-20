package lucas.client.service.pos.admin.mercearia.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lucas.client.service.pos.admin.etc.util;

public class SQLiteControl {
    SQLiteDatabase db;

    public SQLiteControl(Context c){
        SQLiteHelper hp = new SQLiteHelper(c);
        db = hp.getWritableDatabase();
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
    public void delBoleto(long id){
        db.delete("Boletos", "id = ?", new String[]{String.valueOf(id)});
    }
    public void delete(util us){
        db.delete(us.getTable(), "id = ?", new String[]{String.valueOf(us.getId())});
    }
    public void setBaixaBoleto(util us){
        ContentValues ct = new ContentValues();
        ct.put("data", us.getBdata());
        ct.put("vencimento", us.getBvencimento());
        ct.put("valor", us.getBvalor());
        ct.put("tipo", us.getBtipo());
        ct.put("status", us.getBstatus());
        ct.put("descricao", us.getBdescricao());
        ct.put("imagem", us.getBImagem());
        db.insert("BaixaBoletos", null, ct);
    }
    public void upBaixaBoleto(util us){
        ContentValues ct = new ContentValues();
        ct.put("data", us.getBdata());
        ct.put("vencimento", us.getBvencimento());
        ct.put("valor", us.getBvalor());
        ct.put("tipo", us.getBtipo());
        ct.put("status", us.getBstatus());
        ct.put("descricao", us.getBdescricao());
        ct.put("imagem", us.getBImagem());
        db.update("BaixaBoletos", ct, "id = ?", new String[]{String.valueOf(us.getBolId())});
    }
    public void setContasReceber(util us){
        ContentValues ct = new ContentValues();
        ct.put("docto", us.getDocto());
        ct.put("cliente", us.getCliente());
        ct.put("dataCadastro", us.getDataCadastro());
        ct.put("valRecebido", us.getValRecebido());
        ct.put("valDoc", us.getValDoc());
        ct.put("desconto", us.getDesconto());
        ct.put("acrescimo", us.getAcrescimo());
        ct.put("saldo", us.getSaldo());
        ct.put("status", us.getCRStatus());
        db.insert("ContasReceber", null, ct);
    }
    public void upContasReceber(util us){
        ContentValues ct = new ContentValues();
        ct.put("docto", us.getDocto());
        ct.put("cliente", us.getCliente());
        ct.put("dataCadastro", us.getDataCadastro());
        ct.put("valRecebido", us.getValRecebido());
        ct.put("valDoc", us.getValDoc());
        ct.put("desconto", us.getDesconto());
        ct.put("acrescimo", us.getAcrescimo());
        ct.put("saldo", us.getSaldo());
        ct.put("status", us.getCRStatus());
        db.update("ContasReceber", ct, "id = ?", new String[]{String.valueOf(us.getContasReceberId())});
    }

    public void setBoleto(util us){
        ContentValues ct = new ContentValues();
        ct.put("data", us.getBdata());
        ct.put("vencimento", us.getBvencimento());
        ct.put("valor", us.getBvalor());
        ct.put("tipo", us.getBtipo());
        ct.put("status", us.getBstatus());
        ct.put("descricao", us.getBdescricao());
        ct.put("imagem", us.getBImagem());
        db.insert("Boletos", null, ct);
    }
    public void upBoleto(util us){
        ContentValues ct = new ContentValues();
        ct.put("data", us.getBdata());
        ct.put("vencimento", us.getBvencimento());
        ct.put("valor", us.getBvalor());
        ct.put("tipo", us.getBtipo());
        ct.put("status", us.getBstatus());
        ct.put("descricao", us.getBdescricao());
        ct.put("imagem", us.getBImagem());
        db.update("Boletos", ct, "id = ?", new String[]{String.valueOf(us.getBolId())});
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
    public List<util> getContasReceber(){
        ArrayList<util> arr = new ArrayList<util>();
        String[] cl = {"id", "docto", "cliente", "dataCadastro", "valRecebido", "valDoc", "desconto", "acrescimo", "saldo", "status"};
        Cursor cs = db.query("ContasReceber", cl, null, null, null, null, "docto ASC");
        if(cs.getCount() >0){
            cs.moveToFirst();
            do {
                util us = new util();
                us.setContasReceberId(Integer.parseInt(cs.getString(0)));
                us.setDocto(cs.getString(1));
                us.setCliente(cs.getString(2));
                us.setDataCadastro(cs.getString(3));
                us.setValRecebido(cs.getString(4));
                us.setValDoc(cs.getString(5));
                us.setDesconto(cs.getString(6));
                us.setAcrescimo(cs.getString(7));
                us.setSaldo(cs.getString(8));
                us.setCrstatus(cs.getString(9));
                arr.add(us);
            } while(cs.moveToNext());
        }
        return arr;
    }
    public List<util> getBoletos(){
        ArrayList<util> arr = new ArrayList<util>();
        String[] cl = {"id", "data", "vencimento", "valor", "tipo", "status", "descricao", "imagem"};
        Cursor cs = db.query("Boletos", cl, null, null, null, null, "data ASC");
        if(cs.getCount() >0){
            cs.moveToFirst();
            do{
                util us = new util();
                us.setBolId(Integer.parseInt(cs.getString(0)));
                us.setBdata(cs.getString(1));
                us.setBvencimento(cs.getString(2));
                us.setBvalor(cs.getString(3));
                us.setBtipo(cs.getString(4));
                us.setBstatus(cs.getString(5));
                us.setBdescricao(cs.getString(6));
                us.setBImagem(cs.getBlob(7));
                arr.add(us);
            } while(cs.moveToNext());
        }
        return arr;
    }
    public List<util> getBaixaBoletos(){
        ArrayList<util> arr = new ArrayList<util>();
        String[] cl = {"id", "data", "vencimento", "valor", "tipo", "status", "descricao", "imagem"};
        Cursor cs = db.query("BaixaBoletos", cl, null, null, null, null, "data ASC");
        if(cs.getCount() >0){
            cs.moveToFirst();
            do{
                util us = new util();
                us.setBolId(Integer.parseInt(cs.getString(0)));
                us.setBdata(cs.getString(1));
                us.setBvencimento(cs.getString(2));
                us.setBvalor(cs.getString(3));
                us.setBtipo(cs.getString(4));
                us.setBstatus(cs.getString(5));
                us.setBdescricao(cs.getString(6));
                us.setBImagem(cs.getBlob(7));
                arr.add(us);
            } while(cs.moveToNext());
        }
        return arr;
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
    public void setSenhaMCR(util us){
        ContentValues ct = new ContentValues();
        ct.put("usuario", us.getUsuario());
        ct.put("senha", us.getSenha());
        db.insert("senhaMCR", null, ct);
    }
    public void upSenhaMCR(util us){
        ContentValues ct = new ContentValues();
        ct.put("userId", us.getUserId());
        ct.put("usuario", us.getUsuario());
        ct.put("senha", us.getSenha());
        db.update("senhaMCR", ct, "userId = ?", new String[]{String.valueOf(us.getUserId())});
    }
    public void setSenhaCM(util us){
        ContentValues ct = new ContentValues();
        ct.put("usuario", us.getUsuario());
        ct.put("senha", us.getSenha());
        db.insert("senhaCM", null, ct);
    }
    public void upSenhaCM(util us){
        ContentValues ct = new ContentValues();
        ct.put("userId", us.getUserId());
        ct.put("usuario", us.getUsuario());
        ct.put("senha", us.getSenha());
        db.update("senhaCM", ct, "userId = ?", new String[]{String.valueOf(us.getUserId())});
    }
    public void setRetPass(util us){
        ContentValues ct = new ContentValues();
        ct.put("usuario", us.getUsuario());
        ct.put("senha", us.getSenha());
        db.insert("retPass", null, ct);
    }
    public void upRetPass(util us){
        ContentValues ct = new ContentValues();
        ct.put("id", us.getUserId());
        ct.put("usuario", us.getUsuario());
        ct.put("senha", us.getSenha());
        db.update("retPass", ct, "id = ?", new String[]{String.valueOf(us.getUserId())});
    }
    public void setSuperVisor(util us){
        ContentValues ct = new ContentValues();
        ct.put("superV", us.getSenhaSuperVisor());
        db.insert("supervisor", null, ct);
    }
    public void upSuperVisor(util us){
        ContentValues ct = new ContentValues();
        ct.put("id", us.getSupervisor_id());
        ct.put("superV", us.getSenhaSuperVisor());
        db.update("supervisor", ct, "id = ?", new String[]{String.valueOf(us.getSupervisor_id())});
    }
    public util getSuperVisor(long id){
        util us2 = new util();
        Cursor cs = db.rawQuery("select * from supervisor WHERE id ="+id+"", null);
        if(cs.moveToFirst()){
            do {
                us2.setSupervisor_id(cs.getLong(cs.getColumnIndex("id")));
                us2.setSenhaSuperVisor(cs.getString(cs.getColumnIndex("superV")));

            } while(cs.moveToNext());
        }
        return us2;
    }

    public util getSenhaCM(long id){
        util us = new util();
        Cursor cs = db.rawQuery("select * from senhaCM WHERE userId =" + id + "", null);
        if(cs.moveToFirst()){
            do {
                us.setUserId(cs.getLong(cs.getColumnIndex("userId")));
                us.setUsuario(cs.getString(cs.getColumnIndex("usuario")));
                us.setSenha(cs.getString(cs.getColumnIndex("senha")));

            } while(cs.moveToNext());
        }
        return us;
    }
    public util getSenhaRet(long id){
        util us = new util();
        Cursor cs = db.rawQuery("select * from retPass WHERE id =" +id+ "", null);
        if(cs.moveToFirst()){
            do {
                us.setUserId(cs.getLong(cs.getColumnIndex("id")));
                us.setUsuario(cs.getString(cs.getColumnIndex("usuario")));
                us.setSenha(cs.getString(cs.getColumnIndex("senha")));

            } while(cs.moveToNext());
        }
        return us;
    }
    public util getSenhaMCR(long id){
        util us = new util();
        Cursor cs = db.rawQuery("select * from senhaMCR WHERE userId =" + id + "", null);
        if(cs.moveToFirst()){
            do {
                us.setUserId(cs.getLong(cs.getColumnIndex("userId")));
                us.setUsuario(cs.getString(cs.getColumnIndex("usuario")));
                us.setSenha(cs.getString(cs.getColumnIndex("senha")));

            } while(cs.moveToNext());
        }
        return us;
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
        String[] cl = {"id", "data", "NfeHtml"};
        Cursor cs = db.query("Fechamento", cl, null, null, null, null, "data ASC");
        if(cs.getCount() >0){
            cs.moveToFirst();
            do{
                util us = new util();
                us.setId(Integer.parseInt(cs.getString(0)));
                us.setData(cs.getString(1));
                us.setNfehtml(cs.getString(2));

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
