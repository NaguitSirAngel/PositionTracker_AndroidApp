package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import ca.georgebrown.comp3074.positiontracker.model.Route;

import java.util.List;
import ca.georgebrown.comp3074.positiontracker.sql.DbContract;
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


        String myTags = "";
        Cursor c = getTags(String.valueOf(route.getId()));
        List l = new ArrayList();
        while (c.moveToNext()){
            String w = c.getString(c.getColumnIndex(DbContract.TagEntity.COLUMN_TAG));
            l.add(w);
        }

        myTags = android.text.TextUtils.join(",", l);


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

    private Cursor getTags(String id){
        final DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DbContract.TagEntity._ID, DbContract.TagEntity.COLUMN_TAG, DbContract.TagEntity.COLUMN_ROUTEID};
        String selection = DbContract.TagEntity.COLUMN_ROUTEID+"=?"; //WordContract.WordEntity.COLUMN_NAME_WORD1+"=?";
        String[] selectionArgs = {id}; //{"test"}
        return db.query(
                DbContract.TagEntity.TABLE_NAME,  //table name
                projection, //colums we select
                selection, //columns for WHERE clause
                selectionArgs, //parameters for where clause
                null, //groupby
                null, //having
                null); //sorting
    }

}
