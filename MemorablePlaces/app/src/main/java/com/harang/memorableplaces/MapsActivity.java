package com.harang.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.harang.memorableplaces.databinding.ActivityMapsBinding;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

  private GoogleMap mMap;
  private ActivityMapsBinding binding;
  LocationManager locationManager;
  LocationListener locationListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityMapsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  @Override
  public void onMapLongClick(@NonNull LatLng latLng) {
    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
    String address = "";
    try {
      List<Address> addList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
      if(addList != null && addList.size() > 0) {
        if(addList.get(0).getSubThoroughfare() != null) {
          address += addList.get(0).getSubThoroughfare() + " ";
        }
        if(addList.get(0).getThoroughfare() != null) {
          address += addList.get(0).getThoroughfare();
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }

    mMap.addMarker(new MarkerOptions().position(latLng).title(address));
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.setOnMapLongClickListener(this);
    Intent intent = getIntent();
    if(intent.getIntExtra("placeNumber", 0) == 0) {
      locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
      locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
          centerMapOnLocation(location, "Your Location");
        }
      };

      if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        centerMapOnLocation(lastKnownLocation, "Your Location");
      } else {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
      }
    }
  }

  public void centerMapOnLocation(Location location, String title) {
    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
    mMap.clear();
    mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
      }
    }
  }


}