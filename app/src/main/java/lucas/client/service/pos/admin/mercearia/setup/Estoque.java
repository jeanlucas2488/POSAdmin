package lucas.client.service.pos.admin.mercearia.setup;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.etc.util;
import lucas.client.service.pos.admin.mercearia.setup.adapters.EstoqueAdapter;
import lucas.client.service.pos.admin.mercearia.databases.*;

public class Estoque extends AppCompatActivity
{
	SQLiteControl db;
	ImageButton show1, show2, show3, show4, show5, show6, show7, show8, show9, show10,
	            hide1, hide2, hide3, hide4, hide5, hide6, hide7, hide8, hide9, hide10;
	EstoqueAdapter ad, ad2, ad3, ad4, ad5, ad6, ad7, ad8, ad9, ad10;
	LinearLayout l1, l2, l3, l4, l5, l6, l7, l8, l9, l10;
	ListView li1, li2, li3, li4, li5, li6, li7, li8, li9, li10;
	RelativeLayout rel1, rel2, rel3, rel4, rel5, rel6, rel7, rel8, rel9, rel10;
	List<util> lt1, lt2, lt3, lt4, lt5, lt6, lt7, lt8, lt9, lt10;
	TextView tp1, tp2, tp3, tp4, tp5, tp6, tp7, tp8, tp9, tp10;
	Context c = this;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estoque_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		db = new SQLiteControl(this);
		lt1 = db.findP1();
		lt2 = db.findP2();
		lt3 = db.findP3();
		lt4 = db.findP4();
		lt5 = db.findP5();
		lt6 = db.findP6();
		lt7 = db.findP7();
		lt8 = db.findP8();
		lt9 = db.findP9();
		lt10 = db.findP10();
		
		show1 = (ImageButton) findViewById(R.id.showp1);
		hide1 = (ImageButton) findViewById(R.id.hidep1);
		
		show2 = (ImageButton) findViewById(R.id.showp2);
		hide2 = (ImageButton) findViewById(R.id.hidep2);
		
		show3 = (ImageButton) findViewById(R.id.showp3);
		hide3 = (ImageButton) findViewById(R.id.hidep3);
		
		show4 = (ImageButton) findViewById(R.id.showp4);
		hide4 = (ImageButton) findViewById(R.id.hidep4);
		
		show5 = (ImageButton) findViewById(R.id.showp5);
		hide5 = (ImageButton) findViewById(R.id.hidep5);
		
		show6 = (ImageButton) findViewById(R.id.showp6);
		hide6 = (ImageButton) findViewById(R.id.hidep6);
		
		show7 = (ImageButton) findViewById(R.id.showp7);
		hide7 = (ImageButton) findViewById(R.id.hidep7);
		
		show8 = (ImageButton) findViewById(R.id.showp8);
		hide8 = (ImageButton) findViewById(R.id.hidep8);
		
		show9 = (ImageButton) findViewById(R.id.showp9);
		hide9 = (ImageButton) findViewById(R.id.hidep9);
		
		show10 = (ImageButton) findViewById(R.id.showp10);
		hide10 = (ImageButton) findViewById(R.id.hidep10);
		
		l1 = (LinearLayout) findViewById(R.id.page1);
		l2 = (LinearLayout) findViewById(R.id.page2);
		l3 = (LinearLayout) findViewById(R.id.page3);
		l4 = (LinearLayout) findViewById(R.id.page4);
		l5 = (LinearLayout) findViewById(R.id.page5);
		l6 = (LinearLayout) findViewById(R.id.page6);
		l7 = (LinearLayout) findViewById(R.id.page7);
		l8 = (LinearLayout) findViewById(R.id.page8);
		l9 = (LinearLayout) findViewById(R.id.page9);
		l10 = (LinearLayout) findViewById(R.id.page10);
		
