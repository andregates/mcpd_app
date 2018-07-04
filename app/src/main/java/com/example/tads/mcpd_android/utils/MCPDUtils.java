package com.example.tads.mcpd_android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.tads.mcpd_android.activity.GPSTracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MCPDUtils {

    /**
     * Retorna imagem retornada
     * @return
     */
    public static String getDataAtual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy - HH:mm");
        // OU
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");

        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        String data_completa = dateFormat.format(data_atual);

        String hora_atual = dateFormat_hora.format(data_atual);

        Log.i("data_completa", data_completa);
        Log.i("data_atual", data_atual.toString());
        Log.i("hora_atual", hora_atual); // Esse é o que você quer

        return data_completa;
    }

    public static double getLatitude(Context context) {
        GPSTracker gps = new GPSTracker(context);
        return gps.getLatitude();
    }

    public static double getLongitude(Context context) {
        GPSTracker gps = new GPSTracker(context);
        Log.i("latitude", gps.getLatitude() + "");
        return gps.getLongitude();
    }

    /**
     * Decode string to Image.
     * @param imageFb
     * @return
     */
    public static Bitmap decodeFromFirebaseBase64(String imageFb){
        byte[] decodedByteArray = android.util.Base64.decode(imageFb, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

    }


}
