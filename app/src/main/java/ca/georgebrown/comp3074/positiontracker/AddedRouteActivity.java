package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ca.georgebrown.comp3074.positiontracker.model.Coordinate;
import ca.georgebrown.comp3074.positiontracker.model.Route;
import ca.georgebrown.comp3074.positiontracker.sql.DbHelper;

public class AddedRouteActivity extends AppCompatActivity {

    private ArrayList<Coordinate> coordinates = null;
    private DbHelper dbHelper;
    private EditText routeName, routeTag;
    private Spinner rate;
    private TextView date;
    private Button mapsBtn, addRouteBtn;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_route);

        dbHelper = new DbHelper(this);

        //retrieving route coordinates
        coordinates = (ArrayList<Coordinate>)getIntent().getExtras().getSerializable("coordinates");

        routeName = findViewById(R.id.txtRouteName);
        routeTag = findViewById(R.id.txtRouteTag);
        rate = findViewById(R.id.spinner);

        date = findViewById(R.id.txtDate);
        date.setText(getDate());

        //View current Route button
        mapsBtn = findViewById(R.id.btnSave);
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddedRouteActivity.this, MapsActivity.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });

        //Add new Route button
        addRouteBtn = findViewById(R.id.btnAddRoute);
        addRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String rName = routeName.getText().toString();
                String rTag = routeTag.getText().toString();

                String[] tags = rTag.split(",");

                int rRate = Integer.parseInt(rate.getSelectedItem().toString());
                Route route = new Route();
                route.setRouteName(rName);
                route.setRating(rRate);
                route.setDate(getDate());

                id = dbHelper.addRoute(route);

                //add coordinates to the database
                for(Coordinate coordinate : coordinates){
                    dbHelper.addCoordinate(coordinate, dbHelper.getRoute(id));
                }

                if(tags.length>1){
                    for(String str : tags){
                    dbHelper.addTag(str,id);
                    }
                }else {
                    dbHelper.addTag(rTag, id);
                }

                Toast.makeText(view.getContext(),"Successfully Added Route", Toast.LENGTH_LONG).show();

                //Disables button to ensure no duplicate entry is added
                mapsBtn.setClickable(false);
                addRouteBtn.setClickable(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setResult(RESULT_OK);
                        finish();
                    }
                }, 2000);
            }
        });
    }

    private String getDate(){
        Calendar calendar = Calendar.getInstance();
        return DateFormat.getDateInstance().format(calendar.getTime());
    }

}
