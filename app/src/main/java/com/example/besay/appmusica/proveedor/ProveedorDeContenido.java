package com.example.besay.appmusica.proveedor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;

public class ProveedorDeContenido extends ContentProvider {

    private static final int Musica_ONE_REG = 1;
    private static final int Musica_ALL_REGS = 2;
    private static final int Musica_Categorias_REG = 3;

    private static final int CATEGORIAS_ONE_REG = 10;
    private static final int CATEGORIAS_ALL_REGS = 20;

    private static final int LISTA_ONE_REG = 100;
    private static final int LISTA_ALL_REGS = 200;
    private static final int LISTA_MUSICAS_REG = 300;

    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "Musica.db";
    private static final int DATABASE_VERSION = 12;

    private static final String Musica_TABLE_NAME = "Musica";
    private static final String CATEGORIAS_TABLE_NAME = "Categorias";
    private static final String LISTA_TABLE_NAME = "Lista";

    // Indicates an invalid content URI
    public static final int INVALID_URI = -1;

    // Defines a helper object that matches content URIs to table-specific parameters
    private static final UriMatcher sUriMatcher;

    // Stores the MIME types served by this provider
    private static final SparseArray<String> sMimeTypes;

    /*
     * Initializes meta-data used by the content provider:
     * - UriMatcher that maps content URIs to codes
     * - MimeType array that returns the custom MIME type of a table
     */
    static {

        // Creates an object that associates content URIs with numeric codes
        sUriMatcher = new UriMatcher(0);

        /*
         * Sets up an array that maps content URIs to MIME types, via a mapping between the
         * URIs and an integer code. These are custom MIME types that apply to tables and rows
         * in this particular provider.
         */
        sMimeTypes = new SparseArray<String>();

        // Adds a URI "match" entry that maps picture URL content URIs to a numeric code

        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                Musica_TABLE_NAME,
                Musica_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                Musica_TABLE_NAME + "/#",
                Musica_ONE_REG);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                Musica_TABLE_NAME + "/CATEGORIA",
                Musica_Categorias_REG);

        //Uris para Categoria

        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                CATEGORIAS_TABLE_NAME,
                CATEGORIAS_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                CATEGORIAS_TABLE_NAME + "/#",
                CATEGORIAS_ONE_REG);

        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                LISTA_TABLE_NAME,
                LISTA_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                LISTA_TABLE_NAME + "/#",
                LISTA_ONE_REG);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                LISTA_TABLE_NAME + "/MUSICA",
                LISTA_MUSICAS_REG);

        // Specifies a custom MIME type for the picture URL table

        sMimeTypes.put(
                Musica_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + Musica_TABLE_NAME);
        sMimeTypes.put(
                Musica_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + Musica_TABLE_NAME);
        sMimeTypes.put(
                Musica_Categorias_REG,
                "vnd.android.cursor.dir/vnd."+
                        Contrato.AUTHORITY + "." + Musica_TABLE_NAME + "." + CATEGORIAS_TABLE_NAME );
