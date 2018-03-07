package com.example.besay.appmusica.categorias;

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
import com.example.besay.appmusica.pojos.Musica;
import com.example.besay.appmusica.proveedor.CategoriasProveedor;
import com.example.besay.appmusica.proveedor.MusicaProveedor;


public class CategoriasInsercionActivity extends AppCompatActivity {
    EditText editTextCategoriasNombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ciclo_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextCategoriasNombre = (EditText) findViewById(R.id.editTextCategoriasNombre);

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
        editTextCategoriasNombre.setError(null);

        String nombre = String.valueOf(editTextCategoriasNombre.getText());


        if(TextUtils.isEmpty(nombre)){
            editTextCategoriasNombre.setError(getString(R.string.campo_requerido));
            editTextCategoriasNombre.requestFocus();
            return;
        }

        Categorias ciclo = new Categorias(Constantes.SIN_VALOR_INT, nombre);

        CategoriasProveedor.insert(getContentResolver(), ciclo, this);
        finish();
    }
}
