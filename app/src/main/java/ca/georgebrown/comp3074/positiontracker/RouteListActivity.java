package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
            elements.add(MyContent.ITEMS.get(i).content);
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, MyContent.ITEMS);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent route = new Intent(view.getContext(),ViewRoute.class);
                startActivity(route);

            }
        });

    }
}
