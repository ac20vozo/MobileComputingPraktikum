package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameStatistics extends AppCompatActivity {

    TextView txtStatistics;
    TextView txtDist0;
    TextView txtScore0;
    TextView txtFrage0;
    TextView txtFrage1;
    TextView txtFrage2;
    TextView txtFrage3;
    TextView txtFrage4;
    TextView txtFrage5;
    TextView txtDist1;
    TextView txtDist2;
    TextView txtDist3;
    TextView txtDist4;
    TextView txtDist5;
    TextView txtScore1;
    TextView txtScore2;
    TextView txtScore3;
    TextView txtScore4;
    TextView txtScore5;
    TextView txtGesamt;
    TextView txtTotalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_statistics);

        txtStatistics= findViewById(R.id.txtStatistics);
        txtFrage0 = findViewById(R.id.txtFrage0);
        txtDist0 = findViewById(R.id.txtDist0);
        txtDist0.setPaintFlags(txtDist0.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        txtScore0 = findViewById(R.id.txtScore0);
        txtScore0.setPaintFlags(txtScore0.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        txtFrage1 = findViewById(R.id.txtFrage1);
        txtFrage2 = findViewById(R.id.txtFrage2);
        txtFrage3 = findViewById(R.id.txtFrage3);
        txtFrage4 = findViewById(R.id.txtFrage4);
        txtFrage5 = findViewById(R.id.txtFrage5);
        txtDist1 = findViewById(R.id.txtDist1);
        txtDist2 = findViewById(R.id.txtDist2);
        txtDist3 = findViewById(R.id.txtDist3);
        txtDist4 = findViewById(R.id.txtDist4);
        txtDist5 = findViewById(R.id.txtDist5);
        txtScore1 = findViewById(R.id.txtScore1);
        txtScore2 = findViewById(R.id.txtScore2);
        txtScore3 = findViewById(R.id.txtScore3);
        txtScore4 = findViewById(R.id.txtScore4);
        txtScore5 = findViewById(R.id.txtScore5);
        txtGesamt = findViewById(R.id.txtGesamt);
        txtTotalScore = findViewById(R.id.txtTotalScore);
    }

    public void onScreenClick(View view){
        Intent screenIntent = new Intent(this, homescreen.class);
        startActivity(screenIntent);
    }
}
