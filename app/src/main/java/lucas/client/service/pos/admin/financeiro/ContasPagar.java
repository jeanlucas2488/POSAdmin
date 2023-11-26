package lucas.client.service.pos.admin.financeiro;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
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

public class ContasPagar extends AppCompatActivity
{
    List<util> lt;
    Context c = this;
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
                AlertDialog.Builder b = new AlertDialog.Builder(c);
                b.setTitle("Cadastrar:");
                b.setView(r);
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

            }
        });
    }
}