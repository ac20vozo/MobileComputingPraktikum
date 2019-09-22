package com.example.geogeo;

import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class homescreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_homescreen);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        frameLayout = (FrameLayout) findViewById(R.id.frag_container);
        loadFragment(new frag_play());

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
        Fragment fragment = null;
        switch (Item.getItemId()){
            case R.id.navigation_home:
                fragment = new frag_play();
                break;
        }
        switch (Item.getItemId()){
            case R.id.navigation_dashboard:
                fragment = new frag_stats();
                break;
        }
        switch (Item.getItemId()){
            case R.id.navigation_notifications:
                fragment = new frag_options();
                break;
        }
        return loadFragment(fragment);
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();
            return true;
        }

        return false;
    }
        /*@Override
        public void onClick(View view) {
            if (view.getId() == R.id.play_classicGame){
            Intent startClassicGame = new Intent(this, MainActivity.class);
            startActivity(startClassicGame);
            }

        }*/
}
