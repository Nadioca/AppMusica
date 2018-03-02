package com.example.besay.appmusica.rest;

import android.util.Log;

import com.example.besay.appmusica.pojos.Musica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Besay on 30/11/2017.
 */

public class MusicaRest {

    final static String ruta = "http://192.168.1.44/app/public/api/musica";

    public static ArrayList<Musica> getAllMusica(){
        ArrayList<Musica> musicas = new ArrayList<>();

        try {
            URL url = new URL(ruta);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

            InputStream is = conexion.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder respuesta = new StringBuilder();
            String datos;

            while((datos = br.readLine() ) !=  null){

                respuesta.append(datos);

            }

                JSONArray jsonArray = new JSONArray(respuesta.toString());

            Log.i("WS: ", respuesta.toString());

                for( int i=0; i< jsonArray.length(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    musicas.add(new Musica(obj.getInt("id_musica"),obj.getString("titulo"), obj.getString("autor"), obj.getString("categoria"),null));
                }

            Log.i("WS: ", "Funciona");

            return  musicas;


        } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return  null;
    }
}
