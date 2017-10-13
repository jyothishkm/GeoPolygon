package com.geofencedemo;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements GoogleMap.OnMapClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener , OnStreetViewPanoramaReadyCallback{

    private GeofencingClient mGeofencingClient;
    private Geofence mGeofenceList;
    private PendingIntent mGeofencePendingIntent;
    private EditText editText;
    private GoogleMap map;
    public Marker currentMarker;
    private ArrayList<LatLng> _tempLatLongPoints;
    private ArrayList<LatLng> _latLongPoints;
    private PolygonOptions  _polygonOptions;
    private  Polygon _polygon;
    private Location center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _tempLatLongPoints = new ArrayList<>();
        _latLongPoints = new ArrayList<>();
       /* Intent intent =new Intent(MainActivity.this, MediaPlayer.class);
        startActivity(intent);
        finish();*/
      //  StreetViewPanoramaFragment fragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama);
       // fragment.getStreetViewPanoramaAsync(this);

       // editText = (EditText) findViewById(R.id.show_hide);

       // showSoftKeyboard(editText);
        //hideSoftKeyboard(editText);

      /*  editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND)
                {
                    Toast.makeText(MainActivity.this, "true", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MainActivity.this, "false", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/

         MapFragment mapFragment = (MapFragment) getFragmentManager()
               .findFragmentById(R.id.map_fragment);
      mapFragment.getMapAsync(this);

       /* mGeofencingClient = LocationServices.getGeofencingClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(MainActivity.this, "denied", Toast.LENGTH_SHORT).show();
            return;
        }
        mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent()).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
*/

    }

