package lucas.client.service.pos.admin.financeiro.baixas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.etc.util;
import lucas.client.service.pos.admin.financeiro.adapter.BoletosAdapter;
import lucas.client.service.pos.admin.financeiro.view.boletoViewImageSource;
import lucas.client.service.pos.admin.sqlite.SQLiteControl;

public class baixaBoletos extends AppCompatActivity {

    Context c = this;
    TextInputEditText data, vencimento, valor, tipo, status, descricao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baixas_boletos);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        SQLiteControl db = new SQLiteControl(c);
        List<util> lt = db.getBaixaBoletos();
        ImageButton add = (ImageButton) findViewById(R.id.contasAdd);
        ListView l = (ListView) findViewById(R.id.list);
        l.setEmptyView(findViewById(android.R.id.empty));
        l.setAdapter(new BoletosAdapter(c, lt));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.cadastro_boletos, null);
                data = r.findViewById(R.id.bdata);
                vencimento = r.findViewById(R.id.bvencimento);
                valor = r.findViewById(R.id.bvalor);
                tipo = r.findViewById(R.id.btipo);
                status = r.findViewById(R.id.status);
                descricao = r.findViewById(R.id.bdesc);
                ImageView im = r.findViewById(R.id.im);
                ImageView up_im = r.findViewById(R.id.up_im);
                RelativeLayout rel = r.findViewById(R.id.laySel);
                RelativeLayout rel2 = r.findViewById(R.id.imres);
                up_im.setVisibility(View.GONE);
                rel.setVisibility(View.GONE);
                rel2.setVisibility(View.VISIBLE);
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        byte[] test = lt.get(position).getBImagem();
                        Intent it = new Intent(c, boletoViewImageSource.class);
                        Bundle b = new Bundle();
                        b.putByteArray("image", test);
                        it.putExtras(b);
                        startActivity(it);
                    }
                });
                data.setText(lt.get(position).getBdata());
                vencimento.setText(lt.get(position).getBvencimento());
                valor.setText(lt.get(position).getBvalor());
                tipo.setText(lt.get(position).getBtipo());
                status.setText(lt.get(position).getBstatus());
                descricao.setText(lt.get(position).getBdescricao());
                byte[] res = lt.get(position).getBImagem();
                Bitmap bt = BitmapFactory.decodeByteArray(res, 0, res.length);
                im.setImageBitmap(bt);
                AlertDialog.Builder al = new AlertDialog.Builder(c);
                al.setTitle("Visualizar Baixa:");
                al.setView(r);
                al.setPositiveButton("OK", null);
                al.create();
                al.show();
            }
        });
    }
}
