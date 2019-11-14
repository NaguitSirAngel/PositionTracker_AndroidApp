package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ca.georgebrown.comp3074.positiontracker.model.Route;
import ca.georgebrown.comp3074.positiontracker.sql.DbHelper;

public class EditRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);

        final DbHelper dbHelper = new DbHelper(this);

        final EditText name = findViewById(R.id.editName);
        final Spinner rate = findViewById(R.id.spinRatings);
        final EditText tags = findViewById(R.id.editTags);

        final Route route = (Route)getIntent().getExtras().getSerializable("route");
        String myString = String.valueOf(route.getRating()); //the value you want the position for
        ArrayAdapter myAdap = (ArrayAdapter) rate.getAdapter(); //cast to an ArrayAdapter
        int spinnerPosition = myAdap.getPosition(myString);

        name.setText(route.getRouteName());
        rate.setSelection(spinnerPosition); //set the default according to value
//        tags.setText(route.getTags());

        Button saveBtn = findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rName = name.getText().toString();
//              String rTag = rate.getText().toString();
                int rRate = Integer.parseInt(rate.getSelectedItem().toString());

                route.setRouteName(rName);
//              route.setTags(tags);
                route.setRating(rRate);

                dbHelper.updateRoute(route);

                Toast t = Toast.makeText(view.getContext(),"Successfully updated route!",Toast.LENGTH_LONG);
                t.show();
                finish();
            }
        });
    }
}