private void hideSoftKeyboard(View view)
{
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
}
private void showSoftKeyboard(View view)
{
    if (view.requestFocus()) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }
}
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;
       /* Polygon polygon = map.addPolygon(new PolygonOptions()
                .add(new LatLng(0, 0), new LatLng(0, 5), new LatLng(3, 5), new LatLng(0, 0))
                .strokeColor(Color.RED)
                .fillColor(Color.parseColor("#51000000")).strokeWidth(2));

        Polygon polygon1 = map.addPolygon(new PolygonOptions()
                .add(new LatLng(29.0167,77.3833), new LatLng(18.9647, 72.8258),new LatLng(27.21, 75.33),new LatLng(29.0167,77.3833))
                .strokeColor(Color.RED)
                .fillColor(Color.parseColor("#51000000")).strokeWidth(2));


        Polygon polygon2 = map.addPolygon(new PolygonOptions()
                .add(new LatLng(-37.81319, 144.96298), new LatLng(-31.95285, 115.85734),new LatLng(-20.95285, 105.85734),new LatLng(-21.95285, 117.85734),new LatLng(-37.81319, 144.96298))
                .strokeColor(Color.RED)
                .fillColor(Color.parseColor("#51000000")).strokeWidth(2));*/
       /* GoogleMapOptions options = new GoogleMapOptions();
        options.compassEnabled(false);
        options.rotateGesturesEnabled(false);
        options.tiltGesturesEnabled(false);*/
        map.setMyLocationEnabled(true);
        LatLng sydney = new LatLng(-34, 151);
        final LatLng SYDNEY = new LatLng(-31.952854, 115.857342);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                .snippet("The most populous city in Australia.")
                .alpha(0.7f)
                .flat(true)
                .anchor(0.5f, 0.5f)
                .rotation(360.0f)
                .zIndex(1.0f)
                .position(sydney));
       // map.addMarker(new MarkerOptions().title("test").position(SYDNEY));
        map.setOnMarkerClickListener(this);
        map.setOnMapClickListener(this);
       /* googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(-34, 151)).title("Marker"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    @Override
    public void onMapClick(LatLng latLng) {
        _tempLatLongPoints.clear();
        map.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_marker)).title("polygon"));
        _latLongPoints.add(latLng);
        drawPoliyline(_latLongPoints);


    }

    private void drawPoliyline(ArrayList<LatLng> latLongPoints)
    {
        for (LatLng latLng : latLongPoints)
        {
           currentMarker = map.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_marker)));
        }
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.width(7.0f);
        polylineOptions.color(Color.BLUE);
        polylineOptions.addAll(latLongPoints);
        map.addPolyline(polylineOptions);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
         marker.showInfoWindow();

        if (_latLongPoints.size() > 0 && _latLongPoints.get(0).equals(marker.getPosition()))
        {
            countPolygonPoints();
        }
        return true;
    }

    private void countPolygonPoints() {
        Toast.makeText(MainActivity.this, "first point choose", Toast.LENGTH_SHORT).show();
        if (_latLongPoints.size() > 3)
        {
            _polygonOptions = new PolygonOptions();
            _polygonOptions.addAll(_latLongPoints);
            _polygonOptions.strokeColor(Color.BLUE);
            _polygonOptions.strokeWidth(7);
            _polygon = map.addPolygon(_polygonOptions);
            LatLngBounds.Builder boundaryBuilder = new LatLngBounds.Builder();
            for (LatLng latLng : _latLongPoints)
            {
                boundaryBuilder.include(latLng);
            }
            LatLngBounds boundary = boundaryBuilder.build();
            center = new Location("db");
            LatLng centerLatLng = boundary.getCenter();
            center.setLatitude(centerLatLng.latitude);
            center.setLongitude(centerLatLng.longitude);
            Float distance = getRadiusInKm(center, _latLongPoints);
            if ((distance * 1000) < 180 )
            {
                clearCreatedPolygon();
                showGeofenceDialog();
            }
            else
            {
                Toast.makeText(this, "Selected area is too high", Toast.LENGTH_SHORT).show();
               clearCreatedPolygon();
                showGeofenceDialog();

            }
        }
    }

    private void clearCreatedPolygon()
    {
        map.clear();
       _tempLatLongPoints.clear();
        if (_polygonOptions != null && _polygonOptions.getPoints() != null && _polygonOptions.getPoints().size() > 0)
        {
            _polygonOptions.getPoints().clear();
        }
        if (_polygon !=  null && _polygon.getPoints() != null && _polygon.getPoints().size() > 0)
        {
            _polygon.getPoints().clear();
        }
    }

    private void showGeofenceDialog()
    {
        map.setMyLocationEnabled(true);
        LatLng sydney = new LatLng(-34, 151);
        final LatLng SYDNEY = new LatLng(-31.952854, 115.857342);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                .snippet("The most populous city in Australia.")
                .alpha(0.7f)
                .flat(true)
                .anchor(0.5f, 0.5f)
                .rotation(360.0f)
                .zIndex(1.0f)
                .position(sydney));
        // map.addMarker(new MarkerOptions().title("test").position(SYDNEY));
        map.setOnMarkerClickListener(this);
        map.setOnMapClickListener(this);
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(_latLongPoints);
        polygonOptions.strokeColor(Color.RED);
        polygonOptions.strokeWidth(7);
        polygonOptions.fillColor(0x2200FFFF);
        Polygon polygon = map.addPolygon(polygonOptions);
        LatLng latLng = new LatLng(center.getLatitude(),center.getLongitude());
        map.addMarker(new MarkerOptions().position(latLng));

        _latLongPoints.clear();


    }

    private Float getRadiusInKm(Location center, ArrayList<LatLng> latLongPoints) {
        float radius = 0;
        for (LatLng latLng : latLongPoints)
        {
            Location location = new Location("db");
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);
            if (location.distanceTo(center) > radius)
            {
                radius = location.distanceTo(center);
            }
        }
        return radius / 1000;//convert to KM;
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

        streetViewPanorama.setPosition(new LatLng(-33.87365, 151.20689));
    }


    @Override
    public void onBackPressed() {

    }

}
