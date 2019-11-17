package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import ca.georgebrown.comp3074.positiontracker.model.Coordinates;

public class TrackingActivity extends AppCompatActivity {

    private FusedLocationProviderClient locationClient;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        final ArrayList<Coordinates> coordinates = new ArrayList<>();

        Button stopBtn = findViewById(R.id.btnStop);
        Button cancelBtn = findViewById(R.id.btnCancel);

        locationClient = LocationServices.getFusedLocationProviderClient(this);

        //getting the last known location
        locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    Coordinates coordinate = new Coordinates();
                    coordinate.setLatitude(location.getLatitude());
                    coordinate.setLongitude(location.getLongitude());
                    coordinate.setAccuracy(location.getAccuracy());
                    coordinate.setTimestamp(location.getTime());
                    coordinates.add(coordinate);
                }
            }
        });

        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                Coordinates coordinate = new Coordinates();
                coordinate.setLatitude(location.getLatitude());
                coordinate.setLongitude(location.getLongitude());
                coordinate.setAccuracy(location.getAccuracy());
                coordinate.setTimestamp(location.getTime());
                coordinates.add(coordinate);
            }
        };

        //click for Stop Route recording and forward to AddedRouteActivity View
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                stopTrackingLocation();
                Intent i = new Intent(view.getContext(), AddedRouteActivity.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });

        //click Home to cancel tracking
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void startTrackingLocation(){
        LocationRequest lr = new LocationRequest();
        lr.setInterval(5000);
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationClient.requestLocationUpdates(lr, locationCallback, null);
    }

    private void stopTrackingLocation(){
        locationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onPause(){
        stopTrackingLocation();
        super.onPause();
    }

    @Override
    protected  void onResume(){
        startTrackingLocation();
        super.onResume();
    }
}
