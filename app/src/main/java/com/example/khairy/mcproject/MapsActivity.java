package com.example.khairy.mcproject;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText addressText;
    LocationManager locationManager;
    myLocationListener locationListener;
    Button getLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button b = (Button)findViewById(R.id.btn2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.Location = addressText.getText().toString();
                Intent i = new Intent(MapsActivity.this,MyCart.class);
                startActivity(i);
            }
        });
        addressText = (EditText)findViewById(R.id.edttxt);
        getLocation = (Button)findViewById(R.id.btn1);
        locationListener = new myLocationListener(getApplicationContext());
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,600,0,locationListener);
        }
        catch (SecurityException ex){
            Toast.makeText(getApplicationContext(),"You Are Not Allowed To Access Current Location",Toast.LENGTH_LONG).show();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMap.clear();
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc = null;
                try {
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                catch (SecurityException ex) {
                    Toast.makeText(getApplicationContext(),"You Didn't Allow To Access Current Location",Toast.LENGTH_LONG).show();
                }
                if (loc != null) {
                    LatLng myPosition = new LatLng(loc.getLatitude(),loc.getLongitude());
                    try {

                        addressList = geocoder.getFromLocation(myPosition.latitude,myPosition.longitude,1);
                        if (!addressList.isEmpty()) {
                            String address = "";
                            for (int i=0;i<addressList.get(0).getMaxAddressLineIndex();i++) {
                                address = addressList.get(0).getAddressLine(i)+", ";
                            }
                            mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location").snippet(address)).setDraggable(true);
                            addressText.setText(address);
                        }

                    }catch (IOException e) {

                        mMap.addMarker(new MarkerOptions().position(myPosition).title("My loction"));
                    }

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
                }
                else
                    Toast.makeText(getApplicationContext(),"Please Wait Until Your Location is Determined",Toast.LENGTH_LONG).show();
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {


            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addressList;

                try {

                    addressList = geocoder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if (!addressList.isEmpty()) {
                        String address = "";
                        for (int i=0;i<addressList.get(0).getMaxAddressLineIndex();i++) {
                            address = addressList.get(0).getAddressLine(i)+", ";
                        }
                        addressText.setText(address);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No Address For This Location",Toast.LENGTH_LONG).show();
                        addressText.getText().clear();
                    }
                }
                catch (IOException e){
                    Toast.makeText(getApplicationContext(),"Can't get the address, Check your network",Toast.LENGTH_LONG).show();
                }
            }
        });
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
