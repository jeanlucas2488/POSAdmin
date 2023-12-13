package lucas.client.service.pos.admin.adapters;

import android.content.*;
import android.widget.*;
import java.util.*;
import lucas.client.service.pos.admin.*;
import android.view.*;

public class financeiroAdapter extends ArrayAdapter<String>
{
	Context c;
	List<String> lt;
	
	public financeiroAdapter(Context c2, List<String> lt2){
		super(c2, R.layout.fin_adapter, lt2);
		this.c = c2;
		this.lt = lt2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO: Implement this method
		LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
		View r = li.inflate(R.layout.fin_adapter, parent, false);
		ImageView im = r.findViewById(R.id.im);
		TextView tv = r.findViewById(R.id.tv);
		tv.setText(lt.get(position));
		if(lt.get(position).startsWith("Fechamento")){
			im.setImageResource(R.drawable.rel_fechamento);
		}
		if(lt.get(position).startsWith("Contas a Pagar")){
			im.setImageResource(R.drawable.pagar);
		}
		if(lt.get(position).startsWith("Contas a Receber")){
			im.setImageResource(R.drawable.receber);
		}
		if(lt.get(position).startsWith("Relatórios")){
			im.setImageResource(R.drawable.relatorios);
		}
		return r;
	}
	
}
