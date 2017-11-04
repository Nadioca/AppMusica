package com.example.besay.appmusica.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.besay.appmusica.pojos.Musica;


public class MusicaProveedor {
    static public void insert(ContentResolver resolvedor, Musica ciclo){
        Uri uri = Contrato.Musica.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Musica.NOMBRE, ciclo.getNombre());
        values.put(Contrato.Musica.COMPA, ciclo.getAbreviatura());

        resolvedor.insert(uri, values);
    }

    static public void delete(ContentResolver resolver, int cicloId){
        Uri uri = Uri.parse(Contrato.Musica.CONTENT_URI + "/" + cicloId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Musica ciclo){
        Uri uri = Uri.parse(Contrato.Musica.CONTENT_URI + "/" + ciclo.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Musica.NOMBRE, ciclo.getNombre());
        values.put(Contrato.Musica.COMPA, ciclo.getAbreviatura());

        resolver.update(uri, values, null, null);
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
