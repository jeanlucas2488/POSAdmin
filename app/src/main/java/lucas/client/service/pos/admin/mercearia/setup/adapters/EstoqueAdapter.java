package lucas.client.service.pos.admin.mercearia.setup.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.etc.util;

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
