package lucas.client.service.pos.admin.financeiro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.etc.util;

public class ContasReceberAdapter extends ArrayAdapter<util> {

    Context c;
    List<util> lt;

    public ContasReceberAdapter(Context c2, List<util> lt2){
        super(c2, R.layout.boletoa_adapter, lt2);
        this.c = c2;
        this.lt = lt2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View r = li.inflate(R.layout.boletoa_adapter, parent, false);
        ImageView im = r.findViewById(R.id.im);
        TextView tv = r.findViewById(R.id.tv);
        TextView tvS = r.findViewById(R.id.tvstatus);
        tv.setText(lt.get(position).getDocto() + " - " + lt.get(position).getCliente());
        tvS.setText(lt.get(position).getStatus());
        im.setImageResource(R.drawable.contas);
        return r;
    }
}
