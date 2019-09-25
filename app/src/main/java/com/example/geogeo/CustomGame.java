package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;

import static android.app.PendingIntent.getActivity;
import static com.j256.ormlite.stmt.QueryBuilder.JoinWhereOperation.AND;

public class CustomGame extends AppCompatActivity {

    protected CheckBox checkText, checkPhoto, checkAfrica, checkEurope, checkNorthAmerica, checkSouthAmerica, checkAsia, checkOceania;
    protected Button btnPlayCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_game);

        checkText = findViewById(R.id.checkText);
        checkPhoto = findViewById(R.id.checkPhoto);
        checkAfrica = findViewById(R.id.checkAfrica);
        checkEurope = findViewById(R.id.checkEuropa);
        checkNorthAmerica = findViewById(R.id.checkNorthAmerica);
        checkSouthAmerica = findViewById(R.id.checkSoutAmerica);
        checkOceania = findViewById(R.id.checkOceania);
        checkAsia = findViewById(R.id.checkAsia);
        btnPlayCustom = findViewById(R.id.btnPlayCustom);
    }

    public void playCustomClick(View view) {

        if (checkText.isChecked() && checkPhoto.isChecked()) {
            if (checkAsia.isChecked()){}
            else if (checkAfrica.isChecked()){}
            else if (checkEurope.isChecked()){}
            else if (checkNorthAmerica.isChecked()){}
            else if (checkSouthAmerica.isChecked()){}
            else if (checkOceania.isChecked()){}
        }
        else if (checkText.isChecked() && !checkPhoto.isChecked()){
            if (checkAsia.isChecked()){}
            else if (checkAfrica.isChecked()){}
            else if (checkEurope.isChecked()){}
            else if (checkNorthAmerica.isChecked()){}
            else if (checkSouthAmerica.isChecked()){}
            else if (checkOceania.isChecked()){}
        }
        else if (!checkText.isChecked() && checkPhoto.isChecked()){
            if (checkAsia.isChecked()){}
            else if (checkAfrica.isChecked()){}
            else if (checkEurope.isChecked()){}
            else if (checkNorthAmerica.isChecked()){}
            else if (checkSouthAmerica.isChecked()){}
            else if (checkOceania.isChecked()){}
        }

    }
}
