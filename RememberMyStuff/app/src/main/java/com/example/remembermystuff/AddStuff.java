package com.example.remembermystuff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStuff extends AppCompatActivity {
    EditText nameTxt, locationTxt;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stuff);

        nameTxt = findViewById(R.id.add_stuff_name);
        locationTxt = findViewById(R.id.add_stuff_location);
        submitBtn = findViewById(R.id.add_stuff);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            nameTxt.setText(extras.getString("stuff_name"));
            locationTxt.setText(extras.getString("stuff_location"));
            submitBtn.setText("UPDATE STUFF");
            getSupportActionBar().setTitle("Update Stuff");
        }
    }

    public void saveStuff(View v){
        SQLiteDBHelper dbHelper = new SQLiteDBHelper(this);
        Stuff stuff;

        String stuffName = nameTxt.getText().toString();
        String stuffLocation = locationTxt.getText().toString();

        if (stuffName.equalsIgnoreCase("")|| stuffLocation.equalsIgnoreCase("")){
            if(stuffName.equalsIgnoreCase("")){
                nameTxt.setError("Stuff Name cannot be blank");
            } else {
                locationTxt.setError("Stuff Location cannot be blank");
            }

        } else {
            Bundle extras = getIntent().getExtras();
            if(extras!=null){
                stuff = new Stuff(extras.getInt("stuff_id"),
                        stuffName, stuffLocation);
                dbHelper.updateStuff(stuff);
                Toast.makeText(this, "Stuff successfully updated", Toast.LENGTH_LONG).show();
            } else {
                stuff = new Stuff(0, stuffName, stuffLocation);
                dbHelper.addStuff(stuff);
                Toast.makeText(this, "Stuff successfully added", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }

    public void cancelSubmit(View v){
        finish();
    }
}
