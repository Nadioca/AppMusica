package com.example.besay.appmusica;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Activity contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contexto = this;

        final Usuario Acoidan = new Usuario ("nadioca.gr@gmail.com", "Acoidan","123456", "Hombre");

        final EditText emailLogin = (EditText) findViewById(R.id.emailLogin);
        final EditText claveLogin = (EditText) findViewById(R.id.claveLogin);

        Button botonLogin = (Button) findViewById(R.id.buttonLogin);

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = emailLogin.getText().toString();

                if(TextUtils.isEmpty(correo)) {
                    emailLogin.setError("Campo obligatorio");
                    emailLogin.requestFocus();
                    return;
                } else if(!TextUtils.equals(correo, Acoidan.getCorreo())) {
                    emailLogin.setError("No existe el Usuario");
                    emailLogin.requestFocus();
                    return;
                }

                String clave = claveLogin.getText().toString();
                int caracteres = clave.length();

                if (TextUtils.isEmpty(clave)){
                    claveLogin.setError("campo obligatorio");
                    claveLogin.requestFocus();
                    return;
                } else if(!TextUtils.equals(clave, Acoidan.getClave())) {
                    claveLogin.setError("La clave no es válida");
                    claveLogin.requestFocus();
                    return;
                }

                //esto es temporal hasta que haya base de datos
                //final Usuario usuario = new Usuario ("nadioca.gr@gmail.com", "Acoidan","123456", "Hombre");
                Intent intent = new Intent(contexto, PrincipalActivity.class);
                intent.putExtra("usuario", Acoidan);
                startActivity(intent);

            }
        });
    }
}
