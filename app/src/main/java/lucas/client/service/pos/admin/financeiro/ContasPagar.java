package lucas.client.service.pos.admin.financeiro;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.etc.util;
import lucas.client.service.pos.admin.financeiro.adapter.ContasReceberAdapter;
import lucas.client.service.pos.admin.sqlite.SQLiteControl;

public class ContasPagar extends AppCompatActivity
{
    List<util> lt;
    Context c = this;
    TextInputEditText codigo, clasif, valPagar, DVencimento, empresa, CBancaria, ValorPAg, dataAg,
                      pessoa, dataComp, descAg, comentarios, status, valPago, saldoPagar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contas_pagar);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        SQLiteControl db = new SQLiteControl(c);
        lt = db.getContas();
        ImageButton cadd = (ImageButton) findViewById(R.id.contasAdd);
        cadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.cadastro_contas_pagar, null);
                codigo = r.findViewById(R.id.codigo);
                clasif = r.findViewById(R.id.classificacao);
                valPagar = r.findViewById(R.id.valorPagar);
                DVencimento = r.findViewById(R.id.vencimento);
                empresa = r.findViewById(R.id.empresa);
                CBancaria = r.findViewById(R.id.conta_bancaria);
                ValorPAg = r.findViewById(R.id.valorPago);
                dataAg = r.findViewById(R.id.dataAg);
                pessoa = r.findViewById(R.id.pessoa);
                dataComp = r.findViewById(R.id.dataComp);
                descAg = r.findViewById(R.id.descAg);
                comentarios = r.findViewById(R.id.comentarios);
                status = r.findViewById(R.id.status);
                valPago = r.findViewById(R.id.valorPago);
                saldoPagar = r.findViewById(R.id.saldopagar);

                AlertDialog.Builder b = new AlertDialog.Builder(c);
                b.setTitle("Cadastrar:");
                b.setView(r);
                b.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        util us = new util();
                        us.setContasCodigo(codigo.getText().toString());
                        us.setClassificacao(clasif.getText().toString());
                        us.setValor_pagar(valPagar.getText().toString());
                        us.setData_vencimento(DVencimento.getText().toString());
                        us.setEmpresa(empresa.getText().toString());
                        us.setConta_bancaria(CBancaria.getText().toString());
                        us.setValor_pagar_ag(ValorPAg.getText().toString());
                        us.setData_ag(dataAg.getText().toString());
                        us.setPessoa(pessoa.getText().toString());
                        us.setData_comp(dataComp.getText().toString());
                        us.setDesc_ag(descAg.getText().toString());
                        us.setComentarios(comentarios.getText().toString());
                        us.setStatus(status.getText().toString());
                        us.setValor_pago(valPago.getText().toString());
                        us.setSaldo_pagar(saldoPagar.getText().toString());
                        SQLiteControl db = new SQLiteControl(c);
                        db.setContasPagar(us);
                        try {
                            File sd = Environment.getExternalStorageDirectory();
                            File data = Environment.getDataDirectory();

                            if (sd.canWrite()) {
                                String  currentDBPath= "//data//" + c.getOpPackageName()
                                        + "//databases//" + "myDB.db";
                                String  currentDBPath8 = "//data//" + c.getOpPackageName()
                                        + "//databases//" + "myDB.db-shm";
                                String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                        + "//databases//" + "myDB.db-wal";

                                String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/myDB.db";
                                String backupDBPath8  = "pdvMain/data/lucas.client.service/.sqlite/myDB.db-shm";
                                String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/myDB.db-wal";

                                File currentDB = new File(data, currentDBPath);
                                File currentDB8 = new File(data, currentDBPath8);
                                File currentDB3 = new File(data, currentDBPath3);
                                File backupDB = new File(sd, backupDBPath);
                                File backupDB8 = new File(sd, backupDBPath8);
                                File backupDB3 = new File(sd, backupDBPath3);

                                if(currentDB8.exists()){
                                    FileChannel src = new FileInputStream(currentDB8).getChannel();
                                    FileChannel dst = new FileOutputStream(backupDB8).getChannel();
                                    dst.transferFrom(src, 0, src.size());
                                    src.close();
                                    dst.close();
                                }
                                if(currentDB3.exists()){
                                    FileChannel src = new FileInputStream(currentDB3).getChannel();
                                    FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                    dst.transferFrom(src, 0, src.size());
                                    src.close();
                                    dst.close();
                                }
                                FileChannel src = new FileInputStream(currentDB).getChannel();
                                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                dst.transferFrom(src, 0, src.size());
                                src.close();
                                dst.close();
                            }
                        } catch (Exception e8) {

                        }
                    }
                });
                b.setNegativeButton("Cancelar", null);
                b.create();
                b.show();
            }
        });
        ListView l = (ListView) findViewById(R.id.list);
        l.setEmptyView(findViewById(android.R.id.empty));
        l.setAdapter(new ContasReceberAdapter(c, lt));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.cadastro_contas_pagar, null);
                codigo = r.findViewById(R.id.codigo);
                clasif = r.findViewById(R.id.classificacao);
                valPagar = r.findViewById(R.id.valorPagar);
                DVencimento = r.findViewById(R.id.vencimento);
                empresa = r.findViewById(R.id.empresa);
                CBancaria = r.findViewById(R.id.conta_bancaria);
                ValorPAg = r.findViewById(R.id.valorPago);
                dataAg = r.findViewById(R.id.dataAg);
                pessoa = r.findViewById(R.id.pessoa);
                dataComp = r.findViewById(R.id.dataComp);
                descAg = r.findViewById(R.id.descAg);
                comentarios = r.findViewById(R.id.comentarios);
                status = r.findViewById(R.id.status);
                valPago = r.findViewById(R.id.valorPago);
                saldoPagar = r.findViewById(R.id.saldopagar);

                AlertDialog.Builder b = new AlertDialog.Builder(c);
                b.setTitle("Cadastrar:");
                b.setView(r);
                b.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        util us = new util();
                        us.setContasId(lt.get(position).getContasId());
                        us.setContasCodigo(codigo.getText().toString());
                        us.setClassificacao(clasif.getText().toString());
                        us.setValor_pagar(valPagar.getText().toString());
                        us.setData_vencimento(DVencimento.getText().toString());
                        us.setEmpresa(empresa.getText().toString());
                        us.setConta_bancaria(CBancaria.getText().toString());
                        us.setValor_pagar_ag(ValorPAg.getText().toString());
                        us.setData_ag(dataAg.getText().toString());
                        us.setPessoa(pessoa.getText().toString());
                        us.setData_comp(dataComp.getText().toString());
                        us.setDesc_ag(descAg.getText().toString());
                        us.setComentarios(comentarios.getText().toString());
                        us.setStatus(status.getText().toString());
                        us.setValor_pago(valPago.getText().toString());
                        us.setSaldo_pagar(saldoPagar.getText().toString());
                        SQLiteControl db = new SQLiteControl(c);
                        db.upContasPagar(us);
                        lt.clear();
                        lt.addAll(db.getContas());
                        try {
                            File sd = Environment.getExternalStorageDirectory();
                            File data = Environment.getDataDirectory();

                            if (sd.canWrite()) {
                                String  currentDBPath= "//data//" + c.getOpPackageName()
                                        + "//databases//" + "myDB.db";
                                String  currentDBPath8 = "//data//" + c.getOpPackageName()
                                        + "//databases//" + "myDB.db-shm";
                                String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                        + "//databases//" + "myDB.db-wal";

                                String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/myDB.db";
                                String backupDBPath8  = "pdvMain/data/lucas.client.service/.sqlite/myDB.db-shm";
                                String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/myDB.db-wal";

                                File currentDB = new File(data, currentDBPath);
                                File currentDB8 = new File(data, currentDBPath8);
                                File currentDB3 = new File(data, currentDBPath3);
                                File backupDB = new File(sd, backupDBPath);
                                File backupDB8 = new File(sd, backupDBPath8);
                                File backupDB3 = new File(sd, backupDBPath3);

                                if(currentDB8.exists()){
                                    FileChannel src = new FileInputStream(currentDB8).getChannel();
                                    FileChannel dst = new FileOutputStream(backupDB8).getChannel();
                                    dst.transferFrom(src, 0, src.size());
                                    src.close();
                                    dst.close();
                                }
                                if(currentDB3.exists()){
                                    FileChannel src = new FileInputStream(currentDB3).getChannel();
                                    FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                    dst.transferFrom(src, 0, src.size());
                                    src.close();
                                    dst.close();
                                }
                                FileChannel src = new FileInputStream(currentDB).getChannel();
                                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                dst.transferFrom(src, 0, src.size());
                                src.close();
                                dst.close();
                            }
                        } catch (Exception e8) {

                        }
                    }
                });
                b.setNegativeButton("Cancelar", null);
                b.create();
                b.show();
            }
        });
    }
}