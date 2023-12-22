package lucas.client.service.pos.admin.mercearia;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lucas.client.service.pos.admin.MainActivity;
import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.adapters.adapter;
import lucas.client.service.pos.admin.adapters.financeiroAdapter;
import lucas.client.service.pos.admin.etc.util;
import lucas.client.service.pos.admin.financeiro.ContasPagar;
import lucas.client.service.pos.admin.financeiro.ContasReceber;
import lucas.client.service.pos.admin.financeiro.Fechamento;
import lucas.client.service.pos.admin.mercearia.databases.SQLiteControl;
import lucas.client.service.pos.admin.setup.Estoque;

public class MCRMain extends AppCompatActivity {
    Context c = this;
    AlertDialog al, cat;
    String lstrlinha;
    TextInputEditText forn;
    EditText fil;
    public ListView lt, lr;
    List<util> pagina1, pagina2, pagina3, pagina4, pagina5, pagina6, pagina7, pagina8, pagina9, pagina10,
            aba1, aba2, aba3, aba4, aba5, aba6, aba7, aba8, aba9, aba10;
    long id;
    public String dirPath="";
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final String TAG = "PERMISSION_TAG";
    public String ParentdirPath="";
    public ArrayList<String> theNamesOfFiles;
    public ArrayList<Integer> intImages;
    public File dir;
    AlertDialog root;
    CustomList customList;
    adapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        SQLiteControl sql = new SQLiteControl(c);
        aba1 = sql.findP1();
        aba2 = sql.findP2();
        aba3 = sql.findP3();
        aba4 = sql.findP4();
        aba5 = sql.findP5();
        aba6 = sql.findP6();
        aba7 = sql.findP7();
        aba8 = sql.findP8();
        aba9 = sql.findP9();
        aba10 = sql.findP10();
        ImageButton fin = (ImageButton) findViewById(R.id.financeiro);
        fin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.financeiro, null);
                ListView l = r.findViewById(R.id.list);
                List<String> lop = new ArrayList<String>();
                lop.add("Fechamento");
                lop.add("Contas a Pagar");
                lop.add("Contas a Receber");
                lop.add("Relatórios");

                l.setAdapter(new financeiroAdapter(c, lop));
                l.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
                    {
                        // TODO: Implement this method
                        switch(p3){
                            case 0:
                                Intent it = new Intent(c, Fechamento.class);
                                startActivity(it);
                                break;
                            case 1:
                                Intent it2 = new Intent(c, ContasPagar.class);
                                startActivity(it2);
                                break;
                            case 2:
                                Intent it3 = new Intent(c, ContasReceber.class);
                                startActivity(it3);
                                break;
                        }

                    }
                });
                AlertDialog.Builder b = new AlertDialog.Builder(c);
                b.setTitle("Escolha uma Opção:");
                b.setView(r);
                b.create();
                b.show();
            }
        });
        ImageButton est = (ImageButton) findViewById(R.id.estoque);
        est.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                Intent it = new Intent(c, Estoque.class);
                startActivity(it);
            }
        });

        final Button sh10 = (Button) findViewById(R.id.show10);
        final CardView cv10 = (CardView) findViewById(R.id.cv10);
        TextView tvC10 = (TextView) findViewById(R.id.nameTxt10);
        try{
            if(!aba10.get(0).getProd().equals("")){
                util cat = sql.getCategory(10);
                tvC10.setText(cat.getCategory());
                cv10.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            cv10.setVisibility(View.GONE);
        }
        sh10.setText("Mostrar");
        sh10.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.view_product, null);
                SearchView sc = r.findViewById(R.id.search);
                final ListView lv1 = r.findViewById(R.id.list);
                ad = new adapter(c, aba10);
                lv1.setAdapter(ad);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        ad.filter(s);
                        return false;
                    }
                });
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p10, final int p3, long p4)
                    {
                        // TODO: Implement this method
                        final String[] opts = {
                                "Editar",
                                "Excluír",
                        };
                        AlertDialog.Builder bop = new AlertDialog.Builder(c);
                        bop.setTitle("Escolha uma Opção!");
                        bop.setItems(opts, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p10)
                            {
                                // TODO: Implement this method
                                if(opts[p10].toString().startsWith("Editar")){
                                    LayoutInflater li = getLayoutInflater();
                                    View r = li.inflate(R.layout.cadview, null);
                                    final TextInputEditText pro = r.findViewById(R.id.pessoa);
                                    final TextInputEditText quant = r.findViewById(R.id.quant);
                                    final TextInputEditText valor = r.findViewById(R.id.valor);

                                    pro.setText(aba10.get(p3).getProd());
                                    quant.setText(aba10.get(p3).getQuant());
                                    valor.setText(aba10.get(p3).getValor());
                                    pro.setEnabled(false);
                                    AlertDialog.Builder b = new AlertDialog.Builder(c);
                                    b.setTitle("Editar Produto!");
                                    b.setView(r);
                                    b.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface p1, int p10)
                                        {
                                            // TODO: Implement this method
                                            util us = new util();
                                            us.setId(aba10.get(p3).getId());
                                            us.setTable("paginaTen");
                                            us.setProd(pro.getText().toString());
                                            us.setQuant(quant.getText().toString());
                                            us.setValor(valor.getText().toString());
                                            us.setImage(aba10.get(p3).getImage());
                                            SQLiteControl db = new SQLiteControl(c);
                                            db.postUp(us);

                                            try {
                                                File sd = Environment.getExternalStorageDirectory();
                                                File data = Environment.getDataDirectory();

                                                if (sd.canWrite()) {
                                                    String  currentDBPath= "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db";
                                                    String  currentDBPath10 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-shm";
                                                    String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-wal";

                                                    String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                    String backupDBPath10  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                    String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                    File currentDB = new File(data, currentDBPath);
                                                    File currentDB10 = new File(data, currentDBPath10);
                                                    File currentDB3 = new File(data, currentDBPath3);
                                                    File backupDB = new File(sd, backupDBPath);
                                                    File backupDB10 = new File(sd, backupDBPath10);
                                                    File backupDB3 = new File(sd, backupDBPath3);

                                                    if(currentDB10.exists()){
                                                        FileChannel src = new FileInputStream(currentDB10).getChannel();
                                                        FileChannel dst = new FileOutputStream(backupDB10).getChannel();
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
                                            } catch (Exception e10) {

                                            }
                                            Intent it = new Intent(c, MainActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });

                                    b.setNegativeButton("Cancelar", null);
                                    b.setCancelable(false);
                                    b.create();
                                    b.show();
                                }
                                if(opts[p10].toString().startsWith("Excluír")){
                                    SQLiteControl db = new SQLiteControl(c);
                                    util us = new util();
                                    us.setId(aba10.get(p3).getId());
                                    us.setTable("paginaTen");
                                    db.delete(us);

                                    try {
                                        File sd = Environment.getExternalStorageDirectory();
                                        File data = Environment.getDataDirectory();

                                        if (sd.canWrite()) {
                                            String  currentDBPath= "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db";
                                            String  currentDBPath10 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-shm";
                                            String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-wal";

                                            String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                            String backupDBPath10  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                            String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                            File currentDB = new File(data, currentDBPath);
                                            File currentDB10 = new File(data, currentDBPath10);
                                            File currentDB3 = new File(data, currentDBPath3);
                                            File backupDB = new File(sd, backupDBPath);
                                            File backupDB10 = new File(sd, backupDBPath10);
                                            File backupDB3 = new File(sd, backupDBPath3);

                                            if(currentDB10.exists()){
                                                FileChannel src = new FileInputStream(currentDB10).getChannel();
                                                FileChannel dst = new FileOutputStream(backupDB10).getChannel();
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
                                    } catch (Exception e10) {

                                    }
                                    Intent it = new Intent(c, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
                        bop.create();
                        bop.show();
                    }
                });
                AlertDialog.Builder ale = new AlertDialog.Builder(c);
                ale.setTitle("Visualizar Produtos");
                ale.setView(r);
                ale.show();
            }
        });

        final Button sh9 = (Button) findViewById(R.id.show9);
        final CardView cv9 = (CardView) findViewById(R.id.cv9);
        TextView tvC9 = (TextView) findViewById(R.id.nameTxt9);
        try{
            if(!aba9.get(0).getProd().equals("")){
                util cat = sql.getCategory(9);
                tvC9.setText(cat.getCategory());
                cv9.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            cv9.setVisibility(View.GONE);
        }
        sh9.setText("Mostrar");
        sh9.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.view_product, null);
                SearchView sc = r.findViewById(R.id.search);
                final ListView lv1 = r.findViewById(R.id.list);
                ad = new adapter(c, aba9);
                lv1.setAdapter(ad);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        ad.filter(s);
                        return false;
                    }
                });
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p9, final int p3, long p4)
                    {
                        // TODO: Implement this method
                        final String[] opts = {
                                "Editar",
                                "Excluír",
                        };
                        AlertDialog.Builder bop = new AlertDialog.Builder(c);
                        bop.setTitle("Escolha uma Opção!");
                        bop.setItems(opts, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p9)
                            {
                                // TODO: Implement this method
                                if(opts[p9].toString().startsWith("Editar")){
                                    LayoutInflater li = getLayoutInflater();
                                    View r = li.inflate(R.layout.cadview, null);
                                    final EditText pro = r.findViewById(R.id.pessoa);
                                    final EditText quant = r.findViewById(R.id.quant);
                                    final EditText valor = r.findViewById(R.id.valor);

                                    pro.setText(aba9.get(p3).getProd());
                                    quant.setText(aba9.get(p3).getQuant());
                                    valor.setText(aba9.get(p3).getValor());
                                    pro.setEnabled(false);
                                    AlertDialog.Builder b = new AlertDialog.Builder(c);
                                    b.setTitle("Editar Produto!");
                                    b.setView(r);
                                    b.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface p1, int p9)
                                        {
                                            // TODO: Implement this method
                                            util us = new util();
                                            us.setId(aba9.get(p3).getId());
                                            us.setTable("paginaNine");
                                            us.setProd(pro.getText().toString());
                                            us.setQuant(quant.getText().toString());
                                            us.setValor(valor.getText().toString());
                                            us.setImage(aba9.get(p3).getImage());
                                            SQLiteControl db = new SQLiteControl(c);
                                            db.postUp(us);

                                            try {
                                                File sd = Environment.getExternalStorageDirectory();
                                                File data = Environment.getDataDirectory();

                                                if (sd.canWrite()) {
                                                    String  currentDBPath= "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db";
                                                    String  currentDBPath9 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-shm";
                                                    String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-wal";

                                                    String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                    String backupDBPath9  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                    String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                    File currentDB = new File(data, currentDBPath);
                                                    File currentDB9 = new File(data, currentDBPath9);
                                                    File currentDB3 = new File(data, currentDBPath3);
                                                    File backupDB = new File(sd, backupDBPath);
                                                    File backupDB9 = new File(sd, backupDBPath9);
                                                    File backupDB3 = new File(sd, backupDBPath3);

                                                    if(currentDB9.exists()){
                                                        FileChannel src = new FileInputStream(currentDB9).getChannel();
                                                        FileChannel dst = new FileOutputStream(backupDB9).getChannel();
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
                                            } catch (Exception e9) {

                                            }
                                            Intent it = new Intent(c, MainActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });

                                    b.setNegativeButton("Cancelar", null);
                                    b.setCancelable(false);
                                    b.create();
                                    b.show();
                                }
                                if(opts[p9].toString().startsWith("Excluír")){
                                    SQLiteControl db = new SQLiteControl(c);
                                    util us = new util();
                                    us.setId(aba9.get(p3).getId());
                                    us.setTable("paginaNine");
                                    db.delete(us);

                                    try {
                                        File sd = Environment.getExternalStorageDirectory();
                                        File data = Environment.getDataDirectory();

                                        if (sd.canWrite()) {
                                            String  currentDBPath= "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db";
                                            String  currentDBPath9 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-shm";
                                            String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-wal";

                                            String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                            String backupDBPath9  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                            String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                            File currentDB = new File(data, currentDBPath);
                                            File currentDB9 = new File(data, currentDBPath9);
                                            File currentDB3 = new File(data, currentDBPath3);
                                            File backupDB = new File(sd, backupDBPath);
                                            File backupDB9 = new File(sd, backupDBPath9);
                                            File backupDB3 = new File(sd, backupDBPath3);

                                            if(currentDB9.exists()){
                                                FileChannel src = new FileInputStream(currentDB9).getChannel();
                                                FileChannel dst = new FileOutputStream(backupDB9).getChannel();
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
                                    } catch (Exception e9) {

                                    }
                                    Intent it = new Intent(c, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
                        bop.create();
                        bop.show();
                    }
                });
                AlertDialog.Builder ale = new AlertDialog.Builder(c);
                ale.setTitle("Visualizar Produtos");
                ale.setView(r);
                ale.show();
            }
        });

        final Button sh8 = (Button) findViewById(R.id.show8);
        final CardView cv8 = (CardView) findViewById(R.id.cv8);
        TextView tvC8 = (TextView) findViewById(R.id.nameTxt8);
        try{
            if(!aba8.get(0).getProd().equals("")){
                util cat = sql.getCategory(8);
                tvC8.setText(cat.getCategory());
                cv8.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            cv8.setVisibility(View.GONE);
        }
        sh8.setText("Mostrar");
        sh8.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.view_product, null);
                SearchView sc = r.findViewById(R.id.search);
                final ListView lv1 = r.findViewById(R.id.list);
                ad = new adapter(c, aba8);
                lv1.setAdapter(ad);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        ad.filter(s);
                        return false;
                    }
                });
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p8, final int p3, long p4)
                    {
                        // TODO: Implement this method
                        final String[] opts = {
                                "Editar",
                                "Excluír",
                        };
                        AlertDialog.Builder bop = new AlertDialog.Builder(c);
                        bop.setTitle("Escolha uma Opção!");
                        bop.setItems(opts, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p8)
                            {
                                // TODO: Implement this method
                                if(opts[p8].toString().startsWith("Editar")){
                                    LayoutInflater li = getLayoutInflater();
                                    View r = li.inflate(R.layout.cadview, null);
                                    final EditText pro = r.findViewById(R.id.pessoa);
                                    final EditText quant = r.findViewById(R.id.quant);
                                    final EditText valor = r.findViewById(R.id.valor);

                                    pro.setText(aba8.get(p3).getProd());
                                    quant.setText(aba8.get(p3).getQuant());
                                    valor.setText(aba8.get(p3).getValor());
                                    pro.setEnabled(false);
                                    AlertDialog.Builder b = new AlertDialog.Builder(c);
                                    b.setTitle("Editar Produto!");
                                    b.setView(r);
                                    b.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface p1, int p8)
                                        {
                                            // TODO: Implement this method
                                            util us = new util();
                                            us.setId(aba8.get(p3).getId());
                                            us.setTable("paginaEight");
                                            us.setProd(pro.getText().toString());
                                            us.setQuant(quant.getText().toString());
                                            us.setValor(valor.getText().toString());
                                            us.setImage(aba8.get(p3).getImage());
                                            SQLiteControl db = new SQLiteControl(c);
                                            db.postUp(us);

                                            try {
                                                File sd = Environment.getExternalStorageDirectory();
                                                File data = Environment.getDataDirectory();

                                                if (sd.canWrite()) {
                                                    String  currentDBPath= "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db";
                                                    String  currentDBPath8 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-shm";
                                                    String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-wal";

                                                    String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                    String backupDBPath8  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                    String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

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
                                            Intent it = new Intent(c, MainActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });

                                    b.setNegativeButton("Cancelar", null);
                                    b.setCancelable(false);
                                    b.create();
                                    b.show();
                                }
                                if(opts[p8].toString().startsWith("Excluír")){
                                    SQLiteControl db = new SQLiteControl(c);
                                    util us = new util();
                                    us.setId(aba8.get(p3).getId());
                                    us.setTable("paginaEight");
                                    db.delete(us);

                                    try {
                                        File sd = Environment.getExternalStorageDirectory();
                                        File data = Environment.getDataDirectory();

                                        if (sd.canWrite()) {
                                            String  currentDBPath= "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db";
                                            String  currentDBPath8 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-shm";
                                            String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-wal";

                                            String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                            String backupDBPath8  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                            String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

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
                                    Intent it = new Intent(c, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
                        bop.create();
                        bop.show();
                    }
                });
                AlertDialog.Builder ale = new AlertDialog.Builder(c);
                ale.setTitle("Visualizar Produtos");
                ale.setView(r);
                ale.show();
            }
        });

        final Button sh7 = (Button) findViewById(R.id.show7);
        final CardView cv7 = (CardView) findViewById(R.id.cv7);
        TextView tvC7 = (TextView) findViewById(R.id.nameTxt7);
        try{
            if(!aba7.get(0).getProd().equals("")){
                util cat = sql.getCategory(7);
                tvC7.setText(cat.getCategory());
                cv7.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            cv7.setVisibility(View.GONE);
        }
        sh7.setText("Mostrar");
        sh7.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.view_product, null);
                SearchView sc = r.findViewById(R.id.search);
                final ListView lv1 = r.findViewById(R.id.list);
                ad = new adapter(c, aba7);
                lv1.setAdapter(ad);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        ad.filter(s);
                        return false;
                    }
                });
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p7, final int p3, long p4)
                    {
                        // TODO: Implement this method
                        final String[] opts = {
                                "Editar",
                                "Excluír",
                        };
                        AlertDialog.Builder bop = new AlertDialog.Builder(c);
                        bop.setTitle("Escolha uma Opção!");
                        bop.setItems(opts, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p7)
                            {
                                // TODO: Implement this method
                                if(opts[p7].toString().startsWith("Editar")){
                                    LayoutInflater li = getLayoutInflater();
                                    View r = li.inflate(R.layout.cadview, null);
                                    final EditText pro = r.findViewById(R.id.pessoa);
                                    final EditText quant = r.findViewById(R.id.quant);
                                    final EditText valor = r.findViewById(R.id.valor);

                                    pro.setText(aba7.get(p3).getProd());
                                    quant.setText(aba7.get(p3).getQuant());
                                    valor.setText(aba7.get(p3).getValor());
                                    pro.setEnabled(false);
                                    AlertDialog.Builder b = new AlertDialog.Builder(c);
                                    b.setTitle("Editar Produto!");
                                    b.setView(r);
                                    b.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface p1, int p7)
                                        {
                                            // TODO: Implement this method
                                            util us = new util();
                                            us.setId(aba7.get(p3).getId());
                                            us.setTable("paginaSeven");
                                            us.setProd(pro.getText().toString());
                                            us.setQuant(quant.getText().toString());
                                            us.setValor(valor.getText().toString());
                                            us.setImage(aba7.get(p3).getImage());
                                            SQLiteControl db = new SQLiteControl(c);
                                            db.postUp(us);

                                            try {
                                                File sd = Environment.getExternalStorageDirectory();
                                                File data = Environment.getDataDirectory();

                                                if (sd.canWrite()) {
                                                    String  currentDBPath= "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db";
                                                    String  currentDBPath7 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-shm";
                                                    String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-wal";

                                                    String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                    String backupDBPath7  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                    String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                    File currentDB = new File(data, currentDBPath);
                                                    File currentDB7 = new File(data, currentDBPath7);
                                                    File currentDB3 = new File(data, currentDBPath3);
                                                    File backupDB = new File(sd, backupDBPath);
                                                    File backupDB7 = new File(sd, backupDBPath7);
                                                    File backupDB3 = new File(sd, backupDBPath3);

                                                    if(currentDB7.exists()){
                                                        FileChannel src = new FileInputStream(currentDB7).getChannel();
                                                        FileChannel dst = new FileOutputStream(backupDB7).getChannel();
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
                                            } catch (Exception e7) {

                                            }
                                            Intent it = new Intent(c, MainActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });

                                    b.setNegativeButton("Cancelar", null);
                                    b.setCancelable(false);
                                    b.create();
                                    b.show();
                                }
                                if(opts[p7].toString().startsWith("Excluír")){
                                    SQLiteControl db = new SQLiteControl(c);
                                    util us = new util();
                                    us.setId(aba7.get(p3).getId());
                                    us.setTable("paginaSeven");
                                    db.delete(us);

                                    try {
                                        File sd = Environment.getExternalStorageDirectory();
                                        File data = Environment.getDataDirectory();

                                        if (sd.canWrite()) {
                                            String  currentDBPath= "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db";
                                            String  currentDBPath7 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-shm";
                                            String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-wal";

                                            String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                            String backupDBPath7  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                            String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                            File currentDB = new File(data, currentDBPath);
                                            File currentDB7 = new File(data, currentDBPath7);
                                            File currentDB3 = new File(data, currentDBPath3);
                                            File backupDB = new File(sd, backupDBPath);
                                            File backupDB7 = new File(sd, backupDBPath7);
                                            File backupDB3 = new File(sd, backupDBPath3);

                                            if(currentDB7.exists()){
                                                FileChannel src = new FileInputStream(currentDB7).getChannel();
                                                FileChannel dst = new FileOutputStream(backupDB7).getChannel();
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
                                    } catch (Exception e7) {

                                    }
                                    Intent it = new Intent(c, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
                        bop.create();
                        bop.show();
                    }
                });
                AlertDialog.Builder ale = new AlertDialog.Builder(c);
                ale.setTitle("Visualizar Produtos");
                ale.setView(r);
                ale.show();
            }
        });

        final Button sh6 = (Button) findViewById(R.id.show6);
        final CardView cv6 = (CardView) findViewById(R.id.cv6);
        TextView tvC6 = (TextView) findViewById(R.id.nameTxt6);
        try{
            if(!aba6.get(0).getProd().equals("")){
                util cat = sql.getCategory(6);
                tvC6.setText(cat.getCategory());
                cv6.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            cv6.setVisibility(View.GONE);
        }
        sh6.setText("Mostrar");
        sh6.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.view_product, null);
                SearchView sc = r.findViewById(R.id.search);
                final ListView lv1 = r.findViewById(R.id.list);
                ad = new adapter(c, aba6);
                lv1.setAdapter(ad);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        ad.filter(s);
                        return false;
                    }
                });
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p6, final int p3, long p4)
                    {
                        // TODO: Implement this method
                        final String[] opts = {
                                "Editar",
                                "Excluír",
                        };
                        AlertDialog.Builder bop = new AlertDialog.Builder(c);
                        bop.setTitle("Escolha uma Opção!");
                        bop.setItems(opts, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p6)
                            {
                                // TODO: Implement this method
                                if(opts[p6].toString().startsWith("Editar")){
                                    LayoutInflater li = getLayoutInflater();
                                    View r = li.inflate(R.layout.cadview, null);
                                    final EditText pro = r.findViewById(R.id.pessoa);
                                    final EditText quant = r.findViewById(R.id.quant);
                                    final EditText valor = r.findViewById(R.id.valor);

                                    pro.setText(aba6.get(p3).getProd());
                                    quant.setText(aba6.get(p3).getQuant());
                                    valor.setText(aba6.get(p3).getValor());
                                    pro.setEnabled(false);
                                    AlertDialog.Builder b = new AlertDialog.Builder(c);
                                    b.setTitle("Editar Produto!");
                                    b.setView(r);
                                    b.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface p1, int p6)
                                        {
                                            // TODO: Implement this method
                                            util us = new util();
                                            us.setId(aba6.get(p3).getId());
                                            us.setTable("paginaSix");
                                            us.setProd(pro.getText().toString());
                                            us.setQuant(quant.getText().toString());
                                            us.setValor(valor.getText().toString());
                                            us.setImage(aba6.get(p3).getImage());
                                            SQLiteControl db = new SQLiteControl(c);
                                            db.postUp(us);

                                            try {
                                                File sd = Environment.getExternalStorageDirectory();
                                                File data = Environment.getDataDirectory();

                                                if (sd.canWrite()) {
                                                    String  currentDBPath= "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db";
                                                    String  currentDBPath6 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-shm";
                                                    String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-wal";

                                                    String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                    String backupDBPath6  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                    String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                    File currentDB = new File(data, currentDBPath);
                                                    File currentDB6 = new File(data, currentDBPath6);
                                                    File currentDB3 = new File(data, currentDBPath3);
                                                    File backupDB = new File(sd, backupDBPath);
                                                    File backupDB6 = new File(sd, backupDBPath6);
                                                    File backupDB3 = new File(sd, backupDBPath3);

                                                    if(currentDB6.exists()){
                                                        FileChannel src = new FileInputStream(currentDB6).getChannel();
                                                        FileChannel dst = new FileOutputStream(backupDB6).getChannel();
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
                                            } catch (Exception e6) {

                                            }
                                            Intent it = new Intent(c, MainActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });

                                    b.setNegativeButton("Cancelar", null);
                                    b.setCancelable(false);
                                    b.create();
                                    b.show();
                                }
                                if(opts[p6].toString().startsWith("Excluír")){
                                    SQLiteControl db = new SQLiteControl(c);
                                    util us = new util();
                                    us.setId(aba6.get(p3).getId());
                                    us.setTable("paginaSix");
                                    db.delete(us);

                                    try {
                                        File sd = Environment.getExternalStorageDirectory();
                                        File data = Environment.getDataDirectory();

                                        if (sd.canWrite()) {
                                            String  currentDBPath= "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db";
                                            String  currentDBPath6 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-shm";
                                            String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-wal";

                                            String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                            String backupDBPath6  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                            String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                            File currentDB = new File(data, currentDBPath);
                                            File currentDB6 = new File(data, currentDBPath6);
                                            File currentDB3 = new File(data, currentDBPath3);
                                            File backupDB = new File(sd, backupDBPath);
                                            File backupDB6 = new File(sd, backupDBPath6);
                                            File backupDB3 = new File(sd, backupDBPath3);

                                            if(currentDB6.exists()){
                                                FileChannel src = new FileInputStream(currentDB6).getChannel();
                                                FileChannel dst = new FileOutputStream(backupDB6).getChannel();
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
                                    } catch (Exception e6) {

                                    }
                                    Intent it = new Intent(c, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
                        bop.create();
                        bop.show();
                    }
                });
                AlertDialog.Builder ale = new AlertDialog.Builder(c);
                ale.setTitle("Visualizar Produtos");
                ale.setView(r);
                ale.show();
            }
        });

        final Button sh5 = (Button) findViewById(R.id.show5);
        final CardView cv5 = (CardView) findViewById(R.id.cv5);
        TextView tvC5 = (TextView) findViewById(R.id.nameTxt5);
        try{
            if(!aba5.get(0).getProd().equals("")){
                util cat = sql.getCategory(5);
                tvC5.setText(cat.getCategory());
                cv5.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            cv5.setVisibility(View.GONE);
        }
        sh5.setText("Mostrar");
        sh5.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.view_product, null);
                SearchView sc = r.findViewById(R.id.search);
                final ListView lv1 = r.findViewById(R.id.list);
                ad = new adapter(c, aba5);
                lv1.setAdapter(ad);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        ad.filter(s);
                        return false;
                    }
                });
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p5, final int p3, long p4)
                    {
                        // TODO: Implement this method
                        final String[] opts = {
                                "Editar",
                                "Excluír",
                        };
                        AlertDialog.Builder bop = new AlertDialog.Builder(c);
                        bop.setTitle("Escolha uma Opção!");
                        bop.setItems(opts, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p5)
                            {
                                // TODO: Implement this method
                                if(opts[p5].toString().startsWith("Editar")){
                                    LayoutInflater li = getLayoutInflater();
                                    View r = li.inflate(R.layout.cadview, null);
                                    final EditText pro = r.findViewById(R.id.pessoa);
                                    final EditText quant = r.findViewById(R.id.quant);
                                    final EditText valor = r.findViewById(R.id.valor);

                                    pro.setText(aba5.get(p3).getProd());
                                    quant.setText(aba5.get(p3).getQuant());
                                    valor.setText(aba5.get(p3).getValor());
                                    pro.setEnabled(false);
                                    AlertDialog.Builder b = new AlertDialog.Builder(c);
                                    b.setTitle("Editar Produto!");
                                    b.setView(r);
                                    b.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface p1, int p5)
                                        {
                                            // TODO: Implement this method
                                            util us = new util();
                                            us.setId(aba5.get(p3).getId());
                                            us.setTable("paginaFive");
                                            us.setProd(pro.getText().toString());
                                            us.setQuant(quant.getText().toString());
                                            us.setValor(valor.getText().toString());
                                            us.setImage(aba5.get(p3).getImage());
                                            SQLiteControl db = new SQLiteControl(c);
                                            db.postUp(us);

                                            try {
                                                File sd = Environment.getExternalStorageDirectory();
                                                File data = Environment.getDataDirectory();

                                                if (sd.canWrite()) {
                                                    String  currentDBPath= "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db";
                                                    String  currentDBPath5 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-shm";
                                                    String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-wal";

                                                    String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                    String backupDBPath5  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                    String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                    File currentDB = new File(data, currentDBPath);
                                                    File currentDB5 = new File(data, currentDBPath5);
                                                    File currentDB3 = new File(data, currentDBPath3);
                                                    File backupDB = new File(sd, backupDBPath);
                                                    File backupDB5 = new File(sd, backupDBPath5);
                                                    File backupDB3 = new File(sd, backupDBPath3);

                                                    if(currentDB5.exists()){
                                                        FileChannel src = new FileInputStream(currentDB5).getChannel();
                                                        FileChannel dst = new FileOutputStream(backupDB5).getChannel();
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
                                            } catch (Exception e5) {

                                            }
                                            Intent it = new Intent(c, MainActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });

                                    b.setNegativeButton("Cancelar", null);
                                    b.setCancelable(false);
                                    b.create();
                                    b.show();
                                }
                                if(opts[p5].toString().startsWith("Excluír")){
                                    SQLiteControl db = new SQLiteControl(c);
                                    util us = new util();
                                    us.setId(aba5.get(p3).getId());
                                    us.setTable("paginaFive");
                                    db.delete(us);

                                    try {
                                        File sd = Environment.getExternalStorageDirectory();
                                        File data = Environment.getDataDirectory();

                                        if (sd.canWrite()) {
                                            String  currentDBPath= "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db";
                                            String  currentDBPath5 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-shm";
                                            String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-wal";

                                            String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                            String backupDBPath5  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                            String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                            File currentDB = new File(data, currentDBPath);
                                            File currentDB5 = new File(data, currentDBPath5);
                                            File currentDB3 = new File(data, currentDBPath3);
                                            File backupDB = new File(sd, backupDBPath);
                                            File backupDB5 = new File(sd, backupDBPath5);
                                            File backupDB3 = new File(sd, backupDBPath3);

                                            if(currentDB5.exists()){
                                                FileChannel src = new FileInputStream(currentDB5).getChannel();
                                                FileChannel dst = new FileOutputStream(backupDB5).getChannel();
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
                                    } catch (Exception e5) {

                                    }
                                    Intent it = new Intent(c, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
                        bop.create();
                        bop.show();
                    }
                });
                AlertDialog.Builder ale = new AlertDialog.Builder(c);
                ale.setTitle("Visualizar Produtos");
                ale.setView(r);
                ale.show();
            }
        });

        final Button sh4 = (Button) findViewById(R.id.show4);
        final CardView cv4 = (CardView) findViewById(R.id.cv4);
        TextView tvC4 = (TextView) findViewById(R.id.nameTxt4);
        try{
            if(!aba4.get(0).getProd().equals("")){
                util cat = sql.getCategory(4);
                tvC4.setText(cat.getCategory());
                cv4.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            cv4.setVisibility(View.GONE);
        }
        sh4.setText("Mostrar");
        sh4.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.view_product, null);
                SearchView sc = r.findViewById(R.id.search);
                final ListView lv1 = r.findViewById(R.id.list);
                ad = new adapter(c, aba4);
                lv1.setAdapter(ad);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        ad.filter(s);
                        return false;
                    }
                });
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
                    {
                        // TODO: Implement this method
                        final String[] opts = {
                                "Editar",
                                "Excluír",
                        };
                        AlertDialog.Builder bop = new AlertDialog.Builder(c);
                        bop.setTitle("Escolha uma Opção!");
                        bop.setItems(opts, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p2)
                            {
                                // TODO: Implement this method
                                if(opts[p2].toString().startsWith("Editar")){
                                    LayoutInflater li = getLayoutInflater();
                                    View r = li.inflate(R.layout.cadview, null);
                                    final EditText pro = r.findViewById(R.id.pessoa);
                                    final EditText quant = r.findViewById(R.id.quant);
                                    final EditText valor = r.findViewById(R.id.valor);

                                    pro.setText(aba4.get(p3).getProd());
                                    quant.setText(aba4.get(p3).getQuant());
                                    valor.setText(aba4.get(p3).getValor());
                                    pro.setEnabled(false);
                                    AlertDialog.Builder b = new AlertDialog.Builder(c);
                                    b.setTitle("Editar Produto!");
                                    b.setView(r);
                                    b.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface p1, int p2)
                                        {
                                            // TODO: Implement this method
                                            util us = new util();
                                            us.setId(aba4.get(p3).getId());
                                            us.setTable("paginaFour");
                                            us.setProd(pro.getText().toString());
                                            us.setQuant(quant.getText().toString());
                                            us.setValor(valor.getText().toString());
                                            us.setImage(aba4.get(p3).getImage());
                                            SQLiteControl db = new SQLiteControl(c);
                                            db.postUp(us);

                                            try {
                                                File sd = Environment.getExternalStorageDirectory();
                                                File data = Environment.getDataDirectory();

                                                if (sd.canWrite()) {
                                                    String  currentDBPath= "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db";
                                                    String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-shm";
                                                    String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-wal";

                                                    String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                    String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                    String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                    File currentDB = new File(data, currentDBPath);
                                                    File currentDB2 = new File(data, currentDBPath2);
                                                    File currentDB3 = new File(data, currentDBPath3);
                                                    File backupDB = new File(sd, backupDBPath);
                                                    File backupDB2 = new File(sd, backupDBPath2);
                                                    File backupDB3 = new File(sd, backupDBPath3);

                                                    if(currentDB2.exists()){
                                                        FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                        FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                            } catch (Exception e2) {

                                            }
                                            Intent it = new Intent(c, MainActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });

                                    b.setNegativeButton("Cancelar", null);
                                    b.setCancelable(false);
                                    b.create();
                                    b.show();
                                }
                                if(opts[p2].toString().startsWith("Excluír")){
                                    SQLiteControl db = new SQLiteControl(c);
                                    util us = new util();
                                    us.setId(aba4.get(p3).getId());
                                    us.setTable("paginaFour");
                                    db.delete(us);

                                    try {
                                        File sd = Environment.getExternalStorageDirectory();
                                        File data = Environment.getDataDirectory();

                                        if (sd.canWrite()) {
                                            String  currentDBPath= "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db";
                                            String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-shm";
                                            String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-wal";

                                            String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                            String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                            String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                            File currentDB = new File(data, currentDBPath);
                                            File currentDB2 = new File(data, currentDBPath2);
                                            File currentDB3 = new File(data, currentDBPath3);
                                            File backupDB = new File(sd, backupDBPath);
                                            File backupDB2 = new File(sd, backupDBPath2);
                                            File backupDB3 = new File(sd, backupDBPath3);

                                            if(currentDB2.exists()){
                                                FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                    } catch (Exception e2) {

                                    }
                                    Intent it = new Intent(c, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
                        bop.create();
                        bop.show();
                    }
                });
                AlertDialog.Builder ale = new AlertDialog.Builder(c);
                ale.setTitle("Visualizar Produtos");
                ale.setView(r);
                ale.show();
            }
        });


        final Button sh3 = (Button) findViewById(R.id.show3);
        final CardView cv3 = (CardView) findViewById(R.id.cv3);
        TextView tvC3 = (TextView) findViewById(R.id.nameTxt3);
        try{
            if(!aba3.get(0).getProd().equals("")){
                util cat = sql.getCategory(3);
                tvC3.setText(cat.getCategory());
                cv3.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            cv3.setVisibility(View.GONE);
        }
        sh3.setText("Mostrar");
        sh3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.view_product, null);
                SearchView sc = r.findViewById(R.id.search);
                final ListView lv1 = r.findViewById(R.id.list);
                ad = new adapter(c, aba3);
                lv1.setAdapter(ad);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        ad.filter(s);
                        return false;
                    }
                });
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
                    {
                        // TODO: Implement this method
                        final String[] opts = {
                                "Editar",
                                "Excluír",
                        };
                        AlertDialog.Builder bop = new AlertDialog.Builder(c);
                        bop.setTitle("Escolha uma Opção!");
                        bop.setItems(opts, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p2)
                            {
                                // TODO: Implement this method
                                if(opts[p2].toString().startsWith("Editar")){
                                    LayoutInflater li = getLayoutInflater();
                                    View r = li.inflate(R.layout.cadview, null);
                                    final EditText pro = r.findViewById(R.id.pessoa);
                                    final EditText quant = r.findViewById(R.id.quant);
                                    final EditText valor = r.findViewById(R.id.valor);

                                    pro.setText(aba3.get(p3).getProd());
                                    quant.setText(aba3.get(p3).getQuant());
                                    valor.setText(aba3.get(p3).getValor());
                                    pro.setEnabled(false);
                                    AlertDialog.Builder b = new AlertDialog.Builder(c);
                                    b.setTitle("Editar Produto!");
                                    b.setView(r);
                                    b.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface p1, int p2)
                                        {
                                            // TODO: Implement this method
                                            util us = new util();
                                            us.setId(aba3.get(p3).getId());
                                            us.setTable("paginaThree");
                                            us.setProd(pro.getText().toString());
                                            us.setQuant(quant.getText().toString());
                                            us.setValor(valor.getText().toString());
                                            us.setImage(aba3.get(p3).getImage());
                                            SQLiteControl db = new SQLiteControl(c);
                                            db.postUp(us);

                                            try {
                                                File sd = Environment.getExternalStorageDirectory();
                                                File data = Environment.getDataDirectory();

                                                if (sd.canWrite()) {
                                                    String  currentDBPath= "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db";
                                                    String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-shm";
                                                    String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-wal";

                                                    String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                    String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                    String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                    File currentDB = new File(data, currentDBPath);
                                                    File currentDB2 = new File(data, currentDBPath2);
                                                    File currentDB3 = new File(data, currentDBPath3);
                                                    File backupDB = new File(sd, backupDBPath);
                                                    File backupDB2 = new File(sd, backupDBPath2);
                                                    File backupDB3 = new File(sd, backupDBPath3);

                                                    if(currentDB2.exists()){
                                                        FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                        FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                            } catch (Exception e2) {

                                            }
                                            Intent it = new Intent(c, MainActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });

                                    b.setNegativeButton("Cancelar", null);
                                    b.setCancelable(false);
                                    b.create();
                                    b.show();
                                }
                                if(opts[p2].toString().startsWith("Excluír")){
                                    SQLiteControl db = new SQLiteControl(c);
                                    util us = new util();
                                    us.setId(aba3.get(p3).getId());
                                    us.setTable("paginaThree");
                                    db.delete(us);

                                    try {
                                        File sd = Environment.getExternalStorageDirectory();
                                        File data = Environment.getDataDirectory();

                                        if (sd.canWrite()) {
                                            String  currentDBPath= "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db";
                                            String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-shm";
                                            String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-wal";

                                            String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                            String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                            String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                            File currentDB = new File(data, currentDBPath);
                                            File currentDB2 = new File(data, currentDBPath2);
                                            File currentDB3 = new File(data, currentDBPath3);
                                            File backupDB = new File(sd, backupDBPath);
                                            File backupDB2 = new File(sd, backupDBPath2);
                                            File backupDB3 = new File(sd, backupDBPath3);

                                            if(currentDB2.exists()){
                                                FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                    } catch (Exception e2) {

                                    }
                                    Intent it = new Intent(c, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
                        bop.create();
                        bop.show();
                    }
                });
                AlertDialog.Builder ale = new AlertDialog.Builder(c);
                ale.setTitle("Visualizar Produtos");
                ale.setView(r);
                ale.show();
            }
        });

        final Button sh2 = (Button) findViewById(R.id.show2);
        final CardView cv2 = (CardView) findViewById(R.id.cv2);
        TextView tvC2 = (TextView) findViewById(R.id.nameTxt2);
        try{
            if(!aba2.get(0).getProd().equals("")){
                util cat = sql.getCategory(2);
                tvC2.setText(cat.getCategory());
                cv2.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            cv2.setVisibility(View.GONE);
        }
        sh2.setText("Mostrar");
        sh2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.view_product, null);
                SearchView sc = r.findViewById(R.id.search);
                final ListView lv1 = r.findViewById(R.id.list);
                ad = new adapter(c, aba2);
                lv1.setAdapter(ad);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        ad.filter(s);
                        return false;
                    }
                });
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
                    {
                        // TODO: Implement this method
                        final String[] opts = {
                                "Editar",
                                "Excluír",
                        };
                        AlertDialog.Builder bop = new AlertDialog.Builder(c);
                        bop.setTitle("Escolha uma Opção!");
                        bop.setItems(opts, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p2)
                            {
                                // TODO: Implement this method
                                if(opts[p2].toString().startsWith("Editar")){
                                    LayoutInflater li = getLayoutInflater();
                                    View r = li.inflate(R.layout.cadview, null);
                                    final EditText pro = r.findViewById(R.id.pessoa);
                                    final EditText quant = r.findViewById(R.id.quant);
                                    final EditText valor = r.findViewById(R.id.valor);

                                    pro.setText(aba2.get(p3).getProd());
                                    quant.setText(aba2.get(p3).getQuant());
                                    valor.setText(aba2.get(p3).getValor());
                                    pro.setEnabled(false);
                                    AlertDialog.Builder b = new AlertDialog.Builder(c);
                                    b.setTitle("Editar Produto!");
                                    b.setView(r);
                                    b.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface p1, int p2)
                                        {
                                            // TODO: Implement this method
                                            util us = new util();
                                            us.setId(aba2.get(p3).getId());
                                            us.setTable("paginaTwo");
                                            us.setProd(pro.getText().toString());
                                            us.setQuant(quant.getText().toString());
                                            us.setValor(valor.getText().toString());
                                            us.setImage(aba2.get(p3).getImage());
                                            SQLiteControl db = new SQLiteControl(c);
                                            db.postUp(us);

                                            try {
                                                File sd = Environment.getExternalStorageDirectory();
                                                File data = Environment.getDataDirectory();

                                                if (sd.canWrite()) {
                                                    String  currentDBPath= "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db";
                                                    String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-shm";
                                                    String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-wal";

                                                    String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                    String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                    String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                    File currentDB = new File(data, currentDBPath);
                                                    File currentDB2 = new File(data, currentDBPath2);
                                                    File currentDB3 = new File(data, currentDBPath3);
                                                    File backupDB = new File(sd, backupDBPath);
                                                    File backupDB2 = new File(sd, backupDBPath2);
                                                    File backupDB3 = new File(sd, backupDBPath3);

                                                    if(currentDB2.exists()){
                                                        FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                        FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                            } catch (Exception e2) {

                                            }
                                            Intent it = new Intent(c, MainActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });

                                    b.setNegativeButton("Cancelar", null);
                                    b.setCancelable(false);
                                    b.create();
                                    b.show();
                                }
                                if(opts[p2].toString().startsWith("Excluír")){
                                    SQLiteControl db = new SQLiteControl(c);
                                    util us = new util();
                                    us.setId(aba2.get(p3).getId());
                                    us.setTable("paginaTwo");
                                    db.delete(us);

                                    try {
                                        File sd = Environment.getExternalStorageDirectory();
                                        File data = Environment.getDataDirectory();

                                        if (sd.canWrite()) {
                                            String  currentDBPath= "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db";
                                            String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-shm";
                                            String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-wal";

                                            String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                            String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                            String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                            File currentDB = new File(data, currentDBPath);
                                            File currentDB2 = new File(data, currentDBPath2);
                                            File currentDB3 = new File(data, currentDBPath3);
                                            File backupDB = new File(sd, backupDBPath);
                                            File backupDB2 = new File(sd, backupDBPath2);
                                            File backupDB3 = new File(sd, backupDBPath3);

                                            if(currentDB2.exists()){
                                                FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                    } catch (Exception e2) {

                                    }
                                    Intent it = new Intent(c, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
                        bop.create();
                        bop.show();
                    }
                });
                AlertDialog.Builder ale = new AlertDialog.Builder(c);
                ale.setTitle("Visualizar Produtos");
                ale.setView(r);
                ale.show();
            }
        });

        final Button sh1 = (Button) findViewById(R.id.show);
        final CardView cv1 = (CardView) findViewById(R.id.cv1);
        TextView tvC = (TextView) findViewById(R.id.nameTxt);
        try{
            if(!aba1.get(0).getProd().equals("")){
                util cat = sql.getCategory(1);
                tvC.setText(cat.getCategory());
                cv1.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            cv1.setVisibility(View.GONE);
        }
        sh1.setText("Mostrar");
        sh1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.view_product, null);
                SearchView sc = r.findViewById(R.id.search);
                final ListView lv1 = r.findViewById(R.id.list);
                ad = new adapter(c, aba1);
                lv1.setAdapter(ad);
                sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        ad.filter(s);
                        return false;
                    }
                });
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
                    {
                        // TODO: Implement this method
                        final String[] opts = {
                                "Editar",
                                "Excluír",
                        };
                        AlertDialog.Builder bop = new AlertDialog.Builder(c);
                        bop.setTitle("Escolha uma Opção!");
                        bop.setItems(opts, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p2)
                            {
                                // TODO: Implement this method
                                if(opts[p2].toString().startsWith("Editar")){
                                    LayoutInflater li = getLayoutInflater();
                                    View r = li.inflate(R.layout.cadview, null);
                                    final EditText pro = r.findViewById(R.id.pessoa);
                                    final EditText quant = r.findViewById(R.id.quant);
                                    final EditText valor = r.findViewById(R.id.valor);

                                    pro.setText(aba1.get(p3).getProd());
                                    quant.setText(aba1.get(p3).getQuant());
                                    valor.setText(aba1.get(p3).getValor());
                                    pro.setEnabled(false);
                                    AlertDialog.Builder b = new AlertDialog.Builder(c);
                                    b.setTitle("Editar Produto!");
                                    b.setView(r);
                                    b.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface p1, int p2)
                                        {
                                            // TODO: Implement this method
                                            util us = new util();
                                            us.setId(aba1.get(p3).getId());
                                            us.setTable("paginaOne");
                                            us.setProd(pro.getText().toString());
                                            us.setQuant(quant.getText().toString());
                                            us.setValor(valor.getText().toString());
                                            us.setImage(aba1.get(p3).getImage());
                                            SQLiteControl db = new SQLiteControl(c);
                                            db.postUp(us);

                                            try {
                                                File sd = Environment.getExternalStorageDirectory();
                                                File data = Environment.getDataDirectory();

                                                if (sd.canWrite()) {
                                                    String  currentDBPath= "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db";
                                                    String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-shm";
                                                    String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                            + "//databases//" + "MCRDB.db-wal";

                                                    String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                    String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                    String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                    File currentDB = new File(data, currentDBPath);
                                                    File currentDB2 = new File(data, currentDBPath2);
                                                    File currentDB3 = new File(data, currentDBPath3);
                                                    File backupDB = new File(sd, backupDBPath);
                                                    File backupDB2 = new File(sd, backupDBPath2);
                                                    File backupDB3 = new File(sd, backupDBPath3);

                                                    if(currentDB2.exists()){
                                                        FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                        FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                            } catch (Exception e2) {

                                            }
                                            Intent it = new Intent(c, MainActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });

                                    b.setNegativeButton("Cancelar", null);
                                    b.setCancelable(false);
                                    b.create();
                                    b.show();
                                }
                                if(opts[p2].toString().startsWith("Excluír")){
                                    SQLiteControl db = new SQLiteControl(c);
                                    util us = new util();
                                    us.setId(aba1.get(p3).getId());
                                    us.setTable("paginaOne");
                                    db.delete(us);

                                    try {
                                        File sd = Environment.getExternalStorageDirectory();
                                        File data = Environment.getDataDirectory();

                                        if (sd.canWrite()) {
                                            String  currentDBPath= "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db";
                                            String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-shm";
                                            String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                    + "//databases//" + "MCRDB.db-wal";

                                            String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                            String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                            String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                            File currentDB = new File(data, currentDBPath);
                                            File currentDB2 = new File(data, currentDBPath2);
                                            File currentDB3 = new File(data, currentDBPath3);
                                            File backupDB = new File(sd, backupDBPath);
                                            File backupDB2 = new File(sd, backupDBPath2);
                                            File backupDB3 = new File(sd, backupDBPath3);

                                            if(currentDB2.exists()){
                                                FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                    } catch (Exception e2) {

                                    }
                                    Intent it = new Intent(c, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
                        bop.create();
                        bop.show();
                    }
                });
                AlertDialog.Builder ale = new AlertDialog.Builder(c);
                ale.setTitle("Visualizar Produtos");
                ale.setView(r);
                ale.show();
            }
        });

        ImageButton btC = (ImageButton) findViewById(R.id.add_cat);
        btC.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                String[] op = {
                        "Gerenciar Páginas",
                        "Adicionar Páginas",
                };
                AlertDialog.Builder b = new AlertDialog.Builder(c);
                b.setTitle("Escolha uma Opção");
                b.setItems(op, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface p1, int p2)
                    {
                        // TODO: Implement this method
                        switch(p2){
                            case 0:
                                SQLiteControl db = new SQLiteControl(c);
                                final util cat1 = db.getCategory(1);
                                final util cat2 = db.getCategory(2);
                                final util cat3 = db.getCategory(3);
                                final util cat4 = db.getCategory(4);
                                final util cat5 = db.getCategory(5);
                                final util cat6 = db.getCategory(6);
                                final util cat7 = db.getCategory(7);
                                final util cat8 = db.getCategory(8);
                                final util cat9 = db.getCategory(9);
                                final util cat10 = db.getCategory(10);


                                LayoutInflater li_ = getLayoutInflater();
                                View r_ = li_.inflate(R.layout.adapter_list, null);
                                final TextView tv = r_.findViewById(R.id.tv);
                                final ImageView edit = r_.findViewById(R.id.edit);
                                final ImageView canc = r_.findViewById(R.id.cancel);

                                final TextView tv2 = r_.findViewById(R.id.tv2);
                                final ImageView edit2 = r_.findViewById(R.id.edit2);
                                final ImageView canc2 = r_.findViewById(R.id.cancel2);

                                final TextView tv3 = r_.findViewById(R.id.tv3);
                                final ImageView edit3 = r_.findViewById(R.id.edit3);
                                final ImageView canc3 = r_.findViewById(R.id.cancel3);
                                final TextView tv4 = r_.findViewById(R.id.tv4);
                                final ImageView edit4 = r_.findViewById(R.id.edit4);
                                final ImageView canc4 = r_.findViewById(R.id.cancel4);

                                final TextView tv5 = r_.findViewById(R.id.tv5);
                                final ImageView edit5 = r_.findViewById(R.id.edit5);
                                final ImageView canc5 = r_.findViewById(R.id.cancel5);

                                final TextView tv6 = r_.findViewById(R.id.tv6);
                                final ImageView edit6 = r_.findViewById(R.id.edit6);
                                final ImageView canc6 = r_.findViewById(R.id.cancel6);

                                final TextView tv7 = r_.findViewById(R.id.tv7);
                                final ImageView edit7 = r_.findViewById(R.id.edit7);
                                final ImageView canc7 = r_.findViewById(R.id.cancel7);

                                final TextView tv8 = r_.findViewById(R.id.tv8);
                                final ImageView edit8 = r_.findViewById(R.id.edit8);
                                final ImageView canc8 = r_.findViewById(R.id.cancel8);

                                final TextView tv9 = r_.findViewById(R.id.tv9);
                                final ImageView edit9 = r_.findViewById(R.id.edit9);
                                final ImageView canc9 = r_.findViewById(R.id.cancel9);

                                final TextView tv10 = r_.findViewById(R.id.tv10);
                                final ImageView edit10 = r_.findViewById(R.id.edit10);
                                final ImageView canc10 = r_.findViewById(R.id.cancel10);

                                final RelativeLayout l1 = r_.findViewById(R.id.l1);
                                final RelativeLayout l2 = r_.findViewById(R.id.l2);
                                final RelativeLayout l3 = r_.findViewById(R.id.l3);
                                final RelativeLayout l4 = r_.findViewById(R.id.l4);
                                final RelativeLayout l5 = r_.findViewById(R.id.l5);
                                final RelativeLayout l6 = r_.findViewById(R.id.l6);
                                final RelativeLayout l7 = r_.findViewById(R.id.l7);
                                final RelativeLayout l8 = r_.findViewById(R.id.l8);
                                final RelativeLayout l9 = r_.findViewById(R.id.l9);
                                final RelativeLayout l10 = r_.findViewById(R.id.l10);

                                try{
                                    if(!cat1.getCategory().toString().equals("")){
                                        l1.setVisibility(View.VISIBLE);
                                        tv.setText(cat1.getCategory());
                                    }
                                }catch(Exception e){
                                    l1.setVisibility(View.GONE);
                                }
                                try{
                                    if(!cat2.getCategory().toString().equals("")){
                                        l2.setVisibility(View.VISIBLE);
                                        tv2.setText(cat2.getCategory());
                                    }
                                }catch(Exception e){
                                    l2.setVisibility(View.GONE);
                                }
                                try{
                                    if(!cat3.getCategory().toString().equals("")){
                                        l3.setVisibility(View.VISIBLE);
                                        tv3.setText(cat3.getCategory());
                                    }
                                }catch(Exception e){
                                    l3.setVisibility(View.GONE);
                                }
                                try{
                                    if(!cat4.getCategory().toString().equals("")){
                                        l4.setVisibility(View.VISIBLE);
                                        tv4.setText(cat4.getCategory());
                                    }
                                }catch(Exception e){
                                    l4.setVisibility(View.GONE);
                                }
                                try{
                                    if(!cat5.getCategory().toString().equals("")){
                                        l5.setVisibility(View.VISIBLE);
                                        tv5.setText(cat5.getCategory());
                                    }
                                }catch(Exception e){
                                    l5.setVisibility(View.GONE);
                                }
                                try{
                                    if(!cat6.getCategory().toString().equals("")){
                                        l6.setVisibility(View.VISIBLE);
                                        tv6.setText(cat6.getCategory());
                                    }
                                }catch(Exception e){
                                    l6.setVisibility(View.GONE);
                                }
                                try{
                                    if(!cat7.getCategory().toString().equals("")){
                                        l7.setVisibility(View.VISIBLE);
                                        tv7.setText(cat7.getCategory());
                                    }
                                }catch(Exception e){
                                    l7.setVisibility(View.GONE);
                                }
                                try{
                                    if(!cat8.getCategory().toString().equals("")){
                                        l8.setVisibility(View.VISIBLE);
                                        tv8.setText(cat8.getCategory());
                                    }
                                }catch(Exception e){
                                    l8.setVisibility(View.GONE);
                                }
                                try{
                                    if(!cat9.getCategory().toString().equals("")){
                                        l9.setVisibility(View.VISIBLE);
                                        tv9.setText(cat9.getCategory());
                                    }
                                }catch(Exception e){
                                    l9.setVisibility(View.GONE);
                                }
                                try{
                                    if(!cat10.getCategory().toString().equals("")){
                                        l10.setVisibility(View.VISIBLE);
                                        tv10.setText(cat10.getCategory());
                                    }
                                }catch(Exception e){
                                    l10.setVisibility(View.GONE);
                                }
                                edit10.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        LayoutInflater li = getLayoutInflater();
                                        View r = li.inflate(R.layout.category_layout, null);
                                        final EditText category = r.findViewById(R.id.cat);
                                        category.setText(cat10.getCategory());
                                        AlertDialog.Builder bcat = new AlertDialog.Builder(c);
                                        bcat.setTitle("Editar Página");
                                        bcat.setView(r);
                                        bcat.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                util us = new util();
                                                us.setId(10);
                                                us.setCategory(category.getText().toString());
                                                SQLiteControl db = new SQLiteControl(c);
                                                db.upCategory(us);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {
                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        bcat.setNegativeButton("Cancelar", null);
                                        bcat.create();
                                        bcat.show();

                                    }
                                });
                                edit9.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        LayoutInflater li = getLayoutInflater();
                                        View r = li.inflate(R.layout.category_layout, null);
                                        final EditText category = r.findViewById(R.id.cat);
                                        category.setText(cat9.getCategory());
                                        AlertDialog.Builder bcat = new AlertDialog.Builder(c);
                                        bcat.setTitle("Editar Página");
                                        bcat.setView(r);
                                        bcat.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                util us = new util();
                                                us.setId(9);
                                                us.setCategory(category.getText().toString());
                                                SQLiteControl db = new SQLiteControl(c);
                                                db.upCategory(us);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {
                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        bcat.setNegativeButton("Cancelar", null);
                                        bcat.create();
                                        bcat.show();

                                    }
                                });
                                edit8.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        LayoutInflater li = getLayoutInflater();
                                        View r = li.inflate(R.layout.category_layout, null);
                                        final EditText category = r.findViewById(R.id.cat);
                                        category.setText(cat8.getCategory());
                                        AlertDialog.Builder bcat = new AlertDialog.Builder(c);
                                        bcat.setTitle("Editar Página");
                                        bcat.setView(r);
                                        bcat.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                util us = new util();
                                                us.setId(8);
                                                us.setCategory(category.getText().toString());
                                                SQLiteControl db = new SQLiteControl(c);
                                                db.upCategory(us);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        bcat.setNegativeButton("Cancelar", null);
                                        bcat.create();
                                        bcat.show();

                                    }
                                });
                                edit7.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        LayoutInflater li = getLayoutInflater();
                                        View r = li.inflate(R.layout.category_layout, null);
                                        final EditText category = r.findViewById(R.id.cat);
                                        category.setText(cat7.getCategory());
                                        AlertDialog.Builder bcat = new AlertDialog.Builder(c);
                                        bcat.setTitle("Editar Página");
                                        bcat.setView(r);
                                        bcat.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                util us = new util();
                                                us.setId(7);
                                                us.setCategory(category.getText().toString());
                                                SQLiteControl db = new SQLiteControl(c);
                                                db.upCategory(us);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        bcat.setNegativeButton("Cancelar", null);
                                        bcat.create();
                                        bcat.show();

                                    }
                                });
                                edit6.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        LayoutInflater li = getLayoutInflater();
                                        View r = li.inflate(R.layout.category_layout, null);
                                        final EditText category = r.findViewById(R.id.cat);
                                        category.setText(cat6.getCategory());
                                        AlertDialog.Builder bcat = new AlertDialog.Builder(c);
                                        bcat.setTitle("Editar Página");
                                        bcat.setView(r);
                                        bcat.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                util us = new util();
                                                us.setId(6);
                                                us.setCategory(category.getText().toString());
                                                SQLiteControl db = new SQLiteControl(c);
                                                db.upCategory(us);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        bcat.setNegativeButton("Cancelar", null);
                                        bcat.create();
                                        bcat.show();

                                    }
                                });
                                edit5.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        LayoutInflater li = getLayoutInflater();
                                        View r = li.inflate(R.layout.category_layout, null);
                                        final EditText category = r.findViewById(R.id.cat);
                                        category.setText(cat5.getCategory());
                                        AlertDialog.Builder bcat = new AlertDialog.Builder(c);
                                        bcat.setTitle("Editar Página");
                                        bcat.setView(r);
                                        bcat.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                util us = new util();
                                                us.setId(5);
                                                us.setCategory(category.getText().toString());
                                                SQLiteControl db = new SQLiteControl(c);
                                                db.upCategory(us);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        bcat.setNegativeButton("Cancelar", null);
                                        bcat.create();
                                        bcat.show();

                                    }
                                });

                                edit4.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        LayoutInflater li = getLayoutInflater();
                                        View r = li.inflate(R.layout.category_layout, null);
                                        final EditText category = r.findViewById(R.id.cat);
                                        category.setText(cat4.getCategory());
                                        AlertDialog.Builder bcat = new AlertDialog.Builder(c);
                                        bcat.setTitle("Editar Página");
                                        bcat.setView(r);
                                        bcat.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                util us = new util();
                                                us.setId(4);
                                                us.setCategory(category.getText().toString());
                                                SQLiteControl db = new SQLiteControl(c);
                                                db.upCategory(us);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        bcat.setNegativeButton("Cancelar", null);
                                        bcat.create();
                                        bcat.show();

                                    }
                                });

                                edit3.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        LayoutInflater li = getLayoutInflater();
                                        View r = li.inflate(R.layout.category_layout, null);
                                        final EditText category = r.findViewById(R.id.cat);
                                        category.setText(cat3.getCategory());
                                        AlertDialog.Builder bcat = new AlertDialog.Builder(c);
                                        bcat.setTitle("Editar Página");
                                        bcat.setView(r);
                                        bcat.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                util us = new util();
                                                us.setId(3);
                                                us.setCategory(category.getText().toString());
                                                SQLiteControl db = new SQLiteControl(c);
                                                db.upCategory(us);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        bcat.setNegativeButton("Cancelar", null);
                                        bcat.create();
                                        bcat.show();

                                    }
                                });

                                edit2.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        LayoutInflater li = getLayoutInflater();
                                        View r = li.inflate(R.layout.category_layout, null);
                                        final EditText category = r.findViewById(R.id.cat);
                                        category.setText(cat2.getCategory());
                                        AlertDialog.Builder bcat = new AlertDialog.Builder(c);
                                        bcat.setTitle("Editar Página");
                                        bcat.setView(r);
                                        bcat.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                util us = new util();
                                                us.setId(2);
                                                us.setCategory(category.getText().toString());
                                                SQLiteControl db = new SQLiteControl(c);
                                                db.upCategory(us);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        bcat.setNegativeButton("Cancelar", null);
                                        bcat.create();
                                        bcat.show();

                                    }
                                });



                                edit.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        LayoutInflater li = getLayoutInflater();
                                        View r = li.inflate(R.layout.category_layout, null);
                                        final EditText category = r.findViewById(R.id.cat);
                                        category.setText(cat1.getCategory());
                                        AlertDialog.Builder bcat = new AlertDialog.Builder(c);
                                        bcat.setTitle("Editar Página");
                                        bcat.setView(r);
                                        bcat.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                util us = new util();
                                                us.setId(1);
                                                us.setCategory(category.getText().toString());
                                                SQLiteControl db = new SQLiteControl(c);
                                                db.upCategory(us);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {

                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";


                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                        //FileChannel src2 = new FileInputStream(currentDB).getChannel();
                                                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                        //FileChannel dst_ = new FileInputStream(currentDB_).getChannel();

                                                        dst.transferFrom(src, 0, src.size());
                                                        //dst_.transferFrom(src2, 0, src2.size());
                                                        src.close();
                                                        //src2.close();
                                                        dst.close();
                                                        //dst_.close();
                                                    }
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        bcat.setNegativeButton("Cancelar", null);
                                        bcat.create();
                                        bcat.show();

                                    }
                                });
                                canc.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        AlertDialog al = new AlertDialog.Builder(c).create();
                                        al.setTitle("Excluír Página!");
                                        al.setMessage("Tem certeza que deseja excluír a página selecionada? Esta ação removerá todos os items juntamente com a pagina. Deseja Continuar?");
                                        al.setButton("Sim, Excluír!", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                SQLiteControl db = new SQLiteControl(c);
                                                util us = new util();
                                                us.setTable("Paginas");
                                                us.setId(1);

                                                util us2 = new util();
                                                us2.setTable("paginaOne");
                                                db.delete(us);
                                                db.del(us2);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        al.setButton2("Não", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                            }
                                        });
                                        al.setCancelable(false);
                                        al.show();
                                    }
                                });

                                canc2.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        AlertDialog al = new AlertDialog.Builder(c).create();
                                        al.setTitle("Excluír Página!");
                                        al.setMessage("Tem certeza que deseja excluír a página selecionada? Esta ação removerá todos os items juntamente com a pagina. Deseja Continuar?");
                                        al.setButton("Sim, Excluír!", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                SQLiteControl db = new SQLiteControl(c);
                                                util us = new util();
                                                us.setTable("Paginas");
                                                us.setId(2);

                                                util us2 = new util();
                                                us2.setTable("paginaTwo");
                                                db.delete(us);
                                                db.del(us2);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        al.setButton2("Não", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                            }
                                        });
                                        al.setCancelable(false);
                                        al.show();
                                    }
                                });
                                canc3.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        AlertDialog al = new AlertDialog.Builder(c).create();
                                        al.setTitle("Excluír Página!");
                                        al.setMessage("Tem certeza que deseja excluír a página selecionada? Esta ação removerá todos os items juntamente com a pagina. Deseja Continuar?");
                                        al.setButton("Sim, Excluír!", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                SQLiteControl db = new SQLiteControl(c);
                                                util us = new util();
                                                us.setTable("Paginas");
                                                us.setId(3);

                                                util us2 = new util();
                                                us2.setTable("paginaThree");
                                                db.delete(us);
                                                db.del(us2);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        al.setButton2("Não", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                            }
                                        });
                                        al.setCancelable(false);
                                        al.show();
                                    }
                                });
                                canc4.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        AlertDialog al = new AlertDialog.Builder(c).create();
                                        al.setTitle("Excluír Página!");
                                        al.setMessage("Tem certeza que deseja excluír a página selecionada? Esta ação removerá todos os items juntamente com a pagina. Deseja Continuar?");
                                        al.setButton("Sim, Excluír!", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                SQLiteControl db = new SQLiteControl(c);
                                                util us = new util();
                                                us.setTable("Paginas");
                                                us.setId(4);

                                                util us2 = new util();
                                                us2.setTable("paginaFour");
                                                db.delete(us);
                                                db.del(us2);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        al.setButton2("Não", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                            }
                                        });
                                        al.setCancelable(false);
                                        al.show();
                                    }
                                });
                                canc5.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        AlertDialog al = new AlertDialog.Builder(c).create();
                                        al.setTitle("Excluír Página!");
                                        al.setMessage("Tem certeza que deseja excluír a página selecionada? Esta ação removerá todos os items juntamente com a pagina. Deseja Continuar?");
                                        al.setButton("Sim, Excluír!", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                SQLiteControl db = new SQLiteControl(c);
                                                util us = new util();
                                                us.setTable("Paginas");
                                                us.setId(5);

                                                util us2 = new util();
                                                us2.setTable("paginaFive");
                                                db.delete(us);
                                                db.del(us2);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        al.setButton2("Não", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                            }
                                        });
                                        al.setCancelable(false);
                                        al.show();
                                    }
                                });
                                canc6.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        AlertDialog al = new AlertDialog.Builder(c).create();
                                        al.setTitle("Excluír Página!");
                                        al.setMessage("Tem certeza que deseja excluír a página selecionada? Esta ação removerá todos os items juntamente com a pagina. Deseja Continuar?");
                                        al.setButton("Sim, Excluír!", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                SQLiteControl db = new SQLiteControl(c);
                                                util us = new util();
                                                us.setTable("Paginas");
                                                us.setId(6);

                                                util us2 = new util();
                                                us2.setTable("paginaSix");
                                                db.delete(us);
                                                db.del(us2);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        al.setButton2("Não", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                            }
                                        });
                                        al.setCancelable(false);
                                        al.show();
                                    }
                                });
                                canc7.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        AlertDialog al = new AlertDialog.Builder(c).create();
                                        al.setTitle("Excluír Página!");
                                        al.setMessage("Tem certeza que deseja excluír a página selecionada? Esta ação removerá todos os items juntamente com a pagina. Deseja Continuar?");
                                        al.setButton("Sim, Excluír!", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                SQLiteControl db = new SQLiteControl(c);
                                                util us = new util();
                                                us.setTable("Paginas");
                                                us.setId(7);

                                                util us2 = new util();
                                                us2.setTable("paginaSeven");
                                                db.delete(us);
                                                db.del(us2);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        al.setButton2("Não", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                            }
                                        });
                                        al.setCancelable(false);
                                        al.show();
                                    }
                                });
                                canc8.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        AlertDialog al = new AlertDialog.Builder(c).create();
                                        al.setTitle("Excluír Página!");
                                        al.setMessage("Tem certeza que deseja excluír a página selecionada? Esta ação removerá todos os items juntamente com a pagina. Deseja Continuar?");
                                        al.setButton("Sim, Excluír!", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                SQLiteControl db = new SQLiteControl(c);
                                                util us = new util();
                                                us.setTable("Paginas");
                                                us.setId(8);

                                                util us2 = new util();
                                                us2.setTable("paginaEight");
                                                db.delete(us);
                                                db.del(us2);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        al.setButton2("Não", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                            }
                                        });
                                        al.setCancelable(false);
                                        al.show();
                                    }
                                });
                                canc9.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        AlertDialog al = new AlertDialog.Builder(c).create();
                                        al.setTitle("Excluír Página!");
                                        al.setMessage("Tem certeza que deseja excluír a página selecionada? Esta ação removerá todos os items juntamente com a pagina. Deseja Continuar?");
                                        al.setButton("Sim, Excluír!", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                SQLiteControl db = new SQLiteControl(c);
                                                util us = new util();
                                                us.setTable("Paginas");
                                                us.setId(9);

                                                util us2 = new util();
                                                us2.setTable("paginaNine");
                                                db.delete(us);
                                                db.del(us2);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        al.setButton2("Não", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                            }
                                        });
                                        al.setCancelable(false);
                                        al.show();
                                    }
                                });
                                canc10.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View p1)
                                    {
                                        // TODO: Implement this method
                                        AlertDialog al = new AlertDialog.Builder(c).create();
                                        al.setTitle("Excluír Página!");
                                        al.setMessage("Tem certeza que deseja excluír a página selecionada? Esta ação removerá todos os items juntamente com a pagina. Deseja Continuar?");
                                        al.setButton("Sim, Excluír!", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                                SQLiteControl db = new SQLiteControl(c);
                                                util us = new util();
                                                us.setTable("Paginas");
                                                us.setId(10);

                                                util us2 = new util();
                                                us2.setTable("paginaTen");
                                                db.delete(us);
                                                db.del(us2);
                                                try {
                                                    File sd = Environment.getExternalStorageDirectory();
                                                    File data = Environment.getDataDirectory();

                                                    if (sd.canWrite()) {
                                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db";
                                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-shm";
                                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                + "//databases//" + "MCRDB.db-wal";

                                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                        File currentDB = new File(data, currentDBPath);
                                                        File currentDB2 = new File(data, currentDBPath2);
                                                        File currentDB3 = new File(data, currentDBPath3);
                                                        File backupDB = new File(sd, backupDBPath);
                                                        File backupDB2 = new File(sd, backupDBPath2);
                                                        File backupDB3 = new File(sd, backupDBPath3);

                                                        if(currentDB2.exists()){
                                                            FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                                } catch (Exception e2) {

                                                }
                                                Intent it = new Intent(c, MainActivity.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });
                                        al.setButton2("Não", new Dialog.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface p1, int p2)
                                            {
                                                // TODO: Implement this method
                                            }
                                        });
                                        al.setCancelable(false);
                                        al.show();
                                    }
                                });

                                AlertDialog.Builder b = new AlertDialog.Builder(c);
                                b.setTitle("Gerenciar Páginas");
                                b.setView(r_);
                                b.create();
                                b.show();
                                break;
                            case 1:
                                LayoutInflater li = getLayoutInflater();
                                View r = li.inflate(R.layout.category_layout, null);
                                final EditText cat = r.findViewById(R.id.cat);
                                AlertDialog.Builder bs = new AlertDialog.Builder(c);
                                bs.setTitle("Adicionar Categoria!");
                                bs.setView(r);
                                bs.setPositiveButton("Adicionar", new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface p1, int p2)
                                    {
                                        // TODO: Implement this method
                                        util us = new util();
                                        us.setCategory(cat.getText().toString());
                                        SQLiteControl db_cat = new SQLiteControl(c);
                                        db_cat.insertCategory(us);
                                        try {
                                            File sd = Environment.getExternalStorageDirectory();
                                            File data = Environment.getDataDirectory();

                                            if (sd.canWrite()) {
                                                String  currentDBPath= "//data//" + c.getOpPackageName()
                                                        + "//databases//" + "MCRDB.db";
                                                String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                        + "//databases//" + "MCRDB.db-shm";
                                                String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                        + "//databases//" + "MCRDB.db-wal";

                                                String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                File currentDB = new File(data, currentDBPath);
                                                File currentDB2 = new File(data, currentDBPath2);
                                                File currentDB3 = new File(data, currentDBPath3);
                                                File backupDB = new File(sd, backupDBPath);
                                                File backupDB2 = new File(sd, backupDBPath2);
                                                File backupDB3 = new File(sd, backupDBPath3);

                                                if(currentDB2.exists()){
                                                    FileChannel src = new FileInputStream(currentDB2).getChannel();
                                                    FileChannel dst = new FileOutputStream(backupDB2).getChannel();
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
                                        } catch (Exception e2) {

                                        }
                                    }
                                });
                                bs.setNegativeButton("Cancelar", null);
                                bs.create();
                                bs.show();
                                break;
                        }
                    }
                });
                b.create();
                b.show();
            }
        });

        ImageButton bt = (ImageButton) findViewById(R.id.add);

        bt.setImageResource(R.drawable.ic_add);
        bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                try{
                    SQLiteControl ct = new SQLiteControl(c);
                    util us = ct.getCategory(1);
                    if(!us.getCategory().toString().equals("")){
                        LayoutInflater li = getLayoutInflater();
                        View r = li.inflate(R.layout.cadastro, null);
                        final EditText imp = r.findViewById(R.id.imP);
                        final TextInputEditText prod = r.findViewById(R.id.pessoa);
                        final TextInputEditText quant = r.findViewById(R.id.quant);
                        final TextInputEditText valor = r.findViewById(R.id.valor);
                        final TextInputEditText etotal = r.findViewById(R.id.etotal);
                        forn = r.findViewById(R.id.forn);
                        final TextInputEditText dIn = r.findViewById(R.id.dIn);
                        final TextInputEditText dout = r.findViewById(R.id.dOut);
                        final CheckBox chk1 = r.findViewById(R.id.check1);
                        final CheckBox chk2 = r.findViewById(R.id.check2);
                        final CheckBox chk3 = r.findViewById(R.id.check3);
                        final TextInputEditText tipo = r.findViewById(R.id.tipo);

                        final CheckBox chForn = r.findViewById(R.id.chForn);
                        final CheckBox chTotal = r.findViewById(R.id.chTotal);
                        final CheckBox chDin = r.findViewById(R.id.chDin);
                        final CheckBox chDout = r.findViewById(R.id.chDout);
                        final CheckBox chk4 = r.findViewById(R.id.check4);
                        final CheckBox chk5 = r.findViewById(R.id.check5);
                        final CheckBox chk6 = r.findViewById(R.id.check6);
                        final CheckBox chk7 = r.findViewById(R.id.check7);
                        final CheckBox chk8 = r.findViewById(R.id.check8);
                        final CheckBox chk9 = r.findViewById(R.id.check9);
                        final CheckBox chk10= r.findViewById(R.id.check10);

                        TextView tv = r.findViewById(R.id.tv1);
                        TextView tv2 = r.findViewById(R.id.tv2);
                        TextView tv3 = r.findViewById(R.id.tv3);
                        TextView tv4 = r.findViewById(R.id.tv4);
                        TextView tv5 = r.findViewById(R.id.tv5);
                        TextView tv6 = r.findViewById(R.id.tv6);
                        TextView tv7 = r.findViewById(R.id.tv7);
                        TextView tv8 = r.findViewById(R.id.tv8);
                        TextView tv9 = r.findViewById(R.id.tv9);
                        TextView tv10 = r.findViewById(R.id.tv10);

                        LinearLayout lcheck1 = r.findViewById(R.id.lcheck1);
                        LinearLayout lcheck2 = r.findViewById(R.id.lcheck2);
                        LinearLayout lcheck3 = r.findViewById(R.id.lcheck3);
                        LinearLayout lcheck4 = r.findViewById(R.id.lcheck4);
                        LinearLayout lcheck5 = r.findViewById(R.id.lcheck5);
                        LinearLayout lcheck6 = r.findViewById(R.id.lcheck6);
                        LinearLayout lcheck7 = r.findViewById(R.id.lcheck7);
                        LinearLayout lcheck8 = r.findViewById(R.id.lcheck8);
                        LinearLayout lcheck9 = r.findViewById(R.id.lcheck9);
                        LinearLayout lcheck10 = r.findViewById(R.id.lcheck10);

                        try{
                            SQLiteControl db = new SQLiteControl(c);
                            util cat10 = db.getCategory(10);
                            if(!cat10.getCategory().toString().equals("")){
                                tv10.setText(cat10.getCategory());
                                lcheck10.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception e){
                            lcheck10.setVisibility(View.GONE);
                        }
                        try{
                            SQLiteControl db = new SQLiteControl(c);
                            util cat7 = db.getCategory(7);
                            if(!cat7.getCategory().toString().equals("")){
                                tv7.setText(cat7.getCategory());
                                lcheck7.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception e){
                            lcheck7.setVisibility(View.GONE);
                        }
                        try{
                            SQLiteControl db = new SQLiteControl(c);
                            util cat8 = db.getCategory(8);
                            if(!cat8.getCategory().toString().equals("")){
                                tv8.setText(cat8.getCategory());
                                lcheck8.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception e){
                            lcheck8.setVisibility(View.GONE);
                        }
                        try{
                            SQLiteControl db = new SQLiteControl(c);
                            util cat9 = db.getCategory(9);
                            if(!cat9.getCategory().toString().equals("")){
                                tv9.setText(cat9.getCategory());
                                lcheck9.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception e){
                            lcheck9.setVisibility(View.GONE);
                        }

                        try{
                            SQLiteControl db = new SQLiteControl(c);
                            util cat6 = db.getCategory(6);
                            if(!cat6.getCategory().toString().equals("")){
                                tv6.setText(cat6.getCategory());
                                lcheck6.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception e){
                            lcheck6.setVisibility(View.GONE);
                        }
                        try{
                            SQLiteControl db = new SQLiteControl(c);
                            util cat5 = db.getCategory(5);
                            if(!cat5.getCategory().toString().equals("")){
                                tv5.setText(cat5.getCategory());
                                lcheck5.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception e){
                            lcheck5.setVisibility(View.GONE);
                        }
                        try{
                            SQLiteControl db = new SQLiteControl(c);
                            util cat4 = db.getCategory(4);
                            if(!cat4.getCategory().toString().equals("")){
                                tv4.setText(cat4.getCategory());
                                lcheck4.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception e){
                            lcheck4.setVisibility(View.GONE);
                        }
                        try{
                            SQLiteControl db = new SQLiteControl(c);
                            util cat1 = db.getCategory(1);
                            if(!cat1.getCategory().toString().equals("")){
                                tv.setText(cat1.getCategory());
                                lcheck1.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception e){
                            lcheck1.setVisibility(View.GONE);
                        }
                        try{
                            SQLiteControl db = new SQLiteControl(c);
                            util cat2 = db.getCategory(2);
                            if(!cat2.getCategory().toString().equals("")){
                                tv2.setText(cat2.getCategory());
                                lcheck2.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception e){
                            //tv.setText("Erro");
                            lcheck2.setVisibility(View.GONE);
                        }
                        try{
                            SQLiteControl db = new SQLiteControl(c);
                            util cat3 = db.getCategory(3);
                            if(!cat3.getCategory().toString().equals("")){
                                tv3.setText(cat3.getCategory());
                                lcheck3.setVisibility(View.VISIBLE);
                            }
                        }catch(Exception e){
                            //tv.setText("Erro");
                            lcheck3.setVisibility(View.GONE);
                        }
                        ImageView chp = r.findViewById(R.id.chIm);
                        chp.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View p2)
                            {
                                // TODO: Implement this method
                                LayoutInflater li = getLayoutInflater();
                                View r2 = li.inflate(R.layout.chooser, null);
                                lr = r2.findViewById(R.id.list);
                                Button bck = r2.findViewById(R.id.back);
                                intImages = new ArrayList<Integer>();
                                theNamesOfFiles = new ArrayList<String>();
                                lt = (ListView) findViewById(R.id.list);
                                if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                                {
                                    dirPath = String.valueOf(android.os.Environment.getExternalStorageDirectory());
                                }

                                ///mounted
                                RefreshListView();
                                set_Adapter();
                                bck.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        if (dirPath!="" && dirPath!="/"){
                                            String[] folders = dirPath.split("\\/");
                                            String[] folders2={};
                                            folders2 = Arrays.copyOf(folders, folders.length-1);
                                            dirPath = TextUtils.join("/", folders2);
                                        }

                                        if (dirPath==""){
                                            dirPath="/";
                                        }
                                        RefreshListView();
                                        RefreshAdapter();

                                    }
                                });
                                lr.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                                    @Override
                                    public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
                                    {
                                        // TODO: Implement this method
                                        try{
                                            ParentdirPath = dirPath+"/..";
                                            dirPath = dirPath+"/"+theNamesOfFiles.get(p3);

                                            File f = new File(dirPath);
                                            if (f.isDirectory()){
                                                RefreshListView();
                                                RefreshAdapter();
                                            }else{
                                                imp.getText().clear();
                                                imp.setText(dirPath.toString());
                                                al.dismiss();
                                            }

                                        }catch (Exception e){
                                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                AlertDialog.Builder b = new AlertDialog.Builder(c);
                                b.setView(r2);

                                al = b.create();
                                al = b.show();
                            }
                        });
                        chForn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                            @Override
                            public void onCheckedChanged(CompoundButton p1, boolean p2)
                            {
                                // TODO: Implement this method
                                if(p2){
                                    forn.setText("N/A");
                                } else {
                                    forn.setText("");
                                }
                            }
                        });
                        chTotal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                            @Override
                            public void onCheckedChanged(CompoundButton p1, boolean p2)
                            {
                                // TODO: Implement this method
                                if(p2){
                                    etotal.setText("00.00");
                                } else {
                                    etotal.setText("");
                                }
                            }
                        });
                        chDin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                            @Override
                            public void onCheckedChanged(CompoundButton p1, boolean p2)
                            {
                                // TODO: Implement this method
                                if(p2){
                                    dIn.setText("00/00/0000");
                                } else {
                                    dIn.setText("");
                                }
                            }
                        });
                        chDout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                            @Override
                            public void onCheckedChanged(CompoundButton p1, boolean p2)
                            {
                                // TODO: Implement this method
                                if(p2){
                                    dout.setText("00/00/0000");
                                } else {
                                    dout.setText("");
                                }
                            }
                        });
                        AlertDialog.Builder ch = new AlertDialog.Builder(c);
                        ch.setTitle("Cadastrar Produtos");
                        ch.setView(r);
                        ch.setPositiveButton("Salvar", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p2)
                            {
                                // TODO: Implement this method
                                if(chk1.isChecked()){
                                    SQLiteControl db_pg1 = new SQLiteControl(c);
                                    util us = new util();
                                    us.setTable("paginaOne");
                                    us.setProd(prod.getText().toString());
                                    us.setQuant(quant.getText().toString());
                                    us.setValor(valor.getText().toString());
                                    us.setDataIn(dIn.getText().toString());
                                    us.setDataOut(dout.getText().toString());
                                    us.setForn(forn.getText().toString());
                                    us.setETotal(etotal.getText().toString());
                                    us.setTipo(tipo.getText().toString());
                                    try{
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setImage(lm);
                                    }catch(IOException e){}
                                    db_pg1.post(us);

                                }
                                if(chk2.isChecked()){
                                    SQLiteControl db_pg2 = new SQLiteControl(c);
                                    util us = new util();
                                    us.setTable("paginaTwo");
                                    us.setProd(prod.getText().toString());
                                    us.setQuant(quant.getText().toString());
                                    us.setValor(valor.getText().toString());
                                    us.setDataIn(dIn.getText().toString());
                                    us.setDataOut(dout.getText().toString());
                                    us.setForn(forn.getText().toString());
                                    us.setETotal(etotal.getText().toString());
                                    us.setTipo(tipo.getText().toString());
                                    try{
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setImage(lm);
                                    }catch(IOException e){}
                                    db_pg2.post(us);
                                }
                                if(chk3.isChecked()){
                                    SQLiteControl db_pg2 = new SQLiteControl(c);
                                    util us = new util();
                                    us.setTable("paginaThree");
                                    us.setProd(prod.getText().toString());
                                    us.setQuant(quant.getText().toString());
                                    us.setValor(valor.getText().toString());
                                    us.setDataIn(dIn.getText().toString());
                                    us.setDataOut(dout.getText().toString());
                                    us.setForn(forn.getText().toString());
                                    us.setETotal(etotal.getText().toString());
                                    us.setTipo(tipo.getText().toString());
                                    try{
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setImage(lm);
                                    }catch(IOException e){}
                                    db_pg2.post(us);
                                }
                                if(chk4.isChecked()){
                                    SQLiteControl db_pg2 = new SQLiteControl(c);
                                    util us = new util();
                                    us.setTable("paginaFour");
                                    us.setProd(prod.getText().toString());
                                    us.setQuant(quant.getText().toString());
                                    us.setValor(valor.getText().toString());
                                    us.setDataIn(dIn.getText().toString());
                                    us.setDataOut(dout.getText().toString());
                                    us.setForn(forn.getText().toString());
                                    us.setETotal(etotal.getText().toString());
                                    us.setTipo(tipo.getText().toString());
                                    try{
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setImage(lm);
                                    }catch(IOException e){}
                                    db_pg2.post(us);
                                }
                                if(chk5.isChecked()){
                                    SQLiteControl db_pg2 = new SQLiteControl(c);
                                    util us = new util();
                                    us.setTable("paginaFive");
                                    us.setProd(prod.getText().toString());
                                    us.setQuant(quant.getText().toString());
                                    us.setValor(valor.getText().toString());
                                    us.setDataIn(dIn.getText().toString());
                                    us.setDataOut(dout.getText().toString());
                                    us.setForn(forn.getText().toString());
                                    us.setETotal(etotal.getText().toString());
                                    us.setTipo(tipo.getText().toString());
                                    try{
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setImage(lm);
                                    }catch(IOException e){}
                                    db_pg2.post(us);
                                }
                                if(chk6.isChecked()){
                                    SQLiteControl db_pg2 = new SQLiteControl(c);
                                    util us = new util();
                                    us.setTable("paginaSix");
                                    us.setProd(prod.getText().toString());
                                    us.setQuant(quant.getText().toString());
                                    us.setValor(valor.getText().toString());
                                    us.setDataIn(dIn.getText().toString());
                                    us.setDataOut(dout.getText().toString());
                                    us.setForn(forn.getText().toString());
                                    us.setETotal(etotal.getText().toString());
                                    us.setTipo(tipo.getText().toString());
                                    try{
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setImage(lm);
                                    }catch(IOException e){}
                                    db_pg2.post(us);
                                }
                                if(chk7.isChecked()){
                                    SQLiteControl db_pg2 = new SQLiteControl(c);
                                    util us = new util();
                                    us.setTable("paginaSeven");
                                    us.setProd(prod.getText().toString());
                                    us.setQuant(quant.getText().toString());
                                    us.setValor(valor.getText().toString());
                                    us.setDataIn(dIn.getText().toString());
                                    us.setDataOut(dout.getText().toString());
                                    us.setForn(forn.getText().toString());
                                    us.setETotal(etotal.getText().toString());
                                    us.setTipo(tipo.getText().toString());
                                    try{
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setImage(lm);
                                    }catch(IOException e){}
                                    db_pg2.post(us);
                                }
                                if(chk8.isChecked()){
                                    SQLiteControl db_pg2 = new SQLiteControl(c);
                                    util us = new util();
                                    us.setTable("paginaEight");
                                    us.setProd(prod.getText().toString());
                                    us.setQuant(quant.getText().toString());
                                    us.setValor(valor.getText().toString());
                                    us.setDataIn(dIn.getText().toString());
                                    us.setDataOut(dout.getText().toString());
                                    us.setForn(forn.getText().toString());
                                    us.setETotal(etotal.getText().toString());
                                    us.setTipo(tipo.getText().toString());
                                    try{
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setImage(lm);
                                    }catch(IOException e){}
                                    db_pg2.post(us);
                                }
                                if(chk9.isChecked()){
                                    SQLiteControl db_pg2 = new SQLiteControl(c);
                                    util us = new util();
                                    us.setTable("paginaNine");
                                    us.setProd(prod.getText().toString());
                                    us.setQuant(quant.getText().toString());
                                    us.setValor(valor.getText().toString());
                                    us.setDataIn(dIn.getText().toString());
                                    us.setDataOut(dout.getText().toString());
                                    us.setForn(forn.getText().toString());
                                    us.setETotal(etotal.getText().toString());
                                    us.setTipo(tipo.getText().toString());
                                    try{
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setImage(lm);
                                    }catch(IOException e){}
                                    db_pg2.post(us);
                                }
                                if(chk10.isChecked()){
                                    SQLiteControl db_pg2 = new SQLiteControl(c);
                                    util us = new util();
                                    us.setTable("paginaTen");
                                    us.setProd(prod.getText().toString());
                                    us.setQuant(quant.getText().toString());
                                    us.setValor(valor.getText().toString());
                                    us.setDataIn(dIn.getText().toString());
                                    us.setDataOut(dout.getText().toString());
                                    us.setForn(forn.getText().toString());
                                    us.setETotal(etotal.getText().toString());
                                    us.setTipo(tipo.getText().toString());
                                    try{
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setImage(lm);
                                    }catch(IOException e){}
                                    db_pg2.post(us);
                                }
                                al.dismiss();
                                Intent itt = new Intent(c, MainActivity.class);
                                startActivity(itt);
                                finish();
                                try {
                                    File sd = Environment.getExternalStorageDirectory();
                                    File data = Environment.getDataDirectory();

                                    if (sd.canWrite()) {
                                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                                + "//databases//" + "MCRDB.db";
                                        String  currentDBPath2 = "//data//" + c.getOpPackageName()
                                                + "//databases//" + "MCRDB.db-shm";
                                        String  currentDBPath3 = "//data//" + c.getOpPackageName()
                                                + "//databases//" + "MCRDB.db-wal";

                                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                        String backupDBPath2  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                        String backupDBPath3  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                        File currentDB = new File(data, currentDBPath);
                                        File currentDB2 = new File(data, currentDBPath2);
                                        File currentDB3 = new File(data, currentDBPath3);
                                        File backupDB = new File(sd, backupDBPath);
                                        File backupDB2 = new File(sd, backupDBPath2);
                                        File backupDB3 = new File(sd, backupDBPath3);

                                        if(currentDB2.exists()){
                                            FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                            FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                            dst.transferFrom(src2, 0, src2.size());
                                            src2.close();
                                            dst.close();
                                        }
                                        if(currentDB3.exists()){
                                            FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                            FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                            dst.transferFrom(src3, 0, src3.size());
                                            src3.close();
                                            dst.close();
                                        }
                                        FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                        dst.transferFrom(src4, 0, src4.size());
                                        src4.close();
                                        dst.close();
                                    }
                                } catch (Exception e2) {

                                }
                                Intent it = new Intent(c, MainActivity.class);
                                startActivity(it);
                                finish();
                            }
                        });
                        ch.setNegativeButton("Cancelar", null);
                        ch.create();
                        ch.show();
                    }
                }catch(Exception e){
                    Toast.makeText(c, "Adicione uma página primeiro!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public static void copyFile(File sourceLocation, File targetLocation)
            throws FileNotFoundException, IOException {
        InputStream in = new FileInputStream(sourceLocation);
        OutputStream out = new FileOutputStream(targetLocation);

        // Copy the bits from instream to outstream
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    public void set_Adapter() {
        customList = new CustomList();
        lr.setAdapter(customList);
    }

    public void RefreshListView() {
        try{

            dir = new File(dirPath);
            File[] filelist = dir.listFiles();

            //reset ArrayLists
            theNamesOfFiles.clear();
            intImages.clear();

            for (int i = 0; i < filelist.length; i++) {

                theNamesOfFiles.add(filelist[i].getName());
                //   intImages[i] = R.drawable.folder;

                if(filelist[i].isDirectory()==true){
                    intImages.add(R.drawable.folder);
                }else if(filelist[i].isFile()==true){
                    intImages.add(R.drawable.file);
                }else{
                    intImages.add(R.drawable.file);
                }
            }
        }catch (Exception e){
//        String error = e.toString() + "\n\nMessage: " + e.getMessage();
//        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }

    }
    public void RefreshAdapter(){
        customList.notifyDataSetChanged();
    }
    public void fornText(){
        forn.setText("N/A");
    }
    public class CustomList extends BaseAdapter {

        @Override
        public int getCount() {
            return intImages.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View view1 = getLayoutInflater().inflate(R.layout.list, null);
            ImageView imageView =  view1.findViewById(R.id.ItemIcon);
            TextView txtPath = view1.findViewById(R.id.ItemName);

            imageView.setImageResource(intImages.get(i));
            txtPath.setText(theNamesOfFiles.get(i));

            return view1;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            String[] op = {
                    "Gerenciar Usuários",
                    "Trocar Retaguarda"
            };
            AlertDialog.Builder ch = new AlertDialog.Builder(c);
            ch.setTitle("Escolha uma Opção!");
            ch.setItems(op, new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int position) {
                   switch(position){
                       case 0:
                           SQLiteControl db = new SQLiteControl(c);
                           try {
                               util test = db.getSenhaCM(1);
                               if (!test.getUsuario().toString().equals("")) {
                                   LayoutInflater li = getLayoutInflater();
                                   View r = li.inflate(R.layout.user_adapter, null);
                                   final RelativeLayout l1 = r.findViewById(R.id.l1);
                                   final RelativeLayout l2 = r.findViewById(R.id.l2);
                                   final RelativeLayout l3 = r.findViewById(R.id.l3);
                                   final RelativeLayout l4 = r.findViewById(R.id.l4);

                                   final ImageView im1 = r.findViewById(R.id.im1);
                                   final ImageView ed1 = r.findViewById(R.id.edit1);
                                   final TextView tv1 = r.findViewById(R.id.tv1);

                                   final ImageView ed2 = r.findViewById(R.id.edit2);
                                   final ImageView im2 = r.findViewById(R.id.im2);
                                   final TextView tv2 = r.findViewById(R.id.tv2);

                                   final ImageView ed3 = r.findViewById(R.id.edit3);
                                   final ImageView im3 = r.findViewById(R.id.im3);
                                   final TextView tv3 = r.findViewById(R.id.tv3);

                                   final ImageView ed4 = r.findViewById(R.id.edit4);
                                   final ImageView im4 = r.findViewById(R.id.im4);
                                   final TextView tv4 = r.findViewById(R.id.tv4);

                                   try {
                                       SQLiteControl dbus = new SQLiteControl(c);
                                       util us = dbus.getSenhaCM(1);
                                       if (!us.getUsuario().toString().equals("")) {
                                           l1.setVisibility(View.VISIBLE);
                                           tv1.setText(us.getUsuario());
                                           im1.setImageResource(R.drawable.chave);
                                       }
                                   } catch (Exception e) {
                                       l1.setVisibility(View.GONE);
                                   }
                                   try {
                                       SQLiteControl dbus = new SQLiteControl(c);
                                       util us = dbus.getSenhaMCR(1);
                                       if (!us.getUsuario().toString().equals("")) {
                                           l2.setVisibility(View.VISIBLE);
                                           tv2.setText(us.getUsuario());
                                           im2.setImageResource(R.drawable.chave);
                                       }
                                   } catch (Exception e) {
                                       l2.setVisibility(View.GONE);
                                   }
                                   try {
                                       SQLiteControl dbus = new SQLiteControl(c);
                                       util us = dbus.getSuperVisor(1);
                                       if (!us.getSenhaSuperVisor().toString().equals("")) {
                                           l3.setVisibility(View.VISIBLE);
                                           tv3.setText(us.getSenhaSuperVisor());
                                           im3.setImageResource(R.drawable.chave);
                                       }
                                   } catch (Exception e) {
                                       l3.setVisibility(View.GONE);
                                   }
                                   try {
                                       SQLiteControl dbus = new SQLiteControl(c);
                                       util us = dbus.getSenhaRet(1);
                                       if (!us.getUsuario().toString().equals("")) {
                                           l4.setVisibility(View.VISIBLE);
                                           tv4.setText(us.getUsuario());
                                           im4.setImageResource(R.drawable.chave);
                                       }
                                   } catch (Exception e) {
                                       l4.setVisibility(View.GONE);
                                   }
                                   ed1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           LayoutInflater li = getLayoutInflater();
                                           View r = li.inflate(R.layout.password_pos_client, null);
                                           final TextInputEditText user = r.findViewById(R.id.user);
                                           final TextInputEditText pass = r.findViewById(R.id.pass);

                                           SQLiteControl db = new SQLiteControl(c);
                                           final util us = db.getSenhaCM(1);
                                           user.setText(us.getUsuario());
                                           pass.setText(us.getSenha());

                                           AlertDialog.Builder sv = new AlertDialog.Builder(c);
                                           sv.setTitle("Atualizar Usuário / Senha CM:");
                                           sv.setView(r);
                                           sv.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                   util us2 = new util();
                                                   us2.setUserId(us.getUserId());
                                                   us2.setUsuario(user.getText().toString());
                                                   us2.setSenha(pass.getText().toString());
                                                   SQLiteControl db = new SQLiteControl(c);
                                                   db.upSenhaCM(us2);
                                                   root.dismiss();
                                                   try {
                                                       File sd = Environment.getExternalStorageDirectory();
                                                       File data = Environment.getDataDirectory();

                                                       if (sd.canWrite()) {
                                                           String currentDBPath = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db";
                                                           String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db-shm";
                                                           String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db-wal";

                                                           String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                           String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                           String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                           File currentDB = new File(data, currentDBPath);
                                                           File currentDB2 = new File(data, currentDBPath2);
                                                           File currentDB3 = new File(data, currentDBPath3);
                                                           File backupDB = new File(sd, backupDBPath);
                                                           File backupDB2 = new File(sd, backupDBPath2);
                                                           File backupDB3 = new File(sd, backupDBPath3);

                                                           if (currentDB2.exists()) {
                                                               FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                               dst.transferFrom(src2, 0, src2.size());
                                                               src2.close();
                                                               dst.close();
                                                           }
                                                           if (currentDB3.exists()) {
                                                               FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                               dst.transferFrom(src3, 0, src3.size());
                                                               src3.close();
                                                               dst.close();
                                                           }
                                                           FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                           FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                           dst.transferFrom(src4, 0, src4.size());
                                                           src4.close();
                                                           dst.close();
                                                       }
                                                   } catch (Exception e2) {

                                                   }
                                               }
                                           });
                                           sv.setNegativeButton("Cancelar", null);
                                           sv.create();
                                           sv.show();
                                       }
                                   });
                                   ed2.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           LayoutInflater li = getLayoutInflater();
                                           View r = li.inflate(R.layout.password_pos_client, null);
                                           final TextInputEditText user = r.findViewById(R.id.user);
                                           final TextInputEditText pass = r.findViewById(R.id.pass);

                                           SQLiteControl db = new SQLiteControl(c);
                                           final util us = db.getSenhaMCR(1);
                                           user.setText(us.getUsuario());
                                           pass.setText(us.getSenha());

                                           AlertDialog.Builder sv = new AlertDialog.Builder(c);
                                           sv.setTitle("Atualizar Usuário / Senha MCR:");
                                           sv.setView(r);
                                           sv.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                   util us2 = new util();
                                                   us2.setUserId(us.getUserId());
                                                   us2.setUsuario(user.getText().toString());
                                                   us2.setSenha(pass.getText().toString());
                                                   SQLiteControl db = new SQLiteControl(c);
                                                   db.upSenhaMCR(us2);
                                                   root.dismiss();
                                                   try {
                                                       File sd = Environment.getExternalStorageDirectory();
                                                       File data = Environment.getDataDirectory();

                                                       if (sd.canWrite()) {
                                                           String currentDBPath = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db";
                                                           String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db-shm";
                                                           String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db-wal";

                                                           String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                           String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                           String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                           File currentDB = new File(data, currentDBPath);
                                                           File currentDB2 = new File(data, currentDBPath2);
                                                           File currentDB3 = new File(data, currentDBPath3);
                                                           File backupDB = new File(sd, backupDBPath);
                                                           File backupDB2 = new File(sd, backupDBPath2);
                                                           File backupDB3 = new File(sd, backupDBPath3);

                                                           if (currentDB2.exists()) {
                                                               FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                               dst.transferFrom(src2, 0, src2.size());
                                                               src2.close();
                                                               dst.close();
                                                           }
                                                           if (currentDB3.exists()) {
                                                               FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                               dst.transferFrom(src3, 0, src3.size());
                                                               src3.close();
                                                               dst.close();
                                                           }
                                                           FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                           FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                           dst.transferFrom(src4, 0, src4.size());
                                                           src4.close();
                                                           dst.close();
                                                       }
                                                   } catch (Exception e2) {

                                                   }
                                               }
                                           });
                                           sv.setNegativeButton("Cancelar", null);
                                           sv.create();
                                           sv.show();
                                       }
                                   });
                                   ed3.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           LayoutInflater li = getLayoutInflater();
                                           View r = li.inflate(R.layout.password_pos_client, null);
                                           final TextInputEditText user = r.findViewById(R.id.user);
                                           final TextInputEditText pass = r.findViewById(R.id.pass);

                                           SQLiteControl db = new SQLiteControl(c);
                                           final util us = db.getSuperVisor(1);
                                           pass.setText(us.getSenhaSuperVisor());
                                           user.setVisibility(View.GONE);
                                           AlertDialog.Builder sv = new AlertDialog.Builder(c);
                                           sv.setTitle("Atualizar Usuário / Senha Sup:");
                                           sv.setView(r);
                                           sv.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                   util us2 = new util();
                                                   us2.setUserId(us2.getSupervisor_id());
                                                   us2.setSenhaSuperVisor(pass.getText().toString());
                                                   SQLiteControl db = new SQLiteControl(c);
                                                   db.upSuperVisor(us2);
                                                   root.dismiss();
                                                   try {
                                                       File sd = Environment.getExternalStorageDirectory();
                                                       File data = Environment.getDataDirectory();

                                                       if (sd.canWrite()) {
                                                           String currentDBPath = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db";
                                                           String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db-shm";
                                                           String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db-wal";

                                                           String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                           String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                           String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                           File currentDB = new File(data, currentDBPath);
                                                           File currentDB2 = new File(data, currentDBPath2);
                                                           File currentDB3 = new File(data, currentDBPath3);
                                                           File backupDB = new File(sd, backupDBPath);
                                                           File backupDB2 = new File(sd, backupDBPath2);
                                                           File backupDB3 = new File(sd, backupDBPath3);

                                                           if (currentDB2.exists()) {
                                                               FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                               dst.transferFrom(src2, 0, src2.size());
                                                               src2.close();
                                                               dst.close();
                                                           }
                                                           if (currentDB3.exists()) {
                                                               FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                               dst.transferFrom(src3, 0, src3.size());
                                                               src3.close();
                                                               dst.close();
                                                           }
                                                           FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                           FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                           dst.transferFrom(src4, 0, src4.size());
                                                           src4.close();
                                                           dst.close();
                                                       }
                                                   } catch (Exception e2) {

                                                   }
                                               }
                                           });
                                           sv.setNegativeButton("Cancelar", null);
                                           sv.create();
                                           sv.show();
                                       }
                                   });

                                   ed4.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           LayoutInflater li = getLayoutInflater();
                                           View r = li.inflate(R.layout.password_pos_client, null);
                                           final TextInputEditText user = r.findViewById(R.id.user);
                                           final TextInputEditText pass = r.findViewById(R.id.pass);

                                           SQLiteControl db = new SQLiteControl(c);
                                           final util us = db.getSenhaRet(1);
                                           pass.setText(us.getSenha());
                                           user.setText(us.getUsuario());
                                           AlertDialog.Builder sv = new AlertDialog.Builder(c);
                                           sv.setTitle("Atualizar Usuário / Senha Ret:");
                                           sv.setView(r);
                                           sv.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                   util us2 = new util();
                                                   us2.setUserId(us.getUserId());
                                                   us2.setUsuario(user.getText().toString());
                                                   us2.setSenha(pass.getText().toString());
                                                   SQLiteControl db = new SQLiteControl(c);
                                                   db.upRetPass(us2);
                                                   root.dismiss();
                                                   try {
                                                       File sd = Environment.getExternalStorageDirectory();
                                                       File data = Environment.getDataDirectory();

                                                       if (sd.canWrite()) {
                                                           String currentDBPath = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db";
                                                           String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db-shm";
                                                           String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                   + "//databases//" + "MCRDB.db-wal";

                                                           String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                           String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                           String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                           File currentDB = new File(data, currentDBPath);
                                                           File currentDB2 = new File(data, currentDBPath2);
                                                           File currentDB3 = new File(data, currentDBPath3);
                                                           File backupDB = new File(sd, backupDBPath);
                                                           File backupDB2 = new File(sd, backupDBPath2);
                                                           File backupDB3 = new File(sd, backupDBPath3);

                                                           if (currentDB2.exists()) {
                                                               FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                               dst.transferFrom(src2, 0, src2.size());
                                                               src2.close();
                                                               dst.close();
                                                           }
                                                           if (currentDB3.exists()) {
                                                               FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                               dst.transferFrom(src3, 0, src3.size());
                                                               src3.close();
                                                               dst.close();
                                                           }
                                                           FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                           FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                           dst.transferFrom(src4, 0, src4.size());
                                                           src4.close();
                                                           dst.close();
                                                       }
                                                   } catch (Exception e2) {

                                                   }
                                               }
                                           });
                                           sv.setNegativeButton("Cancelar", null);
                                           sv.create();
                                           sv.show();
                                       }
                                   });
                                   AlertDialog.Builder alert = new AlertDialog.Builder(c);
                                   alert.setTitle("Gerenciar Senhas:");
                                   alert.setView(r);
                                   alert.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           String[] op = {
                                                   "Senha P/ PDV Informática",
                                                   "Senha P/ PDV Mercearia",
                                                   "Senha P/ Supervisor",
                                                   "Senha P/ Retaguarda"
                                           };
                                           AlertDialog.Builder chosser = new AlertDialog.Builder(c);
                                           chosser.setSingleChoiceItems(op, 0, new DialogInterface.OnClickListener() {

                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                   switch (which) {
                                                       case 0:
                                                           LayoutInflater li = getLayoutInflater();
                                                           View r = li.inflate(R.layout.password_pos_client, null);
                                                           final TextInputEditText user = r.findViewById(R.id.user);
                                                           final TextInputEditText pass = r.findViewById(R.id.pass);

                                                           AlertDialog.Builder sv = new AlertDialog.Builder(c);
                                                           sv.setTitle("Cadastrar Usuário / Senha CM:");
                                                           sv.setView(r);
                                                           sv.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                   util us2 = new util();
                                                                   us2.setUsuario(user.getText().toString());
                                                                   us2.setSenha(pass.getText().toString());
                                                                   SQLiteControl db = new SQLiteControl(c);
                                                                   db.setSenhaCM(us2);
                                                                   root.dismiss();
                                                                   try {
                                                                       File sd = Environment.getExternalStorageDirectory();
                                                                       File data = Environment.getDataDirectory();

                                                                       if (sd.canWrite()) {
                                                                           String currentDBPath = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db";
                                                                           String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db-shm";
                                                                           String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db-wal";

                                                                           String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                                           String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                                           String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                                           File currentDB = new File(data, currentDBPath);
                                                                           File currentDB2 = new File(data, currentDBPath2);
                                                                           File currentDB3 = new File(data, currentDBPath3);
                                                                           File backupDB = new File(sd, backupDBPath);
                                                                           File backupDB2 = new File(sd, backupDBPath2);
                                                                           File backupDB3 = new File(sd, backupDBPath3);

                                                                           if (currentDB2.exists()) {
                                                                               FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                                               FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                                               dst.transferFrom(src2, 0, src2.size());
                                                                               src2.close();
                                                                               dst.close();
                                                                           }
                                                                           if (currentDB3.exists()) {
                                                                               FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                                               FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                                               dst.transferFrom(src3, 0, src3.size());
                                                                               src3.close();
                                                                               dst.close();
                                                                           }
                                                                           FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                                           FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                                           dst.transferFrom(src4, 0, src4.size());
                                                                           src4.close();
                                                                           dst.close();
                                                                       }
                                                                   } catch (Exception e2) {

                                                                   }
                                                               }
                                                           });
                                                           sv.setNegativeButton("Cancelar", null);
                                                           sv.create();
                                                           sv.show();
                                                           break;
                                                       case 1:
                                                           LayoutInflater li2 = getLayoutInflater();
                                                           View r2 = li2.inflate(R.layout.password_pos_client, null);
                                                           final TextInputEditText user2 = r2.findViewById(R.id.user);
                                                           final TextInputEditText pass2 = r2.findViewById(R.id.pass);

                                                           AlertDialog.Builder sv2 = new AlertDialog.Builder(c);
                                                           sv2.setTitle("Cadastrar Usuário / Senha MCR:");
                                                           sv2.setView(r2);
                                                           sv2.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                   util us2 = new util();
                                                                   us2.setUsuario(user2.getText().toString());
                                                                   us2.setSenha(pass2.getText().toString());
                                                                   SQLiteControl db = new SQLiteControl(c);
                                                                   db.setSenhaMCR(us2);
                                                                   root.dismiss();
                                                                   try {
                                                                       File sd = Environment.getExternalStorageDirectory();
                                                                       File data = Environment.getDataDirectory();

                                                                       if (sd.canWrite()) {
                                                                           String currentDBPath = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db";
                                                                           String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db-shm";
                                                                           String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db-wal";

                                                                           String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                                           String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                                           String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                                           File currentDB = new File(data, currentDBPath);
                                                                           File currentDB2 = new File(data, currentDBPath2);
                                                                           File currentDB3 = new File(data, currentDBPath3);
                                                                           File backupDB = new File(sd, backupDBPath);
                                                                           File backupDB2 = new File(sd, backupDBPath2);
                                                                           File backupDB3 = new File(sd, backupDBPath3);

                                                                           if (currentDB2.exists()) {
                                                                               FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                                               FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                                               dst.transferFrom(src2, 0, src2.size());
                                                                               src2.close();
                                                                               dst.close();
                                                                           }
                                                                           if (currentDB3.exists()) {
                                                                               FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                                               FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                                               dst.transferFrom(src3, 0, src3.size());
                                                                               src3.close();
                                                                               dst.close();
                                                                           }
                                                                           FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                                           FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                                           dst.transferFrom(src4, 0, src4.size());
                                                                           src4.close();
                                                                           dst.close();
                                                                       }
                                                                   } catch (Exception e2) {

                                                                   }
                                                               }
                                                           });
                                                           sv2.setNegativeButton("Cancelar", null);
                                                           sv2.create();
                                                           sv2.show();
                                                           break;
                                                       case 2:
                                                           LayoutInflater li3 = getLayoutInflater();
                                                           View r3 = li3.inflate(R.layout.password_pos_client, null);
                                                           final TextInputEditText user3 = r3.findViewById(R.id.user);
                                                           final TextInputEditText pass3 = r3.findViewById(R.id.pass);
                                                           user3.setVisibility(View.GONE);
                                                           AlertDialog.Builder sv3 = new AlertDialog.Builder(c);
                                                           sv3.setTitle("Cadastrar Usuário / Senha Sup:");
                                                           sv3.setView(r3);
                                                           sv3.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                   util us2 = new util();
                                                                   us2.setSenhaSuperVisor(pass3.getText().toString());
                                                                   SQLiteControl db = new SQLiteControl(c);
                                                                   db.setSuperVisor(us2);
                                                                   root.dismiss();
                                                                   try {
                                                                       File sd = Environment.getExternalStorageDirectory();
                                                                       File data = Environment.getDataDirectory();

                                                                       if (sd.canWrite()) {
                                                                           String currentDBPath = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db";
                                                                           String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db-shm";
                                                                           String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db-wal";

                                                                           String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                                           String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                                           String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                                           File currentDB = new File(data, currentDBPath);
                                                                           File currentDB2 = new File(data, currentDBPath2);
                                                                           File currentDB3 = new File(data, currentDBPath3);
                                                                           File backupDB = new File(sd, backupDBPath);
                                                                           File backupDB2 = new File(sd, backupDBPath2);
                                                                           File backupDB3 = new File(sd, backupDBPath3);

                                                                           if (currentDB2.exists()) {
                                                                               FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                                               FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                                               dst.transferFrom(src2, 0, src2.size());
                                                                               src2.close();
                                                                               dst.close();
                                                                           }
                                                                           if (currentDB3.exists()) {
                                                                               FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                                               FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                                               dst.transferFrom(src3, 0, src3.size());
                                                                               src3.close();
                                                                               dst.close();
                                                                           }
                                                                           FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                                           FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                                           dst.transferFrom(src4, 0, src4.size());
                                                                           src4.close();
                                                                           dst.close();
                                                                       }
                                                                   } catch (Exception e2) {

                                                                   }
                                                               }
                                                           });
                                                           sv3.setNegativeButton("Cancelar", null);
                                                           sv3.create();
                                                           sv3.show();
                                                           break;
                                                       case 3:
                                                           LayoutInflater li4 = getLayoutInflater();
                                                           View r4 = li4.inflate(R.layout.password_pos_client, null);
                                                           final TextInputEditText user4 = r4.findViewById(R.id.user);
                                                           final TextInputEditText pass4 = r4.findViewById(R.id.pass);

                                                           AlertDialog.Builder sv4 = new AlertDialog.Builder(c);
                                                           sv4.setTitle("Cadastrar Usuário / Senha Ret:");
                                                           sv4.setView(r4);
                                                           sv4.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                   util us2 = new util();
                                                                   us2.setUsuario(user4.getText().toString());
                                                                   us2.setSenha(pass4.getText().toString());
                                                                   SQLiteControl db = new SQLiteControl(c);
                                                                   db.setRetPass(us2);
                                                                   root.dismiss();
                                                                   try {
                                                                       File sd = Environment.getExternalStorageDirectory();
                                                                       File data = Environment.getDataDirectory();

                                                                       if (sd.canWrite()) {
                                                                           String currentDBPath = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db";
                                                                           String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db-shm";
                                                                           String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                                   + "//databases//" + "MCRDB.db-wal";

                                                                           String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                                           String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                                           String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                                           File currentDB = new File(data, currentDBPath);
                                                                           File currentDB2 = new File(data, currentDBPath2);
                                                                           File currentDB3 = new File(data, currentDBPath3);
                                                                           File backupDB = new File(sd, backupDBPath);
                                                                           File backupDB2 = new File(sd, backupDBPath2);
                                                                           File backupDB3 = new File(sd, backupDBPath3);

                                                                           if (currentDB2.exists()) {
                                                                               FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                                               FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                                               dst.transferFrom(src2, 0, src2.size());
                                                                               src2.close();
                                                                               dst.close();
                                                                           }
                                                                           if (currentDB3.exists()) {
                                                                               FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                                               FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                                               dst.transferFrom(src3, 0, src3.size());
                                                                               src3.close();
                                                                               dst.close();
                                                                           }
                                                                           FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                                           FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                                           dst.transferFrom(src4, 0, src4.size());
                                                                           src4.close();
                                                                           dst.close();
                                                                       }
                                                                   } catch (Exception e2) {

                                                                   }
                                                               }
                                                           });
                                                           sv4.setNegativeButton("Cancelar", null);
                                                           sv4.create();
                                                           sv4.show();
                                                           break;
                                                   }
                                               }
                                           });
                                           chosser.create();
                                           chosser.show();
                                       }
                                   });
                                   alert.setNegativeButton("Cancelar", null);
                                   root = alert.create();
                                   root = alert.show();
                               }
                           } catch (Exception e) {
                               String[] op = {
                                       "Senha P/ PDV Informática",
                                       "Senha P/ PDV Mercearia",
                                       "Senha P/ Supervisor",
                                       "Senha P/ Retaguarda"
                               };
                               AlertDialog.Builder chosser = new AlertDialog.Builder(c);
                               chosser.setSingleChoiceItems(op, 0, new DialogInterface.OnClickListener() {

                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       switch (which) {
                                           case 0:
                                               LayoutInflater li = getLayoutInflater();
                                               View r = li.inflate(R.layout.password_pos_client, null);
                                               final TextInputEditText user = r.findViewById(R.id.user);
                                               final TextInputEditText pass = r.findViewById(R.id.pass);

                                               AlertDialog.Builder sv = new AlertDialog.Builder(c);
                                               sv.setTitle("Cadastrar Usuário / Senha CM:");
                                               sv.setView(r);
                                               sv.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       util us2 = new util();
                                                       us2.setUsuario(user.getText().toString());
                                                       us2.setSenha(pass.getText().toString());
                                                       SQLiteControl db = new SQLiteControl(c);
                                                       db.setSenhaCM(us2);
                                                       try {
                                                           File sd = Environment.getExternalStorageDirectory();
                                                           File data = Environment.getDataDirectory();

                                                           if (sd.canWrite()) {
                                                               String currentDBPath = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db";
                                                               String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db-shm";
                                                               String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db-wal";

                                                               String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                               String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                               String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                               File currentDB = new File(data, currentDBPath);
                                                               File currentDB2 = new File(data, currentDBPath2);
                                                               File currentDB3 = new File(data, currentDBPath3);
                                                               File backupDB = new File(sd, backupDBPath);
                                                               File backupDB2 = new File(sd, backupDBPath2);
                                                               File backupDB3 = new File(sd, backupDBPath3);

                                                               if (currentDB2.exists()) {
                                                                   FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                                   FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                                   dst.transferFrom(src2, 0, src2.size());
                                                                   src2.close();
                                                                   dst.close();
                                                               }
                                                               if (currentDB3.exists()) {
                                                                   FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                                   FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                                   dst.transferFrom(src3, 0, src3.size());
                                                                   src3.close();
                                                                   dst.close();
                                                               }
                                                               FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                               dst.transferFrom(src4, 0, src4.size());
                                                               src4.close();
                                                               dst.close();
                                                           }
                                                       } catch (Exception e2) {

                                                       }
                                                   }
                                               });
                                               sv.setNegativeButton("Cancelar", null);
                                               sv.create();
                                               sv.show();
                                               break;
                                           case 1:
                                               LayoutInflater li2 = getLayoutInflater();
                                               View r2 = li2.inflate(R.layout.password_pos_client, null);
                                               final TextInputEditText user2 = r2.findViewById(R.id.user);
                                               final TextInputEditText pass2 = r2.findViewById(R.id.pass);

                                               AlertDialog.Builder sv2 = new AlertDialog.Builder(c);
                                               sv2.setTitle("Cadastrar Usuário / Senha MCR:");
                                               sv2.setView(r2);
                                               sv2.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       util us2 = new util();
                                                       us2.setUsuario(user2.getText().toString());
                                                       us2.setSenha(pass2.getText().toString());
                                                       SQLiteControl db = new SQLiteControl(c);
                                                       db.setSenhaMCR(us2);
                                                       try {
                                                           File sd = Environment.getExternalStorageDirectory();
                                                           File data = Environment.getDataDirectory();

                                                           if (sd.canWrite()) {
                                                               String currentDBPath = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db";
                                                               String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db-shm";
                                                               String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db-wal";

                                                               String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                               String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                               String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                               File currentDB = new File(data, currentDBPath);
                                                               File currentDB2 = new File(data, currentDBPath2);
                                                               File currentDB3 = new File(data, currentDBPath3);
                                                               File backupDB = new File(sd, backupDBPath);
                                                               File backupDB2 = new File(sd, backupDBPath2);
                                                               File backupDB3 = new File(sd, backupDBPath3);

                                                               if (currentDB2.exists()) {
                                                                   FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                                   FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                                   dst.transferFrom(src2, 0, src2.size());
                                                                   src2.close();
                                                                   dst.close();
                                                               }
                                                               if (currentDB3.exists()) {
                                                                   FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                                   FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                                   dst.transferFrom(src3, 0, src3.size());
                                                                   src3.close();
                                                                   dst.close();
                                                               }
                                                               FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                               dst.transferFrom(src4, 0, src4.size());
                                                               src4.close();
                                                               dst.close();
                                                           }
                                                       } catch (Exception e2) {

                                                       }
                                                   }
                                               });
                                               sv2.setNegativeButton("Cancelar", null);
                                               sv2.create();
                                               sv2.show();
                                               break;
                                           case 2:
                                               LayoutInflater li3 = getLayoutInflater();
                                               View r3 = li3.inflate(R.layout.password_pos_client, null);
                                               final TextInputEditText user3 = r3.findViewById(R.id.user);
                                               final TextInputEditText pass3 = r3.findViewById(R.id.pass);
                                               user3.setVisibility(View.GONE);
                                               AlertDialog.Builder sv3 = new AlertDialog.Builder(c);
                                               sv3.setTitle("Cadastrar Usuário / Senha Sup:");
                                               sv3.setView(r3);
                                               sv3.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       util us2 = new util();
                                                       us2.setSenhaSuperVisor(pass3.getText().toString());
                                                       SQLiteControl db = new SQLiteControl(c);
                                                       db.setSuperVisor(us2);
                                                       try {
                                                           File sd = Environment.getExternalStorageDirectory();
                                                           File data = Environment.getDataDirectory();

                                                           if (sd.canWrite()) {
                                                               String currentDBPath = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db";
                                                               String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db-shm";
                                                               String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db-wal";

                                                               String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                               String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                               String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                               File currentDB = new File(data, currentDBPath);
                                                               File currentDB2 = new File(data, currentDBPath2);
                                                               File currentDB3 = new File(data, currentDBPath3);
                                                               File backupDB = new File(sd, backupDBPath);
                                                               File backupDB2 = new File(sd, backupDBPath2);
                                                               File backupDB3 = new File(sd, backupDBPath3);

                                                               if (currentDB2.exists()) {
                                                                   FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                                   FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                                   dst.transferFrom(src2, 0, src2.size());
                                                                   src2.close();
                                                                   dst.close();
                                                               }
                                                               if (currentDB3.exists()) {
                                                                   FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                                   FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                                   dst.transferFrom(src3, 0, src3.size());
                                                                   src3.close();
                                                                   dst.close();
                                                               }
                                                               FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                               dst.transferFrom(src4, 0, src4.size());
                                                               src4.close();
                                                               dst.close();
                                                           }
                                                       } catch (Exception e2) {

                                                       }
                                                   }
                                               });
                                               sv3.setNegativeButton("Cancelar", null);
                                               sv3.create();
                                               sv3.show();
                                               break;
                                           case 3:
                                               LayoutInflater li4 = getLayoutInflater();
                                               View r4 = li4.inflate(R.layout.password_pos_client, null);
                                               final TextInputEditText user4 = r4.findViewById(R.id.user);
                                               final TextInputEditText pass4 = r4.findViewById(R.id.pass);

                                               AlertDialog.Builder sv4 = new AlertDialog.Builder(c);
                                               sv4.setTitle("Cadastrar Usuário / Senha Ret:");
                                               sv4.setView(r4);
                                               sv4.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       util us2 = new util();
                                                       us2.setUsuario(user4.getText().toString());
                                                       us2.setSenha(pass4.getText().toString());
                                                       SQLiteControl db = new SQLiteControl(c);
                                                       db.setRetPass(us2);
                                                       try {
                                                           File sd = Environment.getExternalStorageDirectory();
                                                           File data = Environment.getDataDirectory();

                                                           if (sd.canWrite()) {
                                                               String currentDBPath = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db";
                                                               String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db-shm";
                                                               String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                                       + "//databases//" + "MCRDB.db-wal";

                                                               String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                                                               String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-shm";
                                                               String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db-wal";

                                                               File currentDB = new File(data, currentDBPath);
                                                               File currentDB2 = new File(data, currentDBPath2);
                                                               File currentDB3 = new File(data, currentDBPath3);
                                                               File backupDB = new File(sd, backupDBPath);
                                                               File backupDB2 = new File(sd, backupDBPath2);
                                                               File backupDB3 = new File(sd, backupDBPath3);

                                                               if (currentDB2.exists()) {
                                                                   FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                                                                   FileChannel dst = new FileOutputStream(backupDB2).getChannel();
                                                                   dst.transferFrom(src2, 0, src2.size());
                                                                   src2.close();
                                                                   dst.close();
                                                               }
                                                               if (currentDB3.exists()) {
                                                                   FileChannel src3 = new FileInputStream(currentDB3).getChannel();
                                                                   FileChannel dst = new FileOutputStream(backupDB3).getChannel();
                                                                   dst.transferFrom(src3, 0, src3.size());
                                                                   src3.close();
                                                                   dst.close();
                                                               }
                                                               FileChannel src4 = new FileInputStream(currentDB).getChannel();
                                                               FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                               dst.transferFrom(src4, 0, src4.size());
                                                               src4.close();
                                                               dst.close();
                                                           }
                                                       } catch (Exception e2) {

                                                       }
                                                   }
                                               });
                                               sv4.setNegativeButton("Cancelar", null);
                                               sv4.create();
                                               sv4.show();
                                               break;
                                       }
                                   }
                               });
                               chosser.create();
                               chosser.show();
                           }
                           break;
                       case 1:
                           startActivity(new Intent(c, MCRMain.class));
                           break;
                   }
                    }
                });
            }
            return super.onKeyDown(keyCode, event);
        }
        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event)
        {
            // TODO: Implement this method
            if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
                try {
                    File sd = Environment.getExternalStorageDirectory();
                    File data = Environment.getDataDirectory();

                    if (sd.canWrite()) {
                        String  currentDBPath= "//data//" + c.getOpPackageName()
                                + "//databases//" + "MCRDB.db";
                        String backupDBPath  = "pdvMain/data/lucas.client.service/.sqlite/MCRDB.db";
                        File dbshm = new File(data, currentDBPath + "-shm");
                        File dbwal = new File(data, currentDBPath + "-wal");
                        if (dbshm.exists()) {
                            dbshm.delete();
                        }
                        if (dbwal.exists()) {
                            dbwal.delete();
                        }
                        File currentDB = new File(data, currentDBPath);
                        File backupDB = new File(sd, backupDBPath);
                        FileChannel src = new FileInputStream(backupDB).getChannel();
                        FileChannel dst = new FileOutputStream(currentDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                        finish();
                        startActivity(getIntent());
                    }
                } catch (Exception e) {
                }
            }
            return super.onKeyUp(keyCode, event);
        }
        private void requestPermission(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                try{

                    Intent it = new Intent();
                    it.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                    it.setData(uri);
                    storageActivityResultLauncher.launch(it);
                }catch (Exception e){

                    Intent it2 = new Intent();
                    it2.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    storageActivityResultLauncher.launch(it2);
                }
            } else {
                ActivityCompat.requestPermissions(MCRMain.this, new String[]{WRITE_EXTERNAL_STORAGE, MANAGE_EXTERNAL_STORAGE,  READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
        private ActivityResultLauncher<Intent> storageActivityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>(){

                            @Override
                            public void onActivityResult(ActivityResult o) {
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                                    //Android is 11 (R) or above
                                    if(Environment.isExternalStorageManager()){
                                        //Manage External Storage Permissions Granted

                                        Log.d(TAG, "onActivityResult: Manage External Storage Permissions Granted");
                                    }else{
                                        Toast.makeText(c, "Storage Permissions Denied", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    //Below android 11

                                }
                            }
                        });

        public boolean checkPermission(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                return Environment.isExternalStorageManager();
            } else {
                int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

                return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0){
                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(read && write){
                    Toast.makeText(c, "Storage Permissions Granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(c, "Storage Permissions Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
