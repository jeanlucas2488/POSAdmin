package lucas.client.service.pos.admin.financeiro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.etc.util;

public class ContasReceberAdapter extends ArrayAdapter<util> {

    Context c;
    List<util> lt;

    public ContasReceberAdapter(Context c2, List<util> lt2){
        super(c2, R.layout.contas_receber_adapter, lt2);
        this.c = c2;
        this.lt = lt2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View r = li.inflate(R.layout.contas_receber_adapter, parent, false);
        ImageView im = r.findViewById(R.id.im);
        LinearLayout la = r.findViewById(R.id.la);
        TextView tv = r.findViewById(R.id.tv);
        TextView tvD = r.findViewById(R.id.tvData);
        TextView tvS = r.findViewById(R.id.tvstatus);
        tv.setText(lt.get(position).getDocto() + " - " + lt.get(position).getCliente());
        tvS.setText("Status: " + lt.get(position).getCRStatus());
        tvD.setText(lt.get(position).getDataCadastro());
        im.setImageResource(R.drawable.contas);
        if (tvS.getText().toString().endsWith("Pago")){
            la.setVisibility(View.VISIBLE);
        } else{
            la.setVisibility(View.GONE);
        }
        return r;
    }
}