		l1.setVisibility(View.GONE);
		l2.setVisibility(View.GONE);
		l3.setVisibility(View.GONE);
		l4.setVisibility(View.GONE);
		l5.setVisibility(View.GONE);
		l6.setVisibility(View.GONE);
		l7.setVisibility(View.GONE);
		l8.setVisibility(View.GONE);
		l9.setVisibility(View.GONE);
		l10.setVisibility(View.GONE);
		rel1 = (RelativeLayout) findViewById(R.id.rel1);
		rel2 = (RelativeLayout) findViewById(R.id.rel2);
		rel3 = (RelativeLayout) findViewById(R.id.rel3);
		rel4 = (RelativeLayout) findViewById(R.id.rel4);
		rel5 = (RelativeLayout) findViewById(R.id.rel5);
		rel6 = (RelativeLayout) findViewById(R.id.rel6);
		rel7 = (RelativeLayout) findViewById(R.id.rel7);
		rel8 = (RelativeLayout) findViewById(R.id.rel8);
		rel9 = (RelativeLayout) findViewById(R.id.rel9);
		rel10 = (RelativeLayout) findViewById(R.id.rel10);
		
		
		tp1 = (TextView) findViewById(R.id.pgT1);
		tp2 = (TextView) findViewById(R.id.pgT2);
		tp3 = (TextView) findViewById(R.id.pgT3);
		tp4 = (TextView) findViewById(R.id.pgT4);
		tp5 = (TextView) findViewById(R.id.pgT5);
		tp6 = (TextView) findViewById(R.id.pgT6);
		tp7 = (TextView) findViewById(R.id.pgT7);
		tp8 = (TextView) findViewById(R.id.pgT8);
		tp9 = (TextView) findViewById(R.id.pgT9);
		tp10 = (TextView) findViewById(R.id.pgT10);
		
		li1 = (ListView) findViewById(R.id.listp1);
		li2 = (ListView) findViewById(R.id.listp2);
		li3 = (ListView) findViewById(R.id.listp3);
		li4 = (ListView) findViewById(R.id.listp4);
		li5 = (ListView) findViewById(R.id.listp5);
		li6 = (ListView) findViewById(R.id.listp6);
		li7 = (ListView) findViewById(R.id.listp7);
		li8 = (ListView) findViewById(R.id.listp8);
		li9 = (ListView) findViewById(R.id.listp9);
		li10 = (ListView) findViewById(R.id.listp10);
		ad = new EstoqueAdapter(this, lt1);
		ad2 = new EstoqueAdapter(this, lt2);
		ad3 = new EstoqueAdapter(this, lt3);
		ad4 = new EstoqueAdapter(this, lt4);
		ad5 = new EstoqueAdapter(this, lt5);
		ad6 = new EstoqueAdapter(this, lt6);
		ad7 = new EstoqueAdapter(this, lt7);
		ad8 = new EstoqueAdapter(this, lt8);
		ad9 = new EstoqueAdapter(this, lt9);
		ad10 = new EstoqueAdapter(this, lt10);
		li1.setAdapter(ad);
		li2.setAdapter(ad2);
		li3.setAdapter(ad3);
		li4.setAdapter(ad4);
		li5.setAdapter(ad5);
		li6.setAdapter(ad6);
		li7.setAdapter(ad7);
		li8.setAdapter(ad8);
		li9.setAdapter(ad9);
		li10.setAdapter(ad10);
		li1.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					LayoutInflater lay = getLayoutInflater();
					View r = lay.inflate(R.layout.estoque_view_product, null);
					final TextInputEditText prod = r.findViewById(R.id.pessoa);
					final TextInputEditText quant = r.findViewById(R.id.quant);
					final TextInputEditText valor = r.findViewById(R.id.valor);
					final TextInputEditText etotal = r.findViewById(R.id.etotal);
					final TextInputEditText forn = r.findViewById(R.id.forn);
					final TextInputEditText dIn = r.findViewById(R.id.dIn);
					final TextInputEditText dout = r.findViewById(R.id.dOut);
					final TextInputEditText tipo = r.findViewById(R.id.tipo);
					
					prod.setText(lt1.get(p3).getProd());
					quant.setText(lt1.get(p3).getQuant());
					valor.setText(lt1.get(p3).getValor());
					etotal.setText(lt1.get(p3).getETotal());
					forn.setText(lt1.get(p3).getForn());
					dIn.setText(lt1.get(p3).getDataIn());
					dout.setText(lt1.get(p3).getDataOut());
					tipo.setText(lt1.get(p3).getTipo());
					
