package com.example.besay.appmusica.pojos;

import com.example.besay.appmusica.constantes.Constantes;

/**
 * Created by Besay on 21/10/2017.
 */

public class Categorias {

    private int ID;
    private String titulo;

    public Categorias() {
        this.ID = Constantes.SIN_VALOR_INT;
        this.titulo = Constantes.SIN_VALOR_STRING;
    }

    public Categorias(int ID, String nombre) {
        this.ID = ID;
        this.titulo = nombre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
