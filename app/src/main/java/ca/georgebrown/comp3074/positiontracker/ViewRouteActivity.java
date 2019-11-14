package ca.georgebrown.comp3074.positiontracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ca.georgebrown.comp3074.positiontracker.model.Route;
import ca.georgebrown.comp3074.positiontracker.sql.DbHelper;

public class ViewRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_route);

        TextView name = findViewById(R.id.editName);
        TextView date = findViewById(R.id.editDate);
        TextView tags = findViewById(R.id.editTags);
        TextView rating = findViewById(R.id.editRating);

        final Route route = (Route)getIntent().getExtras().getSerializable("route");
        final DbHelper dbHelper = new DbHelper(this);

        String myTags = dbHelper.stringTag(route);

        name.setText(route.getRouteName());
        date.setText(route.getDate());
        tags.setText(myTags);
        rating.setText(String.valueOf(route.getRating()));


        //View current Route button
        Button mapsBtn = findViewById(R.id.btnViewRoute);
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:43.6711247,-79.4141207"));
                startActivity(i);
            }
        });

        //Edit Route button
        Button editRouteBtn = findViewById(R.id.btnEditRoute);
        editRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ViewRouteActivity.this, EditRouteActivity.class);
                i.putExtra("route", route);
                startActivity(i);
            }
        });

        //Share Route button
        Button shareBtn = findViewById(R.id.btnShare);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast t = Toast.makeText(view.getContext(),"Route has been successfully shared!",Toast.LENGTH_LONG);
                t.show();
            }
        });

        //Deleting a Route
        Button deleteBtn = findViewById(R.id.btnDelete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteRoute(route);
                Toast.makeText(view.getContext(),"Route deleted!", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            }
        });

    }

}
