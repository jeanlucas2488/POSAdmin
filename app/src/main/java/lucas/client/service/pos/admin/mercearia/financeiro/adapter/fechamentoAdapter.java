package lucas.client.service.pos.admin.mercearia.financeiro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.etc.util;

public class fechamentoAdapter extends ArrayAdapter<util>
{
	Context c;
	List<util> lt;
	
	public fechamentoAdapter(Context c2, List<util> lt2){
		super(c2, R.layout.fechamento_adapter, lt2);
		this.c = c2;
		this.lt = lt2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO: Implement this method
		LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
		View r = li.inflate(R.layout.fechamento_adapter, parent, false);
		ImageView im = r.findViewById(R.id.im);
		TextView tv = r.findViewById(R.id.tv);
		tv.setText("Fechamento: " + lt.get(position).getData());
		im.setImageResource(R.drawable.fechamento_list);
		return r;
	}
	
}
