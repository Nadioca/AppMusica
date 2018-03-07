package com.example.besay.appmusica.pojos;

import android.graphics.Bitmap;

import com.example.besay.appmusica.constantes.Constantes;

import java.util.Date;

/**
 * Created by Besay on 21/10/2017.
 */

public class Lista_Musica {

    private int id_lista;
    private String titulo;
    private String creador;
    private Date fecha_creado;


    public Lista_Musica() {
        this.id_lista = Constantes.SIN_VALOR_INT;
        this.titulo = Constantes.SIN_VALOR_STRING;
        this.setCreador(Constantes.SIN_VALOR_STRING);
        this.setFecha_creado(null);

    }

    public Lista_Musica(int ID, String nombre, String abreviatura, Date fecha_creado) {
        this.id_lista = ID;
        this.titulo = nombre;
        this.setCreador(abreviatura);
        this.setFecha_creado(fecha_creado);
    }

    public int getId_musica() {
        return id_lista;
    }

    public void setId_musica(int id_musica) {
        this.id_lista = id_musica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public Date getFecha_creado() {
        return fecha_creado;
    }

    public void setFecha_creado(Date fecha_creado) {
        this.fecha_creado = fecha_creado;
    }
}
