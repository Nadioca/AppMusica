package com.example.besay.appmusica.pojos;

import android.graphics.Bitmap;

import com.example.besay.appmusica.constantes.Constantes;

/**
 * Created by Besay on 21/10/2017.
 */

public class Musica {

    private int id_musica;
    private String titulo;
    private String autor;
    private String categoria;
    private Bitmap imagen;

    public Musica() {
        this.id_musica = Constantes.SIN_VALOR_INT;
        this.titulo = Constantes.SIN_VALOR_STRING;
        this.setAutor(Constantes.SIN_VALOR_STRING);
        this.setCategoria(Constantes.SIN_VALOR_STRING);
        this.setImagen(null);
    }

    public Musica(int ID, String nombre, String abreviatura, String categoria, Bitmap imagen) {
        this.id_musica = ID;
        this.titulo = nombre;
        this.setAutor(abreviatura);
        this.setCategoria(categoria);
        this.setImagen(imagen);
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId_musica() {
        return id_musica;
    }

    public void setId_musica(int id_musica) {
        this.id_musica = id_musica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

}
