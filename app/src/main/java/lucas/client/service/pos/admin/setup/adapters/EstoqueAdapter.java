package lucas.client.service.pos.admin.setup.adapters;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import lucas.client.service.pos.admin.*;
import lucas.client.service.pos.admin.etc.*;

public class EstoqueAdapter extends ArrayAdapter<util>
{
	Context c;
	List<util> lt;
	
	public EstoqueAdapter(Context c2, List<util> lt2){
		super(c2, R.layout.adapter, lt2);
		this.c = c2;
		this.lt = lt2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO: Implement this method
		LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
		View r = li.inflate(R.layout.adapter, parent, false);
		final ImageView im = r.findViewById(R.id.im);
		final TextView tv = r.findViewById(R.id.tv);
		byte[] res = lt.get(position).getImage();
		Bitmap bt = BitmapFactory.decodeByteArray(res, 0, res.length);
		im.setImageBitmap(bt);
		tv.setText(lt.get(position).getProd());
		return r;
	}
	
}
