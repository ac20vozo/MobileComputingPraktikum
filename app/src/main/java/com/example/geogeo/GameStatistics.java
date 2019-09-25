package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameStatistics extends AppCompatActivity {

    int gameId;
    View[] questions = new View[5];
    TextView[] score = new TextView[5];
    String[] pointsPerRound;
    int totalScore = 0;

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

        Intent mIntent = getIntent();
        gameId = mIntent.getIntExtra("gameId", 0);

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

        questions[0] = txtFrage1;
        questions[1] = txtFrage2;
        questions[2] = txtFrage3;
        questions[3] = txtFrage4;
        questions[4] = txtFrage5;

        score[0] = txtScore1;
        score[1] = txtScore2;
        score[2] = txtScore3;
        score[3] = txtScore4;
        score[4] = txtScore5;

        Controller con = new Controller(getApplicationContext());
        pointsPerRound = con.getPointsPerRound(gameId);

        for (int i = 0; i<pointsPerRound.length;i++){
            if (i == 5){
                break;
            }
            questions[i].setVisibility(View.VISIBLE);
            score[i].setText(pointsPerRound[i]);
            System.out.println(totalScore);
            totalScore = totalScore + Integer.parseInt(pointsPerRound[i]);
        }
        txtTotalScore.setText(Integer.toString(totalScore));



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, homescreen.class);
        startActivity(intent);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, Startseite.class);
        startActivity(intent);
    }
}
