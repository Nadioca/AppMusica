package com.example.besay.appmusica;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besay.appmusica.constantes.Utilidades;
import com.example.besay.appmusica.musica.CicloActivity;
import com.example.besay.appmusica.pojos.Musica;
import com.example.besay.appmusica.rest.MusicaRest;

import java.io.IOException;
import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Activity contexto;
    final int PETICION_SACAR_FOTO = 1;
    final int PETICION_GALERIA = 2;
    ImageView imageView;

    //temporal hasta que haya base de datos
    //final Usuario usuario = new Usuario("nadioca.gr@gmail.com", "Acoidan", "123456", "Hombre");
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contexto = this;

        Intent intent = this.getIntent();

        usuario = intent.getParcelableExtra("usuario");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navcabecera = navigationView.getHeaderView(0);
        TextView nombreUsuario = (TextView) navcabecera.findViewById(R.id.NombreUsuario);
        nombreUsuario.setText(usuario.getNombre());
        //nombreUsuario.setText("Acoidan");

        TextView correoUsuario = (TextView) navcabecera.findViewById(R.id.CorreoUsuario);
        correoUsuario.setText(usuario.getCorreo());
        //correoUsuario.setText("nadioca.gr@gmail.com");

        Button button = (Button) findViewById(R.id.buttonIrAMiMusica);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, CicloActivity.class);
                startActivity(intent);
            }
        });

        Button sacarfoto = (Button) findViewById(R.id.buttonAniadirFoto);
        sacarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sacarFoto();

            }
        });

        Button iragaleria = (Button) findViewById(R.id.botonIrGaleria);
        iragaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirDeGaleria();

            }
        });

        imageView = (ImageView) findViewById(R.id.view_mi_foto);

        Button botonDescarga = (Button) findViewById(R.id.buttonIrAMiMusicaWeb);
        botonDescarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargarDatos();
            }
        });

    }

    void descargarDatos(){
        new DescargarDatosTask().execute();
    }

    class DescargarDatosTask extends AsyncTask<Void,Void,ArrayList<Musica>> {

        @Override
        protected ArrayList<Musica> doInBackground(Void... voids) {
            ArrayList<Musica> musicas = MusicaRest.getAllMusica();
            Log.i("WS: ","llegó al doInBackground");
            return musicas;
        }

        @Override
        protected void onPostExecute(ArrayList<Musica> musicas) {
            super.onPostExecute(musicas);
            Log.i("WS: ","onPostExecute");
            for(Musica i : musicas){
                TextView textView = (TextView) findViewById(R.id.textoDescargado);
                String txt = textView.getText().toString();
                textView.setText(txt + "\n" + i.getTitulo() + " , " + i.getCompa());
            }
        }
    }

    public void sacarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PETICION_SACAR_FOTO);
    }

    public void elegirDeGaleria(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PETICION_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case PETICION_SACAR_FOTO:
                if(resultCode == RESULT_OK){
                    Bitmap foto = (Bitmap)data.getExtras().get("data");
                    imageView.setImageBitmap(foto);
                    try {
                        Utilidades.storeImage (foto, this, "imagen.jpg");
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),"Error: No se pudo guardar la foto", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //el usuario cancela
                    Toast.makeText(getApplicationContext(),"Cancelado", Toast.LENGTH_LONG).show();
                }
                break;

            case PETICION_GALERIA:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    imageView.setImageURI(uri);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){

            case R.id.help:
                Intent intent = new Intent(contexto, AyudaActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "No implementado todavía", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mi_perfil) {

            Intent intent = new Intent(contexto, PerfilActivity.class);
            intent.putExtra("usuario", usuario);

            startActivity(intent);

        } else if (id == R.id.nav_noticias_tab) {

            Intent intent = new Intent(contexto, MultiTabsActivity.class);

            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {


        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_cerrar) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Cerrar Sesión")
                    .setMessage("¿Estás seguro que quieres cerrar sesión?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(contexto, MainActivity.class);
                            startActivity(intent);
                            //finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
