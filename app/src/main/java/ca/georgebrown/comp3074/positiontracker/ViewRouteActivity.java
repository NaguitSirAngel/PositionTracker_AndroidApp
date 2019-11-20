package ca.georgebrown.comp3074.positiontracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.georgebrown.comp3074.positiontracker.model.Coordinate;
import ca.georgebrown.comp3074.positiontracker.model.Route;
import ca.georgebrown.comp3074.positiontracker.sql.DbHelper;


public class ViewRouteActivity extends AppCompatActivity {
    private static int REQUEST_CODE = 2;
    private DbHelper dbHelper;
    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_route);

        dbHelper = new DbHelper(this);
        route = (Route)getIntent().getExtras().getSerializable("route");

        //calls function to fill the textboxes with info from route
        populateTextbox(route);

        //View current Route button
        Button mapsBtn = findViewById(R.id.btnViewRoute);
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Coordinate> coordinates = dbHelper.getAllCoordinates(route.getId());
                Intent i = new Intent(ViewRouteActivity.this, MapsActivity.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });

        //Edit Route button
        Button editRouteBtn = findViewById(R.id.btnEditRoute);
        editRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewRouteActivity.this, EditRouteActivity.class);
                i.putExtra("route_id", route.getId());
                startActivityForResult(i,REQUEST_CODE);
            }
        });

        //Share Route button
        Button shareBtn = findViewById(R.id.btnShare);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
              //  sharingIntent.putExtra("route_id", route.getId());
                String shareBody = "I have crated a new route:"+ route.getRouteName();
                String shareSubject = "From here to there";
                sharingIntent.putExtra(sharingIntent.EXTRA_TEXT,shareBody);
                sharingIntent.putExtra(sharingIntent.EXTRA_SUBJECT,shareSubject);
                startActivity(Intent.createChooser(sharingIntent,"Share using"));
            }
        });

        //Deleting a Route and its coordinates
        Button deleteBtn = findViewById(R.id.btnSave);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteRoute(route);
                dbHelper.deleteCoordinates(route.getId());
                dbHelper.deleteTags(route);
                Toast.makeText(view.getContext(),"Route deleted!", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode== RESULT_OK){
                Route route = (Route)data.getSerializableExtra("route");
                populateTextbox(route);
            }
        }
    }

    public void populateTextbox(Route route){
        TextView name = findViewById(R.id.editName);
        TextView date = findViewById(R.id.editDate);
        TextView tags = findViewById(R.id.editTags);
        TextView rating = findViewById(R.id.editRating);

        DbHelper dbHelper = new DbHelper(this);

        String myTags = dbHelper.stringTag(route);

        name.setText(route.getRouteName());
        date.setText(route.getDate());
        tags.setText(myTags);
        rating.setText(String.valueOf(route.getRating()));
    }

    @Override
    public void onBackPressed() {
        // When the user hits the back button set the resultCode to RESULT_OK so list will be updated
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}