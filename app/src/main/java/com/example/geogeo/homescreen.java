package com.example.geogeo;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class homescreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        frameLayout = (FrameLayout) findViewById(R.id.frag_container);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.navbar_play, R.id.navbar_profil, R.id.navbar_options)
        //        .build();
        //NavController navController = Navigation.findNavController(this, R.id.frag_container);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(navView, navController);
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
                fragment = new frag_profil();
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
}
