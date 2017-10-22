package com.example.besay.appmusica;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Besay on 08/10/2017.
 */

public class Usuario implements Parcelable {

    String correo, nombre, clave, sexo;

    public Usuario(String correo, String nombre, String clave, String sexo){

        this.correo = correo;
        this.nombre = nombre;
        this.clave = clave;
        this.sexo = sexo;

    }

    public Usuario() {
        super();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    protected Usuario(Parcel in) {
        correo = in.readString();
        nombre = in.readString();
        clave = in.readString();
        sexo = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(correo);
        dest.writeString(nombre);
        dest.writeString(clave);
        dest.writeString(sexo);
    }



}