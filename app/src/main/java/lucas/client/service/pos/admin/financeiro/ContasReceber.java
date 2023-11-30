package lucas.client.service.pos.admin.financeiro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

import lucas.client.service.pos.admin.R;
import lucas.client.service.pos.admin.etc.util;
import lucas.client.service.pos.admin.sqlite.SQLiteControl;

public class ContasReceber extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contas_receber);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        SQLiteControl db = new SQLiteControl(c);
    }
}