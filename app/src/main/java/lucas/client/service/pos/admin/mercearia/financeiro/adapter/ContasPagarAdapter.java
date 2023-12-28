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

public class ContasPagarAdapter extends ArrayAdapter<util> {
    Context c;
    List<util> lt;

    public ContasPagarAdapter(Context c2, List<util> lt2){
        super(c2, R.layout.contas_adapter, lt2);
        this.c = c2;
        this.lt = lt2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View r = li.inflate(R.layout.contas_adapter, parent, false);
        final ImageView im = r.findViewById(R.id.im);
        final TextView tv = r.findViewById(R.id.tv);
        tv.setText(lt.get(position).getContasCodigo() + " --> " + lt.get(position).getClassificacao());
        im.setImageResource(R.drawable.pago);


        return r;
    }
}
