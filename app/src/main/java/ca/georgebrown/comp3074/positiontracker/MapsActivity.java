package ca.georgebrown.comp3074.positiontracker;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import ca.georgebrown.comp3074.positiontracker.model.Coordinate;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private ArrayList<Coordinate> coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // retrieve coordinates
        coordinates = (ArrayList<Coordinate>)getIntent().getExtras().getSerializable("coordinates");
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
        GoogleMap mMap = googleMap;

        //setting up the starting position and ending position for marker
        LatLng startPoint = new LatLng(coordinates.get(0).getLatitude(), coordinates.get(0).getLongitude());
        LatLng endPoint = new LatLng(coordinates.get(coordinates.size()-1).getLatitude(), coordinates.get(coordinates.size()-1).getLongitude());

        //Adding coordinates to a line
        PolylineOptions line = new PolylineOptions();
        for (Coordinate c : coordinates){
            line.add(new LatLng(c.getLatitude(), c.getLongitude()));
        }
        line.width(5).color(Color.BLUE);
        mMap.addPolyline(line);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint,19));

        //Starting position marker
        Marker mStart = mMap.addMarker(new MarkerOptions()
                .position(startPoint).title("Start")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mStart.setTag(0);

        //Ending position marker
        Marker mEnd = mMap.addMarker(new MarkerOptions()
                .position(endPoint).title("End")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mEnd.setTag(0);
    }
}
