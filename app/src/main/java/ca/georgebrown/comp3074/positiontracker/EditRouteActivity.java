package ca.georgebrown.comp3074.positiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.georgebrown.comp3074.positiontracker.model.Route;

public class EditRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);

        final Route route = (Route)getIntent().getExtras().getSerializable("route");

        Button saveBtn = findViewById(R.id.btnDelete);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(view.getContext(),"Successfully updated route!",Toast.LENGTH_LONG);
                t.show();
                finish();
            }
        });
    }
}
