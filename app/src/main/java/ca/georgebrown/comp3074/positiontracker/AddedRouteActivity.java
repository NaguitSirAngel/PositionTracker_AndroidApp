package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

import ca.georgebrown.comp3074.positiontracker.model.Coordinates;
import ca.georgebrown.comp3074.positiontracker.model.Route;
import ca.georgebrown.comp3074.positiontracker.sql.DbHelper;

public class AddedRouteActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_route);

        final DbHelper dbHelper = new DbHelper(this);

        final EditText routeName = findViewById(R.id.txtRouteName);
        final EditText routeTag = findViewById(R.id.txtRouteTag);
        final Spinner rate = findViewById(R.id.spinner);

        TextView date = findViewById(R.id.txtDate);
        date.setText(getDate());

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
            public void onClick(final View view) {

                //toast that shows added route
                Toast t = Toast.makeText(view.getContext(),"Route has been added!",Toast.LENGTH_LONG);
                t.show();

                //example 1
                ArrayList<String> tags = new ArrayList<>();
                ArrayList<Coordinates> coord = new ArrayList<>();

                String rName = routeName.getText().toString();
                String rTag = routeTag.getText().toString();

                tags.add(rTag);

                int rRate = Integer.parseInt(rate.getSelectedItem().toString());
                Route route = new Route();
                route.setRouteName(rName);
                route.setRating(rRate);
                route.setTags(tags);
                route.setDate(getDate());

                dbHelper.addRoute(route);
                Toast.makeText(view.getContext(),"Successfully Added Route", Toast.LENGTH_LONG).show(); //bubble at the bottom

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setResult(RESULT_OK);
                        finish();
                    }
                }, 1500);
            }
        });
    }

    private String getDate(){
        Calendar calendar = Calendar.getInstance();
        return DateFormat.getDateInstance().format(calendar.getTime());
    }
}
