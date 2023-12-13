package lucas.client.service.pos.admin.financeiro.adapter;

import android.content.*;
import android.widget.*;
import java.util.*;
import lucas.client.service.pos.admin.etc.*;
import lucas.client.service.pos.admin.*;
import android.view.*;

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
