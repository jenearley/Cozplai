package edu.sjsu.jen.cozplai;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by jen0e on 12/8/17.
 */

public class ConventionLandscape extends Fragment implements OnMapReadyCallback, WeatherCallback{

    private static final int REQUEST_LOCATION = 1;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("Map", "onMapReady:");

        Geocoder geocoder = new Geocoder(this.getActivity());

        for(int i = 0; i < ConventionsTracker.conventions.size(); i++){
            String location = ConventionsTracker.conventions.get(i).getAddress();
            List<Address> list = null;
            try {
                list = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = list.get(0);
            String locality = address.getLocality();

            double lat = address.getLatitude();
            double lng = address.getLongitude();

            //test
            WeatherApi.getWeatherAtLocation(lat, lng, this);

            LatLng center = new LatLng(lat, lng);
            googleMap.addMarker(new MarkerOptions().position(center));

            CameraUpdate camUpdate = CameraUpdateFactory.newLatLngZoom(center, 15.0f);
            googleMap.moveCamera(camUpdate);

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conventions_landscape, container, false);
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onWeatherResult(String weather) {
        Log.d("WEATHER", "onWeatherResult: " + weather);
        WeatherApiResult result = WeatherApiResult.fromString(weather);
        Log.d("WEATHER", "result temp: " + result.main.getTemp());
    }
}
