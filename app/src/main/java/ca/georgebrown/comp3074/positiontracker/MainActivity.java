package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAbout= findViewById(R.id.btnAbout);
        Button btnNewRoute= findViewById(R.id.btnNewRoute);
        Button btnRoute = findViewById(R.id.btnRoute);

        //click New Route Button
        btnNewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TrackingActivity.class);
                startActivity(i);
            }
        });

        btnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,RouteListActivity.class);
                startActivity(i);

            }
        });


        //click About Us button
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(i);
            }
        });


    }
}
