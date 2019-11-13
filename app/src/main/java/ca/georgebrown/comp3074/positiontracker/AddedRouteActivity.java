package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddedRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_route);

        //View current Route button
        Button mapsBtn = findViewById(R.id.btnSave);
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:43.6711247,-79.4141207"));
                startActivity(i);
            }
        });


        //Add new Route button
        Button addRouteBtn = findViewById(R.id.btnAddRoute);
        addRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toast that shows added route
                Toast t = Toast.makeText(view.getContext(),"Route has been added!",Toast.LENGTH_LONG);
                t.show();
                finish();
            }
        });

    }
}
