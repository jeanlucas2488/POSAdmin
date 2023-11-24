package lucas.client.service.pos.admin.adapters;
import android.content.*;
import android.net.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import lucas.client.service.pos.admin.*;
import lucas.client.service.pos.admin.etc.*;
import java.io.*;
import android.os.*;
import lucas.client.service.pos.admin.sqlite.*;
import android.graphics.*;

public class adapter extends ArrayAdapter<util>
{
	Context c;
	List<util> lt;
	List<util> mStringFilterList = new ArrayList<util>();
	public adapter(Context c2, List<util> lt2){
		super(c2, R.layout.adapter, lt2);
		this.c = c2;
		this.lt = lt2;
		mStringFilterList.addAll(lt);
	}
	
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return lt.size();
	}
	@Override
	public long getItemId(int position)
	{
		// TODO: Implement this method
		return position;
	}

	@Override
	public util getItem(int position)
	{
		// TODO: Implement this method
		return lt.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO: Implement this method
		LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
		View r = li.inflate(R.layout.adapter, parent, false);
		ImageView im = r.findViewById(R.id.im);
		TextView tv = r.findViewById(R.id.tv);
		tv.setText(lt.get(position).getProd());
		/*File src = new File(Environment.getExternalStorageDirectory(), "pdvMain/data/lucas.client.service/.src/" + lt.get(position).getProd() + ".png");
		Uri url = Uri.fromFile(src);*/
		
		byte[] res = lt.get(position).getImage();
		Bitmap bt = BitmapFactory.decodeByteArray(res, 0, res.length);
		im.setImageBitmap(bt);
		return r;
	}
	public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        lt.clear();
        if (charText.length() == 0) {
            lt.addAll(mStringFilterList);
        } else {
            for (util wp : mStringFilterList) {
                if (wp.getProd().toLowerCase(Locale.getDefault()).contains(charText)) {
                    lt.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
