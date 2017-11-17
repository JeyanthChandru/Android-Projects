package com.example.mycontactlist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

public class ContactMapActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener gpsListener;
    LocationListener networkListener;
    Location currentBestLocation;
    final int PERMISSION_REQUEST_LOCATION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_map);
        initListButton();
        initMapButton();
        initSettingsButton();
        initGetLocationButton();
    }

    @Override
    public void onPause(){
        super.onPause();
        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission( getBaseContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        try{
            locationManager.removeUpdates(gpsListener);
            locationManager.removeUpdates(networkListener);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initListButton(){
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactMapActivity.this,ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initGetLocationButton(){
        final Button locationButton = (Button) findViewById(R.id.buttonGetLocation);
        locationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(ContactMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            if (ActivityCompat.shouldShowRequestPermissionRationale(ContactMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                                Snackbar.make(findViewById(R.id.activity_contact_map), "MyContactList requires this permission to locate your contacts", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        ActivityCompat.requestPermissions(ContactMapActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                                    }
                                })
                                        .show();

                            } else {
                                ActivityCompat.requestPermissions(ContactMapActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                            }
                        } else {
                            startLocationUpdates();
                        }
                    } else {
                        startLocationUpdates();
                    }
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error requesting permission", Toast.LENGTH_LONG).show();
                }
                /*EditText editAddress = (EditText) findViewById(R.id.editAddress);
                EditText editCity = (EditText) findViewById(R.id.editCity);
                EditText editState = (EditText) findViewById(R.id.editState);
                EditText editZipCode = (EditText) findViewById(R.id.editZipcode);

                String address = editAddress.getText().toString() + ", " +
                        editCity.getText().toString() + ", " +
                        editState.getText().toString() + ", " +
                        editZipCode.getText().toString();
                List<Address> addresses = null;
                Geocoder geo = new Geocoder(ContactMapActivity.this);
                try{
                    addresses = geo.getFromLocationName(address,1);
                }
                catch (IOException e){
                    e.printStackTrace();
                }

                TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);

                txtLatitude.setText(String.valueOf(addresses.get(0).getLatitude()));
                txtLongitude.setText(String.valueOf(addresses.get(1).getLongitude()));*/

            }
        });
    }
    private void initMapButton(){
        ImageButton ibMap = (ImageButton) findViewById(R.id.imageButtonMap);
        ibMap.setBackgroundColor(Color.parseColor("#1A1A48"));
        ibMap.setEnabled(false);
    }
    private void initSettingsButton(){
        ImageButton ibSettings = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactMapActivity.this,ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void startLocationUpdates(){
        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission( getBaseContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        try{
            locationManager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);
            gpsListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(isBetterLocation(location)){
                        currentBestLocation = location;
                        TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                        TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);
                        TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy);
                        txtLatitude.setText(String.valueOf(location.getLatitude()));
                        txtLongitude.setText(String.valueOf(location.getLongitude()));
                        txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                    }
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}
                @Override
                public void onProviderEnabled(String provider) {}
                @Override
                public void onProviderDisabled(String provider) {}
            };
            networkListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(isBetterLocation(location)){
                        currentBestLocation = location;
                        TextView txtLatitude = (TextView) findViewById(R.id.textLatitude1);
                        TextView txtLongitude = (TextView) findViewById(R.id.textLongitude1);
                        TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy1);
                        txtLatitude.setText(String.valueOf(location.getLatitude()));
                        txtLongitude.setText(String.valueOf(location.getLongitude()));
                        txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                    }
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}
                @Override
                public void onProviderEnabled(String provider) {}
                @Override
                public void onProviderDisabled(String provider) {}
            };



            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,gpsListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,networkListener);

        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),"Error, Location not Available",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case PERMISSION_REQUEST_LOCATION:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startLocationUpdates();
                }
                else{
                    Toast.makeText(ContactMapActivity.this,"MyContactList will not locate your contacts.",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean isBetterLocation(Location location){
        boolean isBetter = false;
        if(currentBestLocation == null){
            isBetter = true;
        }
        else if (location.getAccuracy() <= currentBestLocation.getAccuracy()){
            isBetter = true;
        }
        else if (location.getTime() - currentBestLocation.getTime() > 5*60*1000){
            isBetter = true;
        }
        return isBetter;
    }

    private void findBestSensor(){
        double accGPS,accNet;
        TextView txtLatitude = (TextView) findViewById(R.id.textLatitude2);
        TextView txtLongitude = (TextView) findViewById(R.id.textLongitude2);
        TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy2);
        TextView txtAccGPS = (TextView) findViewById(R.id.textAccuracy);
        String ValGPS = txtAccGPS.getText().toString();
        if(ValGPS.equals("None"))
            accGPS = 100;
        else
            accGPS = Integer.parseInt(ValGPS);
        TextView txtAccNet = (TextView) findViewById(R.id.textAccuracy1);
        TextView txtSen = (TextView) findViewById(R.id.textSensor);
        String valNet = txtAccNet.getText().toString();
        if(valNet.equals("None"))
            accNet = 1000;
        else
            accNet = Integer.parseInt(valNet);
        if (accGPS < accNet){
            TextView txtLat = (TextView) findViewById(R.id.textLatitude);
            TextView txtLon = (TextView) findViewById(R.id.textLongitude);
            txtSen.setText(String.valueOf("GPS Sensor"));
            txtLatitude.setText(String.valueOf(txtLat.getText().toString()));
            txtLongitude.setText(String.valueOf(txtLon.getText().toString()));
            txtAccuracy.setText(String.valueOf(txtAccGPS.getText().toString()));
        }
        else{
            TextView txtLat = (TextView) findViewById(R.id.textLatitude1);
            TextView txtLon = (TextView) findViewById(R.id.textLongitude1);
            txtSen.setText(String.valueOf("Network Sensor"));
            txtLatitude.setText(String.valueOf(txtLat.getText().toString()));
            txtLongitude.setText(String.valueOf(txtLon.getText().toString()));
            txtAccuracy.setText(String.valueOf(txtAccNet.getText().toString()));
        }
    }
}