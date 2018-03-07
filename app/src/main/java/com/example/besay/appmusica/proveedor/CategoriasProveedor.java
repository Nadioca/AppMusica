package com.example.besay.appmusica.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.besay.appmusica.pojos.Categorias;


public class CategoriasProveedor {
    static public void insert(ContentResolver resolvedor, Categorias categoria, Context contexto){
        Uri uri = Contrato.Musica.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Categorias.NOMBRE, categoria.getTitulo());


        Uri returnUri = resolvedor.insert(uri, values);

    }

    static public void delete(ContentResolver resolver, int Categoria_ID){
        Uri uri = Uri.parse(Contrato.Categorias.CONTENT_URI + "/" + Categoria_ID);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Categorias categorias, Context contexto){
        Uri uri = Uri.parse(Contrato.Musica.CONTENT_URI + "/" + categorias.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Musica.NOMBRE, categorias.getTitulo());

        resolver.update(uri, values, null, null);

    }

    static public Categorias read(ContentResolver resolver, int Categoria_ID) {
        Uri uri = Uri.parse(Contrato.Categorias.CONTENT_URI + "/" + Categoria_ID);

        String[] projection = {Contrato.Categorias._ID,
                Contrato.Categorias.NOMBRE,
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Categorias ciclo = new Categorias();
            ciclo.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Categorias._ID)));
            ciclo.setTitulo(cursor.getString(cursor.getColumnIndex(Contrato.Categorias.NOMBRE)));
            return ciclo;
        }

        return null;

    }
}
