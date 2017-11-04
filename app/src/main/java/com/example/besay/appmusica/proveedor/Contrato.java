package com.example.besay.appmusica.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contrato {

    public static final String AUTHORITY = "com.example.besay.appmusica.proveedor.ProveedorDeContenido";

    public static final class Musica implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Musica");

        // Table column
        public static final String NOMBRE = "Nombre";
        public static final String COMPA = "Abreviatura";
    }
}