package com.example.suelliton.mcpd_android;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import com.andexert.library.RippleView;

public class GeoPositionActivity extends AppCompatActivity {
    Context c = this;
    RippleView btn_maps;
    RippleView btn_atualiza;
    TextView latitude;
    TextView longitude;
    Double lat ;
    Double lng ;
    int cont = 0;
    GPSTracker  gps = new GPSTracker(GeoPositionActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_position);
        cont = 0;
        latitude = (TextView) findViewById(R.id.tx_latitude);
        longitude = (TextView) findViewById(R.id.tx_longitude);


        btn_maps = (RippleView) findViewById(R.id.btn_maps); btn_maps.setRippleDuration(200);
        btn_maps.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener(){
            @Override
            public void onComplete(RippleView rippleView) {
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", lat);
                bundle.putDouble("longitude", lng);
                Intent intent = new Intent(c,MapsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_atualiza = (RippleView) findViewById(R.id.btn_atualiza);btn_atualiza.setRippleDuration(200);
        btn_atualiza.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                getLocation();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setup();
        getLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }

    public void setup(){
        gps.getLocation();
        if(!gps.isGPSEnabled){
            if(cont == 2){
                finish();
            }else {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                Toast.makeText(c, "Ative o GPS do dispositivo", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                cont ++;
            }
        }else if(gps.getLatitude() == 0.0){
            /*if(cont == 2){
                finish();
            }else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                Toast.makeText(c, "Ative as permissões de local para o MCPD", Toast.LENGTH_SHORT).show();
                Toast.makeText(c, "Clique em MCPD -> Permissões -> Local", Toast.LENGTH_LONG).show();
                startActivity(intent);
                cont++;
            }*/
        }

        if(gps.getLatitude() == 0.0){
            btn_maps.setVisibility(View.GONE);
        }else{
            btn_maps.setVisibility(View.VISIBLE);
        }



    }


    public void getLocation(){
        gps.getLocation();
        if (gps.canGetLocation()) {
            lat = gps.getLatitude();
            lng = gps.getLongitude();
            latitude.setText("" + lat);
            longitude.setText("" + lng);
        }

    }


}
