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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import ca.georgebrown.comp3074.positiontracker.model.Coordinates;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
         LatLng latLng = new LatLng(51.432739, -0.523061);

        //Coordinates newCoor = new Coordinates();

        //Adding coordinates to a line
        PolylineOptions line = new PolylineOptions();
        line.add(new LatLng(51.432739, -0.523061));
        line.add(new LatLng(51.429805, -0.549850));
        line.add(new LatLng( 51.425900, -0.560324));
        line.add(new LatLng( 51.436070, -0.566330));


        line.width(5).color(Color.RED);
        mMap.addPolyline(line);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.432739, -0.523061),12));

        Marker mStart = mMap.addMarker(new MarkerOptions()
                .position(latLng).title("Start")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mStart.setTag(0);

        Marker mEnd = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 51.436070, -0.566330)).title("End")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mEnd.setTag(0);
//         for(Coordinates c : coordinates){
//            System.out.println(c.getLatitude() + "\t " + c.getLongitude());
//        }
    }
}
