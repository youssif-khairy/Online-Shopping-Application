package com.example.khairy.mcproject;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by khairy on 13/12/2017.
 */

public class myLocationListener implements LocationListener {

    private Context activityContext;
    public myLocationListener(Context cont) {
        activityContext = cont;
    }
    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(activityContext,location.getLongitude() + " " + location.getLatitude(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

        Toast.makeText(activityContext,"GPS Enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String s) {

        Toast.makeText(activityContext,"GPS Disabled",Toast.LENGTH_LONG).show();
    }
}
