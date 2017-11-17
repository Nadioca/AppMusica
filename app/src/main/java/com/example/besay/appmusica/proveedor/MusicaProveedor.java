package com.example.besay.appmusica.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.example.besay.appmusica.constantes.Utilidades;
import com.example.besay.appmusica.pojos.Musica;

import java.io.IOException;


public class MusicaProveedor {
    static public void insert(ContentResolver resolvedor, Musica ciclo, Context contexto){
        Uri uri = Contrato.Musica.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Musica.NOMBRE, ciclo.getNombre());
        values.put(Contrato.Musica.COMPA, ciclo.getAbreviatura());

        Uri returnUri = resolvedor.insert(uri, values);

        if(ciclo.getImagen()!=null){
            try {
                Utilidades.storeImage(ciclo.getImagen(), contexto, "img_" + returnUri.getLastPathSegment() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Hubo un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    static public void delete(ContentResolver resolver, int cicloId){
        Uri uri = Uri.parse(Contrato.Musica.CONTENT_URI + "/" + cicloId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Musica ciclo, Context contexto){
        Uri uri = Uri.parse(Contrato.Musica.CONTENT_URI + "/" + ciclo.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Musica.NOMBRE, ciclo.getNombre());
        values.put(Contrato.Musica.COMPA, ciclo.getAbreviatura());

        resolver.update(uri, values, null, null);

        if(ciclo.getImagen()!=null){
            try {
                Utilidades.storeImage(ciclo.getImagen(), contexto, "img_" + ciclo.getID() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Hubo un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    static public Musica read(ContentResolver resolver, int cicloId) {
        Uri uri = Uri.parse(Contrato.Musica.CONTENT_URI + "/" + cicloId);

        String[] projection = {Contrato.Musica._ID,
                Contrato.Musica.NOMBRE,
                Contrato.Musica.COMPA};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Musica ciclo = new Musica();
            ciclo.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Musica._ID)));
            ciclo.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Musica.NOMBRE)));
            ciclo.setAbreviatura(cursor.getString(cursor.getColumnIndex(Contrato.Musica.COMPA)));
            return ciclo;
        }

        return null;

    }
}