					AlertDialog.Builder alert = new AlertDialog.Builder(c);
					alert.setTitle("Visualizar Produto");
					alert.setView(r);
					alert.setPositiveButton("Salvar Alterações", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								SQLiteControl db = new SQLiteControl(c);
								util us = new util();
								us.setId(lt1.get(p3).getId());
								us.setTable("paginaOne");
								us.setProd(prod.getText().toString());
								us.setQuant(quant.getText().toString());
								us.setValor(valor.getText().toString());
								us.setDataIn(dIn.getText().toString());
								us.setDataOut(dout.getText().toString());
								us.setForn(forn.getText().toString());
								us.setETotal(etotal.getText().toString());
								us.setTipo(tipo.getText().toString());
								us.setImage(lt1.get(p3).getImage());
								db.postUp(us);
								lt1.clear();
								lt1.addAll(db.findP1());
								ad.notifyDataSetChanged();
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
							}
					});
					alert.setNegativeButton("Cancelar", null);
					alert.create();
					alert.show();
				}
		});
		
		li2.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					LayoutInflater lay = getLayoutInflater();
					View r = lay.inflate(R.layout.estoque_view_product, null);
					final TextInputEditText prod = r.findViewById(R.id.pessoa);
					final TextInputEditText quant = r.findViewById(R.id.quant);
					final TextInputEditText valor = r.findViewById(R.id.valor);
					final TextInputEditText etotal = r.findViewById(R.id.etotal);
					final TextInputEditText forn = r.findViewById(R.id.forn);
					final TextInputEditText dIn = r.findViewById(R.id.dIn);
					final TextInputEditText dout = r.findViewById(R.id.dOut);
					final TextInputEditText tipo = r.findViewById(R.id.tipo);

					prod.setText(lt2.get(p3).getProd());
					quant.setText(lt2.get(p3).getQuant());
					valor.setText(lt2.get(p3).getValor());
					etotal.setText(lt2.get(p3).getETotal());
					forn.setText(lt2.get(p3).getForn());
					dIn.setText(lt2.get(p3).getDataIn());
					dout.setText(lt2.get(p3).getDataOut());
					tipo.setText(lt2.get(p3).getTipo());

					AlertDialog.Builder alert = new AlertDialog.Builder(c);
					alert.setTitle("Visualizar Produto");
					alert.setView(r);
					alert.setPositiveButton("Salvar Alterações", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								SQLiteControl db = new SQLiteControl(c);
								util us = new util();
								us.setId(lt2.get(p3).getId());
								us.setTable("paginaTwo");
								us.setProd(prod.getText().toString());
								us.setQuant(quant.getText().toString());
								us.setValor(valor.getText().toString());
								us.setDataIn(dIn.getText().toString());
								us.setDataOut(dout.getText().toString());
								us.setForn(forn.getText().toString());
								us.setETotal(etotal.getText().toString());
								us.setTipo(tipo.getText().toString());
								us.setImage(lt2.get(p3).getImage());
								db.postUp(us);
								lt2.clear();
								lt2.addAll(db.findP2());
								ad.notifyDataSetChanged();
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
							}
						});
					alert.setNegativeButton("Cancelar", null);
					alert.create();
					alert.show();
				}
			});
			
		li3.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					LayoutInflater lay = getLayoutInflater();
					View r = lay.inflate(R.layout.estoque_view_product, null);
					final TextInputEditText prod = r.findViewById(R.id.pessoa);
					final TextInputEditText quant = r.findViewById(R.id.quant);
					final TextInputEditText valor = r.findViewById(R.id.valor);
					final TextInputEditText etotal = r.findViewById(R.id.etotal);
					final TextInputEditText forn = r.findViewById(R.id.forn);
					final TextInputEditText dIn = r.findViewById(R.id.dIn);
					final TextInputEditText dout = r.findViewById(R.id.dOut);
					final TextInputEditText tipo = r.findViewById(R.id.tipo);

					prod.setText(lt3.get(p3).getProd());
					quant.setText(lt3.get(p3).getQuant());
					valor.setText(lt3.get(p3).getValor());
					etotal.setText(lt3.get(p3).getETotal());
					forn.setText(lt3.get(p3).getForn());
					dIn.setText(lt3.get(p3).getDataIn());
					dout.setText(lt3.get(p3).getDataOut());
					tipo.setText(lt3.get(p3).getTipo());

					AlertDialog.Builder alert = new AlertDialog.Builder(c);
					alert.setTitle("Visualizar Produto");
					alert.setView(r);
					alert.setPositiveButton("Salvar Alterações", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								SQLiteControl db = new SQLiteControl(c);
								util us = new util();
								us.setId(lt3.get(p3).getId());
								us.setTable("paginaThree");
								us.setProd(prod.getText().toString());
								us.setQuant(quant.getText().toString());
								us.setValor(valor.getText().toString());
								us.setDataIn(dIn.getText().toString());
								us.setDataOut(dout.getText().toString());
								us.setForn(forn.getText().toString());
								us.setETotal(etotal.getText().toString());
								us.setTipo(tipo.getText().toString());
								us.setImage(lt3.get(p3).getImage());
								db.postUp(us);
								lt3.clear();
								lt3.addAll(db.findP3());
								ad.notifyDataSetChanged();
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
							}
						});
					alert.setNegativeButton("Cancelar", null);
					alert.create();
					alert.show();
				}
			});
			
		li4.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					LayoutInflater lay = getLayoutInflater();
					View r = lay.inflate(R.layout.estoque_view_product, null);
					final TextInputEditText prod = r.findViewById(R.id.pessoa);
					final TextInputEditText quant = r.findViewById(R.id.quant);
					final TextInputEditText valor = r.findViewById(R.id.valor);
					final TextInputEditText etotal = r.findViewById(R.id.etotal);
					final TextInputEditText forn = r.findViewById(R.id.forn);
					final TextInputEditText dIn = r.findViewById(R.id.dIn);
					final TextInputEditText dout = r.findViewById(R.id.dOut);
					final TextInputEditText tipo = r.findViewById(R.id.tipo);

					prod.setText(lt4.get(p3).getProd());
					quant.setText(lt4.get(p3).getQuant());
					valor.setText(lt4.get(p3).getValor());
					etotal.setText(lt4.get(p3).getETotal());
					forn.setText(lt4.get(p3).getForn());
					dIn.setText(lt4.get(p3).getDataIn());
					dout.setText(lt4.get(p3).getDataOut());
					tipo.setText(lt4.get(p3).getTipo());

					AlertDialog.Builder alert = new AlertDialog.Builder(c);
					alert.setTitle("Visualizar Produto");
					alert.setView(r);
					alert.setPositiveButton("Salvar Alterações", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								SQLiteControl db = new SQLiteControl(c);
								util us = new util();
								us.setId(lt4.get(p3).getId());
								us.setTable("paginaFour");
								us.setProd(prod.getText().toString());
								us.setQuant(quant.getText().toString());
								us.setValor(valor.getText().toString());
								us.setDataIn(dIn.getText().toString());
								us.setDataOut(dout.getText().toString());
								us.setForn(forn.getText().toString());
								us.setETotal(etotal.getText().toString());
								us.setTipo(tipo.getText().toString());
								us.setImage(lt4.get(p3).getImage());
								db.postUp(us);
								lt4.clear();
								lt4.addAll(db.findP4());
								ad.notifyDataSetChanged();
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
							}
						});
					alert.setNegativeButton("Cancelar", null);
					alert.create();
					alert.show();
				}
			});
			
		li5.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					LayoutInflater lay = getLayoutInflater();
					View r = lay.inflate(R.layout.estoque_view_product, null);
					final TextInputEditText prod = r.findViewById(R.id.pessoa);
					final TextInputEditText quant = r.findViewById(R.id.quant);
					final TextInputEditText valor = r.findViewById(R.id.valor);
					final TextInputEditText etotal = r.findViewById(R.id.etotal);
					final TextInputEditText forn = r.findViewById(R.id.forn);
					final TextInputEditText dIn = r.findViewById(R.id.dIn);
					final TextInputEditText dout = r.findViewById(R.id.dOut);
					final TextInputEditText tipo = r.findViewById(R.id.tipo);

					prod.setText(lt5.get(p3).getProd());
					quant.setText(lt5.get(p3).getQuant());
					valor.setText(lt5.get(p3).getValor());
					etotal.setText(lt5.get(p3).getETotal());
					forn.setText(lt5.get(p3).getForn());
					dIn.setText(lt5.get(p3).getDataIn());
					dout.setText(lt5.get(p3).getDataOut());
					tipo.setText(lt5.get(p3).getTipo());

					AlertDialog.Builder alert = new AlertDialog.Builder(c);
					alert.setTitle("Visualizar Produto");
					alert.setView(r);
					alert.setPositiveButton("Salvar Alterações", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								SQLiteControl db = new SQLiteControl(c);
								util us = new util();
								us.setId(lt5.get(p3).getId());
								us.setTable("paginaFive");
								us.setProd(prod.getText().toString());
								us.setQuant(quant.getText().toString());
								us.setValor(valor.getText().toString());
								us.setDataIn(dIn.getText().toString());
								us.setDataOut(dout.getText().toString());
								us.setForn(forn.getText().toString());
								us.setETotal(etotal.getText().toString());
								us.setTipo(tipo.getText().toString());
								us.setImage(lt5.get(p3).getImage());
								db.postUp(us);
								lt5.clear();
								lt5.addAll(db.findP5());
								ad.notifyDataSetChanged();
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
							}
						});
					alert.setNegativeButton("Cancelar", null);
					alert.create();
					alert.show();
				}
			});
		li6.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					LayoutInflater lay = getLayoutInflater();
					View r = lay.inflate(R.layout.estoque_view_product, null);
					final TextInputEditText prod = r.findViewById(R.id.pessoa);
					final TextInputEditText quant = r.findViewById(R.id.quant);
					final TextInputEditText valor = r.findViewById(R.id.valor);
					final TextInputEditText etotal = r.findViewById(R.id.etotal);
					final TextInputEditText forn = r.findViewById(R.id.forn);
					final TextInputEditText dIn = r.findViewById(R.id.dIn);
					final TextInputEditText dout = r.findViewById(R.id.dOut);
					final TextInputEditText tipo = r.findViewById(R.id.tipo);

					prod.setText(lt6.get(p3).getProd());
					quant.setText(lt6.get(p3).getQuant());
					valor.setText(lt6.get(p3).getValor());
					etotal.setText(lt6.get(p3).getETotal());
					forn.setText(lt6.get(p3).getForn());
					dIn.setText(lt6.get(p3).getDataIn());
					dout.setText(lt6.get(p3).getDataOut());
					tipo.setText(lt6.get(p3).getTipo());

					AlertDialog.Builder alert = new AlertDialog.Builder(c);
					alert.setTitle("Visualizar Produto");
					alert.setView(r);
					alert.setPositiveButton("Salvar Alterações", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								SQLiteControl db = new SQLiteControl(c);
								util us = new util();
								us.setId(lt6.get(p3).getId());
								us.setTable("paginaSix");
								us.setProd(prod.getText().toString());
								us.setQuant(quant.getText().toString());
								us.setValor(valor.getText().toString());
								us.setDataIn(dIn.getText().toString());
								us.setDataOut(dout.getText().toString());
								us.setForn(forn.getText().toString());
								us.setETotal(etotal.getText().toString());
								us.setTipo(tipo.getText().toString());
								us.setImage(lt6.get(p3).getImage());
								db.postUp(us);
								lt6.clear();
								lt6.addAll(db.findP6());
								ad.notifyDataSetChanged();
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
							}
						});
					alert.setNegativeButton("Cancelar", null);
					alert.create();
					alert.show();
				}
			});
		li7.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					LayoutInflater lay = getLayoutInflater();
					View r = lay.inflate(R.layout.estoque_view_product, null);
					final TextInputEditText prod = r.findViewById(R.id.pessoa);
					final TextInputEditText quant = r.findViewById(R.id.quant);
					final TextInputEditText valor = r.findViewById(R.id.valor);
					final TextInputEditText etotal = r.findViewById(R.id.etotal);
					final TextInputEditText forn = r.findViewById(R.id.forn);
					final TextInputEditText dIn = r.findViewById(R.id.dIn);
					final TextInputEditText dout = r.findViewById(R.id.dOut);
					final TextInputEditText tipo = r.findViewById(R.id.tipo);

					prod.setText(lt7.get(p3).getProd());
					quant.setText(lt7.get(p3).getQuant());
					valor.setText(lt7.get(p3).getValor());
					etotal.setText(lt7.get(p3).getETotal());
					forn.setText(lt7.get(p3).getForn());
					dIn.setText(lt7.get(p3).getDataIn());
					dout.setText(lt7.get(p3).getDataOut());
					tipo.setText(lt7.get(p3).getTipo());

					AlertDialog.Builder alert = new AlertDialog.Builder(c);
					alert.setTitle("Visualizar Produto");
					alert.setView(r);
					alert.setPositiveButton("Salvar Alterações", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								SQLiteControl db = new SQLiteControl(c);
								util us = new util();
								us.setId(lt7.get(p3).getId());
								us.setTable("paginaSeven");
								us.setProd(prod.getText().toString());
								us.setQuant(quant.getText().toString());
								us.setValor(valor.getText().toString());
								us.setDataIn(dIn.getText().toString());
								us.setDataOut(dout.getText().toString());
								us.setForn(forn.getText().toString());
								us.setETotal(etotal.getText().toString());
								us.setTipo(tipo.getText().toString());
								us.setImage(lt7.get(p3).getImage());
								db.postUp(us);
								lt7.clear();
								lt7.addAll(db.findP7());
								ad.notifyDataSetChanged();
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
							}
						});
					alert.setNegativeButton("Cancelar", null);
					alert.create();
					alert.show();
				}
			});
		li8.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					LayoutInflater lay = getLayoutInflater();
					View r = lay.inflate(R.layout.estoque_view_product, null);
					final TextInputEditText prod = r.findViewById(R.id.pessoa);
					final TextInputEditText quant = r.findViewById(R.id.quant);
					final TextInputEditText valor = r.findViewById(R.id.valor);
					final TextInputEditText etotal = r.findViewById(R.id.etotal);
					final TextInputEditText forn = r.findViewById(R.id.forn);
					final TextInputEditText dIn = r.findViewById(R.id.dIn);
					final TextInputEditText dout = r.findViewById(R.id.dOut);
					final TextInputEditText tipo = r.findViewById(R.id.tipo);

					prod.setText(lt8.get(p3).getProd());
					quant.setText(lt8.get(p3).getQuant());
					valor.setText(lt8.get(p3).getValor());
					etotal.setText(lt8.get(p3).getETotal());
					forn.setText(lt8.get(p3).getForn());
					dIn.setText(lt8.get(p3).getDataIn());
					dout.setText(lt8.get(p3).getDataOut());
					tipo.setText(lt8.get(p3).getTipo());

					AlertDialog.Builder alert = new AlertDialog.Builder(c);
					alert.setTitle("Visualizar Produto");
					alert.setView(r);
					alert.setPositiveButton("Salvar Alterações", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								SQLiteControl db = new SQLiteControl(c);
								util us = new util();
								us.setId(lt8.get(p3).getId());
								us.setTable("paginaEight");
								us.setProd(prod.getText().toString());
								us.setQuant(quant.getText().toString());
								us.setValor(valor.getText().toString());
								us.setDataIn(dIn.getText().toString());
								us.setDataOut(dout.getText().toString());
								us.setForn(forn.getText().toString());
								us.setETotal(etotal.getText().toString());
								us.setTipo(tipo.getText().toString());
								us.setImage(lt8.get(p3).getImage());
								db.postUp(us);
								lt8.clear();
								lt8.addAll(db.findP8());
								ad.notifyDataSetChanged();
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
							}
						});
					alert.setNegativeButton("Cancelar", null);
					alert.create();
					alert.show();
				}
			});
		li9.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					LayoutInflater lay = getLayoutInflater();
					View r = lay.inflate(R.layout.estoque_view_product, null);
					final TextInputEditText prod = r.findViewById(R.id.pessoa);
					final TextInputEditText quant = r.findViewById(R.id.quant);
					final TextInputEditText valor = r.findViewById(R.id.valor);
					final TextInputEditText etotal = r.findViewById(R.id.etotal);
					final TextInputEditText forn = r.findViewById(R.id.forn);
					final TextInputEditText dIn = r.findViewById(R.id.dIn);
					final TextInputEditText dout = r.findViewById(R.id.dOut);
					final TextInputEditText tipo = r.findViewById(R.id.tipo);

					prod.setText(lt9.get(p3).getProd());
					quant.setText(lt9.get(p3).getQuant());
					valor.setText(lt9.get(p3).getValor());
					etotal.setText(lt9.get(p3).getETotal());
					forn.setText(lt9.get(p3).getForn());
					dIn.setText(lt9.get(p3).getDataIn());
					dout.setText(lt9.get(p3).getDataOut());
					tipo.setText(lt9.get(p3).getTipo());

					AlertDialog.Builder alert = new AlertDialog.Builder(c);
					alert.setTitle("Visualizar Produto");
					alert.setView(r);
					alert.setPositiveButton("Salvar Alterações", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								SQLiteControl db = new SQLiteControl(c);
								util us = new util();
								us.setId(lt9.get(p3).getId());
								us.setTable("paginaNine");
								us.setProd(prod.getText().toString());
								us.setQuant(quant.getText().toString());
								us.setValor(valor.getText().toString());
								us.setDataIn(dIn.getText().toString());
								us.setDataOut(dout.getText().toString());
								us.setForn(forn.getText().toString());
								us.setETotal(etotal.getText().toString());
								us.setTipo(tipo.getText().toString());
								us.setImage(lt9.get(p3).getImage());
								db.postUp(us);
								lt9.clear();
								lt9.addAll(db.findP9());
								ad.notifyDataSetChanged();
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
							}
						});
					alert.setNegativeButton("Cancelar", null);
					alert.create();
					alert.show();
				}
			});
		li10.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					LayoutInflater lay = getLayoutInflater();
					View r = lay.inflate(R.layout.estoque_view_product, null);
					final TextInputEditText prod = r.findViewById(R.id.pessoa);
					final TextInputEditText quant = r.findViewById(R.id.quant);
					final TextInputEditText valor = r.findViewById(R.id.valor);
					final TextInputEditText etotal = r.findViewById(R.id.etotal);
					final TextInputEditText forn = r.findViewById(R.id.forn);
					final TextInputEditText dIn = r.findViewById(R.id.dIn);
					final TextInputEditText dout = r.findViewById(R.id.dOut);
					final TextInputEditText tipo = r.findViewById(R.id.tipo);

					prod.setText(lt10.get(p3).getProd());
					quant.setText(lt10.get(p3).getQuant());
					valor.setText(lt10.get(p3).getValor());
					etotal.setText(lt10.get(p3).getETotal());
					forn.setText(lt10.get(p3).getForn());
					dIn.setText(lt10.get(p3).getDataIn());
					dout.setText(lt10.get(p3).getDataOut());
					tipo.setText(lt10.get(p3).getTipo());

					AlertDialog.Builder alert = new AlertDialog.Builder(c);
					alert.setTitle("Visualizar Produto");
					alert.setView(r);
					alert.setPositiveButton("Salvar Alterações", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								SQLiteControl db = new SQLiteControl(c);
								util us = new util();
								us.setId(lt10.get(p3).getId());
								us.setTable("paginaTen");
								us.setProd(prod.getText().toString());
								us.setQuant(quant.getText().toString());
								us.setValor(valor.getText().toString());
								us.setDataIn(dIn.getText().toString());
								us.setDataOut(dout.getText().toString());
								us.setForn(forn.getText().toString());
								us.setETotal(etotal.getText().toString());
								us.setTipo(tipo.getText().toString());
								us.setImage(lt10.get(p3).getImage());
								db.postUp(us);
								lt10.clear();
								lt10.addAll(db.findP10());
								ad.notifyDataSetChanged();
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
							}
						});
					alert.setNegativeButton("Cancelar", null);
					alert.create();
					alert.show();
				}
			});
		show1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					l1.setVisibility(View.VISIBLE);
				}
		});
		hide1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					l1.setVisibility(View.GONE);
				}
			});
			
		show2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p2)
				{
					// TODO: Implement this method
					l2.setVisibility(View.VISIBLE);
				}
			});
		hide2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p2)
				{
					// TODO: Implement this method
					l2.setVisibility(View.GONE);
				}
			});
		show3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p3)
				{
					// TODO: Implement this method
					l3.setVisibility(View.VISIBLE);
				}
			});
		hide3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p3)
				{
					// TODO: Implement this method
					l3.setVisibility(View.GONE);
				}
			});
		show4.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p4)
				{
					// TODO: Implement this method
					l4.setVisibility(View.VISIBLE);
				}
			});
		hide4.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p4)
				{
					// TODO: Implement this method
					l4.setVisibility(View.GONE);
				}
			});
		show5.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p5)
				{
					// TODO: Implement this method
					l5.setVisibility(View.VISIBLE);
				}
			});
		hide5.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p5)
				{
					// TODO: Implement this method
					l5.setVisibility(View.GONE);
				}
			});
		show6.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p6)
				{
					// TODO: Implement this method
					l6.setVisibility(View.VISIBLE);
				}
			});
		hide6.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p6)
				{
					// TODO: Implement this method
					l6.setVisibility(View.GONE);
				}
			});
		show7.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p7)
				{
					// TODO: Implement this method
					l7.setVisibility(View.VISIBLE);
				}
			});
		hide7.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p7)
				{
					// TODO: Implement this method
					l7.setVisibility(View.GONE);
				}
			});
		show8.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p8)
				{
					// TODO: Implement this method
					l8.setVisibility(View.VISIBLE);
				}
			});
		hide8.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p8)
				{
					// TODO: Implement this method
					l8.setVisibility(View.GONE);
				}
			});
		show9.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p9)
				{
					// TODO: Implement this method
					l9.setVisibility(View.VISIBLE);
				}
			});
		hide9.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p9)
				{
					// TODO: Implement this method
					l9.setVisibility(View.GONE);
				}
			});
		show10.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p10)
				{
					// TODO: Implement this method
					l10.setVisibility(View.VISIBLE);
				}
			});
		hide10.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p10)
				{
					// TODO: Implement this method
					l10.setVisibility(View.GONE);
				}
			});
			
		try{
			util us = db.getCategory(1);
			if(!lt1.get(0).getProd().toString().equals("")){
				tp1.setText(us.getCategory());
				rel1.setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
			rel1.setVisibility(View.GONE);
		}
		try{
			util us = db.getCategory(2);
			if(!lt2.get(0).getProd().toString().equals("")){
				tp2.setText(us.getCategory());
				rel2.setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
			rel2.setVisibility(View.GONE);
		}
		try{
			util us = db.getCategory(3);
			if(!lt3.get(0).getProd().toString().equals("")){
				tp3.setText(us.getCategory());
				rel3.setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
			rel3.setVisibility(View.GONE);
		}
		try{
			util us = db.getCategory(4);
			if(!lt4.get(0).getProd().toString().equals("")){
				tp4.setText(us.getCategory());
				rel4.setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
			rel4.setVisibility(View.GONE);
		}
		try{
			util us = db.getCategory(5);
			if(!lt5.get(0).getProd().toString().equals("")){
				tp5.setText(us.getCategory());
				rel5.setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
			rel5.setVisibility(View.GONE);
		}
		try{
			util us = db.getCategory(6);
			if(!lt6.get(0).getProd().toString().equals("")){
				tp6.setText(us.getCategory());
				rel6.setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
			rel6.setVisibility(View.GONE);
		}
		try{
			util us = db.getCategory(7);
			if(!lt7.get(0).getProd().toString().equals("")){
				tp7.setText(us.getCategory());
				rel7.setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
			rel7.setVisibility(View.GONE);
		}
		try{
			util us = db.getCategory(8);
			if(!lt8.get(0).getProd().toString().equals("")){
				tp8.setText(us.getCategory());
				rel8.setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
			rel8.setVisibility(View.GONE);
		}
		try{
			util us = db.getCategory(9);
			if(!lt9.get(0).getProd().toString().equals("")){
				tp9.setText(us.getCategory());
				rel9.setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
			rel9.setVisibility(View.GONE);
		}
		try{
			util us = db.getCategory(10);
			if(!lt10.get(0).getProd().toString().equals("")){
				tp10.setText(us.getCategory());
				rel10.setVisibility(View.VISIBLE);
			}
		}catch(Exception e){
			rel10.setVisibility(View.GONE);
		}
	}
}
