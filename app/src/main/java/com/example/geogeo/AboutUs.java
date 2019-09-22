package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    TextView txtAboutus;
    TextView txtSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        txtAboutus = findViewById(R.id.txtAboutus);
        txtSummary = findViewById(R.id.txtSummary);
    }
}
