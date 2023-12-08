package lucas.client.service.pos.admin.financeiro.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class BoletosAdapter extends ArrayAdapter<util> {
    Context c;
    List<util> lt;

    public BoletosAdapter (Context c2, List<util> lt2){
        super(c2, R.layout.boletoa_adapter, lt2);
        this.c = c2;
        this.lt = lt2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View r = li.inflate(R.layout. boletoa_adapter, parent, false);
        ImageView im = r.findViewById(R.id.im);
        LinearLayout la = r.findViewById(R.id.la);
        ImageView imc = r.findViewById(R.id.imCheck);
        TextView tv = r.findViewById(R.id.tv);
        TextView tvS = r.findViewById(R.id.tvstatus);
        tv.setText(lt.get(position).getBdata() + " - " + lt.get(position).getBdescricao());
        tvS.setText("Status: " + lt.get(position).getBstatus());
        im.setImageResource(R.drawable.contas);
        if (tvS.getText().toString().endsWith("Pago")){
            la.setVisibility(View.VISIBLE);
        } else{
           la.setVisibility(View.GONE);
        }
        return r;
    }
}