//Categorias

        sMimeTypes.put(
                CATEGORIAS_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + CATEGORIAS_TABLE_NAME);
        sMimeTypes.put(
                CATEGORIAS_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + CATEGORIAS_TABLE_NAME);

        //Lista musicas

        sMimeTypes.put(
                LISTA_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + LISTA_TABLE_NAME);
        sMimeTypes.put(
                LISTA_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + LISTA_TABLE_NAME);
        sMimeTypes.put(
                LISTA_MUSICAS_REG,
                "vnd.android.cursor.dir/vnd."+
                        Contrato.AUTHORITY + "." + LISTA_TABLE_NAME + "." + Musica_TABLE_NAME );


    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);

            //if (!db.isReadOnly()){
            //Habilitamos la integridad referencial
            db.execSQL("PRAGMA foreign_keys=ON;");
            //}
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table to store

            db.execSQL("Create table "
                    + CATEGORIAS_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + Contrato.Categorias.NOMBRE + " TEXT ); "
            );


            db.execSQL("Create table "
                    + Musica_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + Contrato.Musica.NOMBRE + " TEXT , "
                    + Contrato.Musica.COMPA + " TEXT , "
                    + Contrato.Musica.ID_CATEGORIA + " INTEGER , "
                    + "FOREIGN KEY (" + Contrato.Musica.ID_CATEGORIA + ") "
                    + "REFERENCES " + CATEGORIAS_TABLE_NAME + " (" + Contrato.Categorias._ID + ") ON DELETE CASCADE);"
            );

            db.execSQL("Create table "
                    + LISTA_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + Contrato.ListaMusicas.ID_MUSICA + " INTEGER , "
                    + Contrato.ListaMusicas.SUSCRIPTORES + " INTEGER , "
                    + "FOREIGN KEY (" + Contrato.ListaMusicas.ID_MUSICA + ") "
                    + "REFERENCES " + Musica_TABLE_NAME + " (" + Contrato.Musica._ID + ") ON DELETE CASCADE);"
            );

            inicializarDatos(db);

        }

        void inicializarDatos(SQLiteDatabase db){

            db.execSQL("INSERT INTO " + CATEGORIAS_TABLE_NAME + " (" +  Contrato.Categorias._ID
                    + "," + Contrato.Categorias.NOMBRE + ") "
                    + "VALUES (1,'Rock')");

            db.execSQL("INSERT INTO " + CATEGORIAS_TABLE_NAME + " (" +  Contrato.Categorias._ID + "," + Contrato.Categorias.NOMBRE + ") " +
                    "VALUES (2,'Dance')");

            db.execSQL("INSERT INTO " + Musica_TABLE_NAME + " (" +  Contrato.Musica._ID + "," + Contrato.Musica.NOMBRE + "," + Contrato.Musica.COMPA + "," + Contrato.Musica.ID_CATEGORIA + ") " +
                    "VALUES (1,'Elvis Presley','Elvis, La Colección Platino',1)");
            db.execSQL("INSERT INTO " + Musica_TABLE_NAME + " (" +  Contrato.Musica._ID + "," + Contrato.Musica.NOMBRE + "," + Contrato.Musica.COMPA + "," + Contrato.Musica.ID_CATEGORIA + ") " +
                    "VALUES (2,'Maluma','Corazón',2)");
            db.execSQL("INSERT INTO " + Musica_TABLE_NAME + " (" +  Contrato.Musica._ID + "," + Contrato.Musica.NOMBRE + "," + Contrato.Musica.COMPA + "," + Contrato.Musica.ID_CATEGORIA + ") " +
                    "VALUES (3,'Camila Cabello','Havana',2)");
            db.execSQL("INSERT INTO " + Musica_TABLE_NAME + " (" +  Contrato.Musica._ID + "," + Contrato.Musica.NOMBRE + "," + Contrato.Musica.COMPA + "," + Contrato.Musica.ID_CATEGORIA + ") " +
                    "VALUES (4,'U2','Best Friends',2)");

            db.execSQL("INSERT INTO " + LISTA_TABLE_NAME + " (" +  Contrato.ListaMusicas._ID
                    + "," + Contrato.ListaMusicas.ID_MUSICA
                    + "," + Contrato.ListaMusicas.SUSCRIPTORES + ") "
                    + "VALUES (1,1,120)");

            db.execSQL("INSERT INTO " + LISTA_TABLE_NAME + " (" +  Contrato.ListaMusicas._ID
                    + "," + Contrato.ListaMusicas.ID_MUSICA
                    + "," + Contrato.ListaMusicas.SUSCRIPTORES + ") "
                    + "VALUES (2,2,534)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + CATEGORIAS_TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + Musica_TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + LISTA_TABLE_NAME);

            onCreate(db);
        }

    }

    public ProveedorDeContenido() {
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
    }

    public void resetDatabase() {
        dbHelper.close();
        dbHelper = new DatabaseHelper(getContext());
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqlDB = dbHelper.getWritableDatabase();

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case Musica_ALL_REGS:
                table = Musica_TABLE_NAME;
                break;
            case CATEGORIAS_ALL_REGS:
                table = CATEGORIAS_TABLE_NAME;
                break;
            case LISTA_ALL_REGS:
                table = LISTA_TABLE_NAME;
                break;
        }

        long rowId = sqlDB.insert(table, "", values);

        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(
                    uri.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case Musica_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Musica._ID + " = "
                        + uri.getLastPathSegment();
                table = Musica_TABLE_NAME;
                break;
            case Musica_ALL_REGS:
                table = Musica_TABLE_NAME;
                break;
            case CATEGORIAS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Categorias._ID + " = "
                        + uri.getLastPathSegment();
                table = CATEGORIAS_TABLE_NAME;
                break;
            case CATEGORIAS_ALL_REGS:
                table = CATEGORIAS_TABLE_NAME;
                break;

            case LISTA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Categorias._ID + " = "
                        + uri.getLastPathSegment();
                table = LISTA_TABLE_NAME;
                break;
            case LISTA_ALL_REGS:
                table = LISTA_TABLE_NAME;
                break;
        }
        int rows = sqlDB.delete(table, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        throw new SQLException("Failed to delete row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = null;

        switch (sUriMatcher.match(uri)) {
            case Musica_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Musica._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(Musica_TABLE_NAME);
                break;
            case Musica_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Musica._ID + " ASC";
                qb.setTables(Musica_TABLE_NAME);
                break;
            case Musica_Categorias_REG:
                String sql = "SELECT " +
                        Contrato.Musica._ID + "," +
                        Contrato.Musica.NOMBRE + "," +
                        Contrato.Musica.COMPA + "," +
                        Contrato.Categorias.NOMBRE + " AS NOMBRE_CATEGORIA" + " FROM " +
                        Musica_TABLE_NAME +
                        " INNER JOIN " + CATEGORIAS_TABLE_NAME +
                        " ON " + Contrato.Musica.ID_CATEGORIA + " = " + Contrato.Categorias._ID;
                Cursor c;
                c = db.rawQuery(sql, null);
                return c;
            case CATEGORIAS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Categorias._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(CATEGORIAS_TABLE_NAME);
                break;
            case CATEGORIAS_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Categorias._ID + " ASC";
                qb.setTables(CATEGORIAS_TABLE_NAME);
                break;

            case LISTA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.ListaMusicas._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(LISTA_TABLE_NAME);
                break;
            case LISTA_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.ListaMusicas._ID + " ASC";
                qb.setTables(LISTA_TABLE_NAME);
                break;
            case LISTA_MUSICAS_REG:
                String sql2 = "SELECT " +
                        Contrato.ListaMusicas._ID + "," +
                        Contrato.ListaMusicas.SUSCRIPTORES + "," +
                        Contrato.Musica.NOMBRE + " AS NOMBRE_MUSICA" + " FROM " +
                        LISTA_TABLE_NAME +
                        " INNER JOIN " + Musica_TABLE_NAME +
                        " ON " + Contrato.ListaMusicas.ID_MUSICA + " = " + Contrato.Musica._ID;
                Cursor d;
                d = db.rawQuery(sql2, null);
                return d;
        }

        Cursor c;
        c = qb.query(db, projection, selection, selectionArgs, null, null,
                        sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case Musica_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Musica._ID + " = "
                        + uri.getLastPathSegment();
                table = Musica_TABLE_NAME;
                break;
            case Musica_ALL_REGS:
                table = Musica_TABLE_NAME;
                break;
            case CATEGORIAS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Categorias._ID + " = "
                        + uri.getLastPathSegment();
                table = CATEGORIAS_TABLE_NAME;
                break;
            case CATEGORIAS_ALL_REGS:
                table = CATEGORIAS_TABLE_NAME;
                break;

            case LISTA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Musica._ID + " = "
                        + uri.getLastPathSegment();
                table = LISTA_TABLE_NAME;
                break;
            case LISTA_ALL_REGS:
                table = LISTA_TABLE_NAME;
                break;
        }

        int rows = sqlDB.update(table, values, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

            return rows;
        }
        throw new SQLException("Failed to update row into " + uri);
    }
}
