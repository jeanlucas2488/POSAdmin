package lucas.client.service.pos.admin.financeiro.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.sqlite.SQLiteControl;

public class boletoViewImageSource extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_boleto_image);

        Bundle b = getIntent().getExtras();
        byte[] test = b.getByteArray("image");
        ImageView im = findViewById(R.id.imview);
        byte[] res = test;
        Bitmap bt = BitmapFactory.decodeByteArray(res, 0, res.length);
        im.setImageBitmap(bt);
    }
}
