package com.example.besay.appmusica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent intent = this.getIntent();

        Usuario usuario = intent.getParcelableExtra("usuario");

        TextView Username = (TextView) findViewById(R.id.userName);
        Username.setText(usuario.getCorreo());

        TextView nombreDeUsuario = (TextView) findViewById(R.id.nombrePerfil);
        nombreDeUsuario.setText(usuario.getNombre());

        TextView sexoDeUsuario = (TextView) findViewById(R.id.edadPerfil);
        sexoDeUsuario.setText("Sexo: "+usuario.getSexo() );

    }



}
