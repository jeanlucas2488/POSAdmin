package lucas.client.service.pos.admin.adapters;
import android.widget.*;
import lucas.client.service.pos.admin.etc.*;
import android.content.*;
import java.util.*;
import lucas.client.service.pos.admin.*;
import android.view.*;

public class userAdapter extends ArrayAdapter<util>
{
	Context c;
	List<util> lt;
	
	public userAdapter(Context c2, List<util> lt2){
		super(c2, R.layout.user_adapter, lt2);
		this.c = c2;
		this.lt = lt2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO: Implement this method
		LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
		View r = li.inflate(R.layout.user_adapter, parent, false);
		TextView tv = r.findViewById(R.id.tv);
		ImageView im = r.findViewById(R.id.im);
		tv.setText(lt.get(position).getUsuario());
		im.setImageResource(R.drawable.chave);
		return r;
	}
	
}
