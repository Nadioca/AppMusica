package com.example.besay.appmusica.pojos;

import android.graphics.Bitmap;

import com.example.besay.appmusica.constantes.Constantes;

import java.util.Date;

/**
 * Created by Besay on 21/10/2017.
 */

public class Lista_Musica {

    private int id_lista;
    private Musica musica;
    private int suscriptores;


    public Lista_Musica() {
        this.id_lista = Constantes.SIN_VALOR_INT;
        this.musica = null;
        this.suscriptores = Constantes.SIN_VALOR_INT;
    }

    public Lista_Musica(int ID, Musica musica, int suscriptores) {
        this.id_lista = ID;
        this.musica = musica;
        this.suscriptores = suscriptores;
    }

    public int getId_lista() {
        return id_lista;
    }

    public void setId_lista(int id_lista) {
        this.id_lista = id_lista;
    }

    public Musica getMusica() {
        return musica;
    }

    public void setMusica(Musica musica) {
        this.musica = musica;
    }

    public int getSuscriptores() {
        return suscriptores;
    }

    public void setSuscriptores(int suscriptores) {
        this.suscriptores = suscriptores;
    }
}
