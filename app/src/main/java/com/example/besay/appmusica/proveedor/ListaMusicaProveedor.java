package com.example.besay.appmusica.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.example.besay.appmusica.constantes.Utilidades;
import com.example.besay.appmusica.pojos.Categorias;
import com.example.besay.appmusica.pojos.Lista_Musica;
import com.example.besay.appmusica.pojos.Musica;

import java.io.IOException;


public class ListaMusicaProveedor {
    static public void insert(ContentResolver resolvedor, Lista_Musica ciclo, Context contexto){
        Uri uri = Contrato.ListaMusicas.CONTENT_URI;

        ContentValues values = new ContentValues();

        values.put(Contrato.ListaMusicas.ID_MUSICA, ciclo.getMusica().getId_musica());
        values.put(Contrato.ListaMusicas.SUSCRIPTORES, ciclo.getSuscriptores());

        Uri returnUri = resolvedor.insert(uri, values);

    }

    static public void delete(ContentResolver resolver, int cicloId){
        Uri uri = Uri.parse(Contrato.ListaMusicas.CONTENT_URI + "/" + cicloId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Lista_Musica ciclo, Context contexto){
        Uri uri = Uri.parse(Contrato.ListaMusicas.CONTENT_URI + "/" + ciclo.getId_lista());

        ContentValues values = new ContentValues();

        values.put(Contrato.ListaMusicas.ID_MUSICA, ciclo.getMusica().getId_musica());
        values.put(Contrato.ListaMusicas.SUSCRIPTORES, ciclo.getSuscriptores());

        resolver.update(uri, values, null, null);

    }

    static public Lista_Musica read(ContentResolver resolver, int cicloId) {
        Uri uri = Uri.parse(Contrato.ListaMusicas.CONTENT_URI + "/" + cicloId);

        String[] projection = {Contrato.ListaMusicas._ID,
                Contrato.ListaMusicas.ID_MUSICA,
                Contrato.ListaMusicas.SUSCRIPTORES
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Lista_Musica ciclo = new Lista_Musica();
            ciclo.setId_lista(cursor.getInt(cursor.getColumnIndex(Contrato.ListaMusicas._ID)));
            ciclo.setSuscriptores(cursor.getInt(cursor.getColumnIndex(Contrato.ListaMusicas.SUSCRIPTORES)));

            int categoriaID = cursor.getInt(cursor.getColumnIndex(Contrato.ListaMusicas.ID_MUSICA));
            Musica cate = MusicaProveedor.read(resolver, categoriaID);
            ciclo.setMusica(cate);

            return ciclo;
        }

        return null;

    }
}
