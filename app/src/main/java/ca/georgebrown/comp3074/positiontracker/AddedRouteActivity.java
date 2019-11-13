package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ca.georgebrown.comp3074.positiontracker.model.Route;

public class AddedRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_route);

        final EditText routeName = findViewById(R.id.txtRouteName);
        final EditText routeTag = findViewById(R.id.txtRouteTag);
        final Spinner rate = findViewById(R.id.spinner);

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
            public void onClick(View view) {
                //toast that shows added route
                Toast t = Toast.makeText(view.getContext(),"Route has been added!",Toast.LENGTH_LONG);
                t.show();
                String rName = routeName.getText().toString();
                String rTag = routeTag.getText().toString();

                int rRate = (Integer) rate.getSelectedItem();
                Route route = new Route();
                route.setRouteName(rName);
                route.setRating(rRate);

//                long id= dbHelper.addItem(item);
//                Toast.makeText(view.getContext(),"Entry added with id="+id,
//                        Toast.LENGTH_LONG).show(); //bubble at the bottom
//                word.setText("");
                setResult(RESULT_OK);
                finish();


            }
        });

    }
}
