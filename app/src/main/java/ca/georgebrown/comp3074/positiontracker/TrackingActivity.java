package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import ca.georgebrown.comp3074.positiontracker.model.Coordinate;

public class TrackingActivity extends AppCompatActivity {

    private FusedLocationProviderClient locationClient;
    private LocationCallback locationCallback;
    private ImageView loading;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        final ArrayList<Coordinate> coordinates = new ArrayList<>();

        Button stopBtn = findViewById(R.id.btnStop);
        Button cancelBtn = findViewById(R.id.btnCancel);

        // TRACKING ANIMATION
        loading = findViewById(R.id.imageViewLoading);
        loading.setBackgroundResource(R.drawable.animation_loading);
        animationDrawable = (AnimationDrawable)loading.getBackground();
        animationDrawable.start();


        locationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                Coordinate coordinate = new Coordinate();
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
                coordinates.remove(0);
                finish();
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
        lr.setInterval(3000);
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
