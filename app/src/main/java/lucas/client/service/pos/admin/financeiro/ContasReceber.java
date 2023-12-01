package lucas.client.service.pos.admin.financeiro;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.etc.util;
import lucas.client.service.pos.admin.financeiro.adapter.ContasReceberAdapter;
import lucas.client.service.pos.admin.sqlite.SQLiteControl;

public class ContasReceber extends AppCompatActivity
{
    ContasReceberAdapter ad;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contas_receber);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        SQLiteControl db = new SQLiteControl(c);
        List<util> lt = db.getContasReceber();
        ad = new ContasReceberAdapter(this, lt);
        ImageButton add = (ImageButton) findViewById(R.id.add);
        ListView l = (ListView) findViewById(R.id.list);
        l.setAdapter(ad);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.cadastro_contas_receber, null);
                final TextInputEditText doc = r.findViewById(R.id.doc);
                final TextInputEditText cliente = r.findViewById(R.id.cliente);
                final TextInputEditText dataCad = r.findViewById(R.id.dataCadastro);
                final TextInputEditText valRecebido = r.findViewById(R.id.valrecebido);
                final TextInputEditText valdoc = r.findViewById(R.id.valdoc);
                final TextInputEditText desconto = r.findViewById(R.id.desconto);
                final TextInputEditText acrescimo = r.findViewById(R.id.acrescimo);
                final TextInputEditText saldo = r.findViewById(R.id.saldo);
                final TextInputEditText status = r.findViewById(R.id.status);


                AlertDialog.Builder bs = new AlertDialog.Builder(c);
                bs.setTitle("Cadastro Contas Receber");
                bs.setView(r);
                bs.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        util us = new util();
                        us.setDocto(doc.getText().toString());
                        us.setCliente(cliente.getText().toString());
                        us.setDataCadastro(dataCad.getText().toString());
                        us.setValRecebido(valRecebido.getText().toString());
                        us.setValDoc(valdoc.getText().toString());
                        us.setDesconto(desconto.getText().toString());
                        us.setAcrescimo(acrescimo.getText().toString());
                        us.setSaldo(saldo.getText().toString());
                        us.setCrstatus(status.getText().toString());
                        SQLiteControl db = new SQLiteControl(c);
                        db.setContasReceber(us);
                        lt.clear();
                        lt.addAll(db.getContasReceber());
                        ad.notifyDataSetChanged();
                    }
                });
                bs.setNegativeButton("Cancelar", null);
                bs.create();
                bs.show();
            }
        });
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.cadastro_contas_receber, null);
                final TextInputEditText doc = r.findViewById(R.id.doc);
                final TextInputEditText cliente = r.findViewById(R.id.cliente);
                final TextInputEditText dataCad = r.findViewById(R.id.dataCadastro);
                final TextInputEditText valRecebido = r.findViewById(R.id.valrecebido);
                final TextInputEditText valdoc = r.findViewById(R.id.valdoc);
                final TextInputEditText desconto = r.findViewById(R.id.desconto);
                final TextInputEditText acrescimo = r.findViewById(R.id.acrescimo);
                final TextInputEditText saldo = r.findViewById(R.id.saldo);
                final TextInputEditText status = r.findViewById(R.id.status);

                doc.setText(lt.get(position).getDocto());
                cliente.setText(lt.get(position).getCliente());
                dataCad.setText(lt.get(position).getDataCadastro());
                valRecebido.setText(lt.get(position).getValRecebido());
                valdoc.setText(lt.get(position).getValDoc());
                desconto.setText(lt.get(position).getDesconto());
                acrescimo.setText(lt.get(position).getAcrescimo());
                saldo.setText(lt.get(position).getSaldo());
                status.setText(lt.get(position).getCRStatus());

                AlertDialog.Builder bs = new AlertDialog.Builder(c);
                bs.setTitle("Visualizar Ficha de Cadastro");
                bs.setView(r);
                bs.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        util us = new util();
                        us.setContasReceberId(lt.get(position).getContasReceberId());
                        us.setDocto(doc.getText().toString());
                        us.setCliente(cliente.getText().toString());
                        us.setDataCadastro(dataCad.getText().toString());
                        us.setValRecebido(valRecebido.getText().toString());
                        us.setValDoc(valdoc.getText().toString());
                        us.setDesconto(desconto.getText().toString());
                        us.setAcrescimo(acrescimo.getText().toString());
                        us.setSaldo(saldo.getText().toString());
                        us.setCrstatus(status.getText().toString());
                        SQLiteControl db = new SQLiteControl(c);
                        db.upContasReceber(us);
                        lt.clear();
                        lt.addAll(db.getContasReceber());
                        ad.notifyDataSetChanged();
                    }
                });
                bs.setNegativeButton("Cancelar", null);
                bs.create();
                bs.show();
            }
        });
    }
}