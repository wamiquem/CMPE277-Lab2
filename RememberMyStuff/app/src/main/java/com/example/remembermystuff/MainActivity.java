package com.example.remembermystuff;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.example.remembermystuff.StuffListFragment.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity
implements OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StuffListFragment stuffListFragment = new StuffListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, stuffListFragment,
                "STUFF_LIST_FRAGMENT").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent addStuffIntent = new Intent(this, AddStuff.class);
                startActivity(addStuffIntent);
                return true;
        }
        return true;
    }

    @Override
    public void onStuffSelected(Stuff stuff) {
        StuffDetailsFragment detailFragment = StuffDetailsFragment.newInstance(stuff.getId(),
                stuff.getName(), stuff.getLocation());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,detailFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onResume(){
        super.onResume();
        StuffListFragment frg = null;
        frg = (StuffListFragment) getSupportFragmentManager().findFragmentByTag("STUFF_LIST_FRAGMENT");
        getSupportFragmentManager().beginTransaction().detach(frg).attach(frg).commit();

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
