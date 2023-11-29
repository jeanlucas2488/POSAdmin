package lucas.client.service.pos.admin.financeiro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.net.wifi.rtt.RangingResultCallback;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lucas.client.service.pos.admin.MainActivity;
import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.etc.util;
import lucas.client.service.pos.admin.financeiro.adapter.BoletosAdapter;
import lucas.client.service.pos.admin.sqlite.SQLiteControl;

public class Boletos extends AppCompatActivity {
    public String ParentdirPath="";
    public ArrayList<String> theNamesOfFiles;
    public ArrayList<Integer> intImages;
    public CustomList customList;
    AlertDialog al, al2;
    public String dirPath="";
    public File dir;
    Context c = this;
    TextInputEditText data, vencimento, valor, tipo, status, descricao;
    EditText imagem;
    ListView lr;
    BoletosAdapter ad;
    ImageView select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boletos);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        SQLiteControl db = new SQLiteControl(c);
        List<util> lt = db.getBoletos();
        ImageButton add = (ImageButton) findViewById(R.id.contasAdd);
        ListView l = (ListView) findViewById(R.id.list);
        l.setEmptyView(findViewById(android.R.id.empty));
        ad = new BoletosAdapter(c, lt);
        l.setAdapter(ad);
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
                imagem = r.findViewById(R.id.imP);
                select = r.findViewById(R.id.select);
                ImageView im = r.findViewById(R.id.im);
                ImageView up_im = r.findViewById(R.id.up_im);
                RelativeLayout rel = r.findViewById(R.id.laySel);
                RelativeLayout rel2 = r.findViewById(R.id.imres);
                rel.setVisibility(View.GONE);
                rel2.setVisibility(View.VISIBLE);
                data.setText(lt.get(position).getBdata());
                vencimento.setText(lt.get(position).getBvencimento());
                valor.setText(lt.get(position).getBvalor());
                tipo.setText(lt.get(position).getBtipo());
                status.setText(lt.get(position).getBstatus());
                descricao.setText(lt.get(position).getBdescricao());
                byte[] res = lt.get(position).getBImagem();
                Bitmap bt = BitmapFactory.decodeByteArray(res, 0, res.length);
                im.setImageBitmap(bt);

                up_im.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 LayoutInflater li = getLayoutInflater();
                                                 View r2 = li.inflate(R.layout.chooser, null);
                                                 lr = r2.findViewById(R.id.list);
                                                 Button bck = r2.findViewById(R.id.back);
                                                 intImages = new ArrayList<Integer>();
                                                 theNamesOfFiles = new ArrayList<String>();
                                                 ListView lt = (ListView) findViewById(R.id.list);
                                                 if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                                                     dirPath = String.valueOf(android.os.Environment.getExternalStorageDirectory());
                                                 }

                                                 ///mounted
                                                 RefreshListView();
                                                 set_Adapter();
                                                 bck.setOnClickListener(new View.OnClickListener() {
                                                     public void onClick(View v) {
                                                         if (dirPath != "" && dirPath != "/") {
                                                             String[] folders = dirPath.split("\\/");
                                                             String[] folders2 = {};
                                                             folders2 = Arrays.copyOf(folders, folders.length - 1);
                                                             dirPath = TextUtils.join("/", folders2);
                                                         }

                                                         if (dirPath == "") {
                                                             dirPath = "/";
                                                         }
                                                         RefreshListView();
                                                         RefreshAdapter();

                                                     }
                                                 });
                                                 lr.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                     @Override
                                                     public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                                                         // TODO: Implement this method
                                                         try {
                                                             ParentdirPath = dirPath + "/..";
                                                             dirPath = dirPath + "/" + theNamesOfFiles.get(p3);

                                                             File f = new File(dirPath);
                                                             if (f.isDirectory()) {
                                                                 RefreshListView();
                                                                 RefreshAdapter();
                                                             } else {
                                                                 Uri url = Uri.parse(dirPath);
                                                                 im.setImageURI(url);
                                                                 al2.dismiss();
                                                             }

                                                         } catch (Exception e) {
                                                             Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                                         }

                                                     }
                                                 });
                                                 AlertDialog.Builder ch = new AlertDialog.Builder(c);
                                                 ch.setView(r2);
                                                al2 = ch.create();
                                                al2 =  ch.show();
                                             }
                                         });
                        AlertDialog.Builder bs = new AlertDialog.Builder(c);
                        bs.setTitle("Visualizar Boleto:");
                        bs.setView(r);
                        bs.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                util us = new util();
                                us.setBolId(lt.get(position).getBolId());
                                us.setBdata(data.getText().toString());
                                us.setBvencimento(vencimento.getText().toString());
                                us.setBvalor(valor.getText().toString());
                                us.setBtipo(tipo.getText().toString());
                                us.setBstatus(status.getText().toString());
                                us.setBdescricao(descricao.getText().toString());
                                try {
                                    if(!dirPath.toString().equals("")){
                                        FileInputStream fs = new FileInputStream(dirPath);
                                        byte[] lm = new byte[fs.available()];
                                        fs.read(lm);
                                        us.setBImagem(lm);
                                    } else {
                                        us.setBImagem(lt.get(position).getBImagem());
                                    }
                                } catch (IOException e) {
                                }
                                SQLiteControl db = new SQLiteControl(c);
                                db.upBoleto(us);
                                lt.clear();
                                lt.addAll(db.getBoletos());
                                ad.notifyDataSetChanged();
                                try {
                                    File sd = Environment.getExternalStorageDirectory();
                                    File data = Environment.getDataDirectory();

                                    if (sd.canWrite()) {
                                        String currentDBPath = "//data//" + c.getOpPackageName()
                                                + "//databases//" + "myDB.db";
                                        String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                + "//databases//" + "myDB.db-shm";
                                        String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                + "//databases//" + "myDB.db-wal";

                                        String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/myDB.db";
                                        String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/myDB.db-shm";
                                        String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/myDB.db-wal";

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
                        bs.setNegativeButton("Cancelar", null);
                        bs.create();
                        bs.show();
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater li = getLayoutInflater();
                        View r = li.inflate(R.layout.cadastro_boletos, null);
                        data = r.findViewById(R.id.bdata);
                        vencimento = r.findViewById(R.id.bvencimento);
                        valor = r.findViewById(R.id.bvalor);
                        tipo = r.findViewById(R.id.btipo);
                        status = r.findViewById(R.id.status);
                        descricao = r.findViewById(R.id.bdesc);
                        imagem = r.findViewById(R.id.imP);
                        select = r.findViewById(R.id.select);
                        RelativeLayout rel = r.findViewById(R.id.laySel);
                        RelativeLayout rel2 = r.findViewById(R.id.imres);
                        rel2.setVisibility(View.GONE);
                        AlertDialog.Builder bs = new AlertDialog.Builder(c);
                        bs.setTitle("Cadastrar Boleto:");
                        bs.setView(r);
                        bs.setPositiveButton("Cadstrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                util us = new util();
                                us.setBdata(data.getText().toString());
                                us.setBvencimento(vencimento.getText().toString());
                                us.setBvalor(valor.getText().toString());
                                us.setBtipo(tipo.getText().toString());
                                us.setBstatus(status.getText().toString());
                                us.setBdescricao(descricao.getText().toString());
                                try {
                                    FileInputStream fs = new FileInputStream(dirPath);
                                    byte[] lm = new byte[fs.available()];
                                    fs.read(lm);
                                    us.setBImagem(lm);
                                } catch (IOException e) {
                                }
                                SQLiteControl db = new SQLiteControl(c);
                                db.setBoleto(us);
                                lt.clear();
                                lt.addAll(db.getBoletos());
                                ad.notifyDataSetChanged();
                                try {
                                    File sd = Environment.getExternalStorageDirectory();
                                    File data = Environment.getDataDirectory();

                                    if (sd.canWrite()) {
                                        String currentDBPath = "//data//" + c.getOpPackageName()
                                                + "//databases//" + "myDB.db";
                                        String currentDBPath2 = "//data//" + c.getOpPackageName()
                                                + "//databases//" + "myDB.db-shm";
                                        String currentDBPath3 = "//data//" + c.getOpPackageName()
                                                + "//databases//" + "myDB.db-wal";

                                        String backupDBPath = "pdvMain/data/lucas.client.service/.sqlite/myDB.db";
                                        String backupDBPath2 = "pdvMain/data/lucas.client.service/.sqlite/myDB.db-shm";
                                        String backupDBPath3 = "pdvMain/data/lucas.client.service/.sqlite/myDB.db-wal";

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
                        bs.setNegativeButton("Cancelar", null);
                        bs.create();
                        bs.show();
                        select.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LayoutInflater li = getLayoutInflater();
                                View r2 = li.inflate(R.layout.chooser, null);
                                lr = r2.findViewById(R.id.list);
                                Button bck = r2.findViewById(R.id.back);
                                intImages = new ArrayList<Integer>();
                                theNamesOfFiles = new ArrayList<String>();
                                ListView lt = (ListView) findViewById(R.id.list);
                                if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                                    dirPath = String.valueOf(android.os.Environment.getExternalStorageDirectory());
                                }

                                ///mounted
                                RefreshListView();
                                set_Adapter();
                                bck.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        if (dirPath != "" && dirPath != "/") {
                                            String[] folders = dirPath.split("\\/");
                                            String[] folders2 = {};
                                            folders2 = Arrays.copyOf(folders, folders.length - 1);
                                            dirPath = TextUtils.join("/", folders2);
                                        }

                                        if (dirPath == "") {
                                            dirPath = "/";
                                        }
                                        RefreshListView();
                                        RefreshAdapter();

                                    }
                                });
                                lr.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                                        // TODO: Implement this method
                                        try {
                                            ParentdirPath = dirPath + "/..";
                                            dirPath = dirPath + "/" + theNamesOfFiles.get(p3);

                                            File f = new File(dirPath);
                                            if (f.isDirectory()) {
                                                RefreshListView();
                                                RefreshAdapter();
                                            } else {
                                                imagem.getText().clear();
                                                imagem.setText(dirPath.toString());
                                                al.dismiss();
                                            }

                                        } catch (Exception e) {
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
                    }
                });


            }

            public void RefreshListView() {
                try {

                    dir = new File(dirPath);
                    File[] filelist = dir.listFiles();

                    //reset ArrayLists
                    theNamesOfFiles.clear();
                    intImages.clear();

                    for (int i = 0; i < filelist.length; i++) {

                        theNamesOfFiles.add(filelist[i].getName());
                        //   intImages[i] = R.drawable.folder;

                        if (filelist[i].isDirectory() == true) {
                            intImages.add(R.drawable.folder);
                        } else if (filelist[i].isFile() == true) {
                            intImages.add(R.drawable.file);
                        } else {
                            intImages.add(R.drawable.file);
                        }
                    }
                } catch (Exception e) {
//        String error = e.toString() + "\n\nMessage: " + e.getMessage();
//        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                }

            }

            public void set_Adapter() {
                customList = new CustomList();
                lr.setAdapter(customList);
            }

            public void RefreshAdapter() {
                customList.notifyDataSetChanged();
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
                    ImageView imageView = view1.findViewById(R.id.ItemIcon);
                    TextView txtPath = view1.findViewById(R.id.ItemName);

                    imageView.setImageResource(intImages.get(i));
                    txtPath.setText(theNamesOfFiles.get(i));

                    return view1;
                }
            }

}
