package com.example.besay.appmusica.ListaMusicas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.besay.appmusica.R;
import com.example.besay.appmusica.constantes.Constantes;
import com.example.besay.appmusica.pojos.Categorias;
import com.example.besay.appmusica.pojos.Lista_Musica;
import com.example.besay.appmusica.pojos.Musica;
import com.example.besay.appmusica.proveedor.CategoriasProveedor;
import com.example.besay.appmusica.proveedor.ListaMusicaProveedor;
import com.example.besay.appmusica.proveedor.MusicaProveedor;


public class ListaInsercionActivity extends AppCompatActivity {
    EditText editTextListaNombre;
    EditText editTextListaSuscriptores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ciclo_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextListaNombre = (EditText) findViewById(R.id.editTextListaNombre);
        editTextListaSuscriptores = (EditText) findViewById(R.id.editTextListaSuscriptores);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem menuItem = menu.add(Menu.NONE, Constantes.GUARDAR, Menu.NONE, "Guardar");
        menuItem.setIcon(R.drawable.ic_save_black_24dp);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case Constantes.GUARDAR:
                attemptGuardar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void attemptGuardar(){
        editTextListaNombre.setError(null);
        editTextListaSuscriptores.setError(null);

        String nombre = String.valueOf(editTextListaNombre.getText());
        String suscriptores = String.valueOf(editTextListaSuscriptores.getText());

        if(TextUtils.isEmpty(nombre)){
            editTextListaNombre.setError(getString(R.string.campo_requerido));
            editTextListaNombre.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(suscriptores)){
            editTextListaSuscriptores.setError(getString(R.string.campo_requerido));
            editTextListaSuscriptores.requestFocus();
            return;
        }

        Integer cat_id = Integer.parseInt(editTextListaNombre.getText().toString());
        Integer susc = Integer.parseInt(editTextListaSuscriptores.getText().toString());

        Musica categorias = MusicaProveedor.read(getContentResolver(), cat_id);

        Lista_Musica ciclo = new Lista_Musica(Constantes.SIN_VALOR_INT, categorias, susc);

        ListaMusicaProveedor.insert(getContentResolver(), ciclo, this);
        finish();
    }
}
