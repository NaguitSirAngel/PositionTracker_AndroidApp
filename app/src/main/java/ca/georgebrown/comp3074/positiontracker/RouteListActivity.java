package ca.georgebrown.comp3074.positiontracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import ca.georgebrown.comp3074.positiontracker.model.Route;
import ca.georgebrown.comp3074.positiontracker.sql.DbHelper;

public class RouteListActivity extends AppCompatActivity {

    private static int REQUEST_CODE = 1;
    private static DbHelper dbHelper;
    private static ArrayList<Route> routes;
    private EditText search;
    private Button refresh;

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

        search = findViewById(R.id.txtSearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //refresh button
        refresh = findViewById(R.id.btnRefresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshList();
            }
        });
    }

    private void refreshList(){
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode== Activity.RESULT_OK){
                search.setText("");
                finish();
                startActivity(getIntent());
            }
        }
    }

}
