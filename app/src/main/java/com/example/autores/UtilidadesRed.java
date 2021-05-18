package com.example.autores;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class UtilidadesRed {
    private static final String LOG_TAG
            =UtilidadesRed.class.getSimpleName();

    private static String URL_LIBRO
            ="https://www.googleapis.com/books/v1/volumes?";
    private static final String PARAM ="q";
    private static final String LIMIT ="maxResults";
    private static final String PRINT_TYPE ="printType";
    private static final String FILTER ="filter";
    private static final String ORDER ="orderBy";

    static String obtenerInformacionLibro(String cadenaConsulta, String filtro, String orden) throws IOException {
        HttpsURLConnection urlConnection = null;
        BufferedReader reader = null;
        String libroJSON =null;
        try{
            Uri construirUrl = Uri.parse(URL_LIBRO).buildUpon()
                    .appendQueryParameter(PARAM,cadenaConsulta)
                    .appendQueryParameter(LIMIT,"10")
                    .appendQueryParameter(PRINT_TYPE,"books")
                    .appendQueryParameter(FILTER, filtro)
                    .appendQueryParameter(ORDER, orden)
                    .build();
            Log.d(LOG_TAG,construirUrl.toString());
            URL peticionURL = new URL(construirUrl.toString());

            urlConnection = (HttpsURLConnection)peticionURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String linea;
           while ((linea =reader.readLine()) != null){
               builder.append(linea);
               builder.append("\n");
            }
           if(builder.length()==0){
               return  null;
           }
           libroJSON =builder.toString();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
        if(urlConnection !=null){
            urlConnection.disconnect();
        }
        if(reader !=null){
            reader.close();
        }
        }
        Log.d(LOG_TAG, libroJSON);
        return libroJSON;
    }
}
