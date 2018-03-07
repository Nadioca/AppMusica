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
        public static final String ID_CATEGORIA = "Id_Categoria";
        public static final String COMPA = "Abreviatura";
    }

    public static final class Categorias implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Categorias");

        // Table column
        public static final String NOMBRE = "Nombre";

    }

    public static final class ListaMusicas implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Lista");

        // Table column
        public static final String ID_MUSICA = "Id_Musica";
        public static final String SUSCRIPTORES = "Suscriptores";

    }

}
