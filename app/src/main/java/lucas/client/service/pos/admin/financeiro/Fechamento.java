package lucas.client.service.pos.admin.financeiro;

import android.content.*;
import android.net.Uri;
import android.os.*;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import lucas.client.service.pos.admin.*;
import lucas.client.service.pos.admin.etc.*;
import lucas.client.service.pos.admin.sqlite.*;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import lucas.client.service.pos.admin.financeiro.adapter.*;
import android.widget.AdapterView.*;
import android.view.*;

public class Fechamento extends AppCompatActivity
{
	Context c = this;
	String supR1, supR2, supR3, supR4, supR5, supR6, SupResult,
			sanR1, sanR2, sanR3, sanR4, sanR5, sanR6, sanResult,
	        soma1, soma2, soma3, soma4, soma5, soma6;
	List<util> lt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fechamento);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		SQLiteControl db = new SQLiteControl(c);
		lt = db.fechamento();
		ListView l = (ListView) findViewById(R.id.list);
		l.setAdapter(new fechamentoAdapter(this, lt));
		l.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{


					// TODO: Implement this method
					LayoutInflater li = getLayoutInflater();
					View r = li.inflate(R.layout.fechamento_viewer, null);
					final WebView wb =  r.findViewById(R.id.web);
					wb.setWebViewClient(new WebViewClient());
					String dateTime2;
					Calendar calendar2 = Calendar.getInstance();
					SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
					dateTime2 = simpleDateFormat2.format(calendar2.getTime()).toString();
					File root = new File(Environment.getExternalStorageDirectory(), "pdvMain/data/lucas.client.service/.fechamentos/fechamento_" + dateTime2 + ".html");
					Uri url = Uri.fromFile(root);
					wb.loadUrl(url.toString());
					AlertDialog.Builder al = new AlertDialog.Builder(c);
					al.setTitle("Fechamento" + lt.get(p3).getData());
					al.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
					al.setNegativeButton("Cancelar", null);
					al.create();
					al.show();
				}
		});
		
	}
	
}
