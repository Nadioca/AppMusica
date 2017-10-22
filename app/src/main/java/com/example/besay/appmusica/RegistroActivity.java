package com.example.besay.appmusica;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    Activity contexto;
    String sexoSeleccionado = "Hombre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        contexto = this;

        final EditText correo = (EditText) findViewById(R.id.registroCorreo);

        final EditText nombre = (EditText) findViewById(R.id.nombreRegistro);

        final EditText clave = (EditText) findViewById(R.id.claveRegistro);

        final CheckBox condiciones = (CheckBox) findViewById(R.id.checkBox);

        RadioGroup sexo = (RadioGroup) findViewById(R.id.radioSexo);
        sexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if( checkedId == R.id.sexoHombre ){
                    sexoSeleccionado = "Hombre";
                } else {
                    sexoSeleccionado = "Mujer";
                }
            }
        });

        Button botonRegistrar = (Button) findViewById(R.id.botonRegistrar);
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registroCorreo = correo.getText().toString();

                if(TextUtils.isEmpty(registroCorreo)) {
                    correo.setError("Campo obligatorio");
                    correo.requestFocus();
                    return;
                }
                else if(!registroCorreo.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    correo.setError("No es un email valido");
                    correo.requestFocus();
                    return;
                }

                String nombreRegistro = nombre.getText().toString();

                if (TextUtils.isEmpty(nombreRegistro)){
                    nombre.setError("campo obligatorio");
                    nombre.requestFocus();
                    return;
                }else if(!nombreRegistro.matches("[a-zA-Z ]+")) {
                    nombre.setError("Solo puede contener letras");
                    nombre.requestFocus();
                    return;
                }

                String claveRegistro = clave.getText().toString();
                int caracteres = claveRegistro.length();

                if (TextUtils.isEmpty(claveRegistro)){
                    clave.setError("campo obligatorio");
                    clave.requestFocus();
                    return;
                } else if (caracteres < 6) {
                    clave.setError("longitud mínima 6 caracteres");
                    clave.requestFocus();
                    return;
                }

                if(!condiciones.isChecked()){
                    Toast.makeText(contexto, "Debes aceptar las condiciones", Toast.LENGTH_SHORT).show();
                    return;

                }

                Usuario usuario = new Usuario(registroCorreo,nombreRegistro,claveRegistro,sexoSeleccionado);

                Intent intent = new Intent(contexto, PrincipalActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);

                //Nota: De momento, como no guarda en base de datos, voy a cargar siempre un usuario estandar y no el que registramos

            }
        });
    }

}
