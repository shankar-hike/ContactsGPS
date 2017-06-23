package com.example.shankaryadav.contactsgps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main6Activity extends AppCompatActivity {

    private LocationManager lm;
    private LocationListener ll;
    private TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        t = (TextView) findViewById(R.id.tv);

        String s1 = "Latitude , Longitude";
        final String[] it1 = { s1};

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);


        ll = new LocationListener() {
            Location l;



            @Override
            public void onLocationChanged(Location location) {

                String s3 = String.valueOf(location.getLatitude());
                String s4 = String.valueOf(location.getLongitude());
                t.append("\n" + s3 + " , " + s4 + "\n");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET

                }, 10);
                return;
            }
        }
        else {
            conf();
        }
    }
    public void onRequestPermissionResult(int requestCode, String[] permisiion , int[] grantResult) {
        switch(requestCode) {
            case 10:
                if(grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED)
                    conf();
                return;
        }
    }
    private void conf() {
                lm.requestLocationUpdates("gps", 5000, 0, ll);

    }

}
