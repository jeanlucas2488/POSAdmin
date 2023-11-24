package lucas.client.service.pos.admin.financeiro;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import java.util.*;
import lucas.client.service.pos.admin.*;
import lucas.client.service.pos.admin.etc.*;
import lucas.client.service.pos.admin.sqlite.*;
import android.widget.*;
import lucas.client.service.pos.admin.financeiro.adapter.*;
import android.widget.AdapterView.*;
import android.view.*;

public class Fechamento extends AppCompatActivity
{
	Context c = this;
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
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					// TODO: Implement this method
				}
		});
		
	}
	
}
