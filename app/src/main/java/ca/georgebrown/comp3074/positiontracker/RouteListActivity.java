package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ca.georgebrown.comp3074.positiontracker.model.Route;
import ca.georgebrown.comp3074.positiontracker.sql.DbHelper;

public class RouteListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        final DbHelper dbHelper = new DbHelper(this);

        final ListView list = findViewById(R.id.list);
        final ArrayList<Route> routes = dbHelper.getAllRoutes();

        final RouteArrayAdapter adapter = new RouteArrayAdapter(this, R.layout.route_layout, routes);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Route route = ((Route) adapterView.getItemAtPosition(i));
                Intent intent = new Intent(view.getContext(), ViewRouteActivity.class);
                intent.putExtra("route", route);
                startActivity(intent);
            }
        });


        //Home button
        Button homeBtn= findViewById(R.id.btnHome);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
