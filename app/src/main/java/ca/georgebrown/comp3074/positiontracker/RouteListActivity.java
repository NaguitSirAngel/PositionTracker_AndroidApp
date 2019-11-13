package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ca.georgebrown.comp3074.positiontracker.data.MyContent;

public class RouteListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        final ListView list = findViewById(R.id.list);
        final ArrayList<String> elements = new ArrayList<>();

        for(int i=0;i< MyContent.ITEMS.size();i++){
            elements.add(MyContent.ITEMS.get(i).name);
        }

        final RouteArrayAdapter adapter = new RouteArrayAdapter(this, R.layout.route_layout, MyContent.ITEMS);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final MyContent.Element item = ((MyContent.Element) adapterView.getItemAtPosition(i));
                Intent route = new Intent(view.getContext(), ViewRouteActivity.class);
                Bundle b = new Bundle();
                b.putString("date",item.date);
                b.putString("name",item.name);
                b.putString("tags",item.tags);
                b.putString("rating",item.rating);
                route.putExtras(b);
                startActivity(route);

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
