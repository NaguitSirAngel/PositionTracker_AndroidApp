package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrackingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        Button btnStop = findViewById(R.id.btnStop);
        Button btnCancel = findViewById(R.id.btnCancel);

        //click for Stop Route recording and forward to AddedRouteActivity View
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(view.getContext(), AddedRouteActivity.class);
                startActivity(i);
            }
        });

        //when Cancel is pressed it goes back to Home view
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }
}
