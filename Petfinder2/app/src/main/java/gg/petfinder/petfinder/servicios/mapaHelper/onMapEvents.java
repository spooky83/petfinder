package gg.petfinder.petfinder.servicios.mapaHelper;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import gg.petfinder.petfinder.R;

/**
 * Created by gscigliotto on 25/06/2016.
 */
public class onMapEvents implements OnMapReadyCallback {

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //  googleMap.addMarker(new MarkerOptions()
                //          .position(new LatLng(latLng.latitude, latLng.longitude))
                //          .title("Marker2"));
                Log.e("Mapa","Click");

            }
        });
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {

                googleMap.clear();
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latLng.latitude, latLng.longitude))
                        .title("Marker2"));




            }
        });


    }


 }
