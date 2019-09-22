package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Startseite extends AppCompatActivity {

    TextView txtWillkommen;
    TextView txtGeo;
    Button btnSpielen;
    Button btnOption;
    Button btnBeenden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);

        txtWillkommen = findViewById(R.id.txtWillkommen);
        txtGeo = findViewById(R.id.txtGeo);
        btnSpielen = findViewById(R.id.btnSpielen);
        btnOption = findViewById(R.id.btnOption);
        btnBeenden = findViewById(R.id.btnBeenden);
    }



    public void spielenClick(View view){
        Intent spielenIntent = new Intent(this,homescreen.class);
        startActivity(spielenIntent);
    }

    public void aboutUsClick(View view){
        Intent aboutUsIntent = new Intent(this, AboutUs.class);
        startActivity(aboutUsIntent);
    }


    public void quitClick(View view){
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
