package com.example.suelliton.mcpd_android;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.maps.model.CircleOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Double latitude;
    Double longitude;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng here = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(here).title("Marker in here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(here));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMapType(2);
        mMap.setMinZoomPreference(18);
        CircleOptions c = new CircleOptions();
        c.center(here);
        c.fillColor(getColor(R.color.colorAccent));
        c.radius(2);
        mMap.addCircle(c);
    }
}
