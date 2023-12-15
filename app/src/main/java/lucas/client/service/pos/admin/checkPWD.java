package lucas.client.service.pos.admin;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import lucas.client.service.pos.admin.etc.util;
import lucas.client.service.pos.admin.sqlite.SQLiteControl;

public class checkPWD extends AppCompatActivity {
    Context c = this;
    AlertDialog root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteControl db = new SQLiteControl(c);
        try{
            util us = db.getSenhaRet(1);
            if(!us.getUsuario().toString().equals("")){
                LayoutInflater li = getLayoutInflater();
                View r = li.inflate(R.layout.login, null);
                final TextInputEditText user = r.findViewById(R.id.user);
                final TextInputEditText pass = r.findViewById(R.id.pass);
                Button login = r.findViewById(R.id.login);
                Button cancelar = r.findViewById(R.id.cancelar);

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!user.getText().toString().equals("")){
                            if(user.getText().toString().equals(us.getUsuario())){
                                user.setTextColor(Color.GREEN);
                                if(!pass.getText().toString().equals("")){

                                    if(pass.getText().toString().equals(us.getSenha())){
                                        pass.setTextColor(Color.GREEN);
                                        Intent it = new Intent(c, MainActivity.class);
                                        startActivity(it);

                                    } else {
                                        pass.setTextColor(Color.RED);
                                        Toast.makeText(c, "Senha Incorreta!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    pass.setTextColor(Color.YELLOW);
                                    Toast.makeText(c, "Insira uma Senha!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                user.setTextColor(Color.RED);
                                Toast.makeText(c, "Usuario Incorreto!", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            user.setTextColor(Color.YELLOW);
                            Toast.makeText(c, "Insira um Usuário!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        root.dismiss();
                    }
                });
                AlertDialog.Builder al = new AlertDialog.Builder(c);
                al.setTitle("Fazer Login:");
                al.setView(r);

                al.setCancelable(false);
                root = al.create();
                root = al.show();
            }
        } catch (Exception e){
            Intent it = new Intent(c, MainActivity.class);
            startActivity(it);

        }
    }
}
