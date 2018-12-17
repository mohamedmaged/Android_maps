package com.example.mohamed.mps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    public static GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;
    Location location;
    private LatLng tres1;
    private LatLng tres2;
    private   Marker tressure1;
    private   Marker tressure2;
    CircleOptions circleOptions2,circleOptions1;
    public int score=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        tres1 = new LatLng(30.065147, 31.277741);
        tres2 = new LatLng(30.064590, 31.277667);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        2);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }

        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
        }
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        Log.d("locationnn", "onLocationChanged: " + "Lat : " + lat + "  Long : " + lng);
        Location x =new Location("");
        x.setLatitude(tres1.latitude);
        x.setLongitude(tres1.longitude);
        Location y=new Location("");
        y.setLatitude(tres2.latitude);
        y.setLongitude(tres2.longitude);

        double dist1=  location.distanceTo(x);
        if (dist1 < 20){
            Toast.makeText(this, "treasure 1 is found", Toast.LENGTH_SHORT).show();
            score+=5;
            tressure1.setVisible(false);
            circleOptions1.visible(false);

        }
        double dist2 = location.distanceTo(y);
        if (dist2 < 20){
            Toast.makeText(this, "treasure 2 is found", Toast.LENGTH_SHORT).show();
            tressure2.setVisible(false);
            circleOptions2.visible(false);
            score+=5;
        }
        //Toast.makeText(getApplicationContext(), "onLocationChanged: " + "Lat : " + lat + "  Long : " + lng, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng asu = new LatLng(30.064782, 31.277714);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(asu).title("Marker in ma5roba"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(asu));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
        //addMarkerForFence(asu);
        tressure1 = mMap.addMarker(new MarkerOptions().  position(tres1).icon(BitmapDescriptorFactory.fromResource(R.drawable.tres)).title("tressure"));
         tressure2 =mMap.addMarker( new MarkerOptions().position(tres2).icon(BitmapDescriptorFactory.fromResource(R.drawable.tres)).title("tressure"));
         circleOptions1 = new CircleOptions()
                .center( tres1 )
                .radius( 20)
                .fillColor(0x40ff0000)
                .strokeColor(Color.TRANSPARENT)
                .strokeWidth(2);
         circleOptions2 = new CircleOptions()
                .center( tres2 )
                .radius( 20)
                .fillColor(0x40ff0000)
                .strokeColor(Color.TRANSPARENT)
                .strokeWidth(2);
         mMap.addCircle(circleOptions1);
        mMap.addCircle(circleOptions2);
    }
    @SuppressLint("ValidFragment")
    public class FireMissilesDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Congratulations ! You have found all Tressusres and your score is 10")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton("replay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            score=0;
                            tressure1.setVisible(true);
                            tressure1.setVisible(true);
                            circleOptions1.visible(true);
                            circleOptions1.visible(true);
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

}
