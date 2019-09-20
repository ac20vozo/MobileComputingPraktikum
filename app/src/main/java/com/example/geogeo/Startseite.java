package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
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



    /*private void spielenClick(View view){
        Intent spielenIntent = new Intent(this,--neuerIntent--);
        startActivity(spielenIntent);
    }*/
}
