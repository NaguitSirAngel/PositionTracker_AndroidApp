package ca.georgebrown.comp3074.positiontracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;
import ca.georgebrown.comp3074.positiontracker.model.Route;
import ca.georgebrown.comp3074.positiontracker.sql.DbHelper;

public class RouteListActivity extends AppCompatActivity {

    public static int REQUEST_CODE = 1;
    public static DbHelper dbHelper = null;
    public static ArrayList<Route> routes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        dbHelper = new DbHelper(this);
        routes = dbHelper.getAllRoutes();

        final ListView list = findViewById(R.id.list);
        final RouteArrayAdapter adapter = new RouteArrayAdapter(this, R.layout.route_layout, routes);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Route route = ((Route) adapterView.getItemAtPosition(i));
                Intent intent = new Intent(view.getContext(), ViewRouteActivity.class);
                intent.putExtra("route", route);
                startActivityForResult(intent, REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode== Activity.RESULT_OK){
                routes = dbHelper.getAllRoutes();
                updateList();
            }
        }
    }

    public void updateList(){
//        FragmentManager manager = getSupportFragmentManager();
//        ItemFragment fragment =
//                (ItemFragment) manager
//                        .findFragmentById(R.id.fragment);
//        fragment.updateView();
        ListView list = findViewById(R.id.list);
        RouteArrayAdapter adapter = new RouteArrayAdapter(this, R.layout.route_layout, routes);
        list.setAdapter(adapter);

    }
}
