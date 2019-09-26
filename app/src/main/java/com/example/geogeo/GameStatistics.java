package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class GameStatistics extends AppCompatActivity {

    int gameId;

    Controller con;

    String[] pointsPerRound;

    int totalScore = 0;

    TextView txtTotalScore;

    int [] result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_statistics);

        txtTotalScore = findViewById(R.id.txtTotalScore);

///////////////
        ScrollView scrollView = new ScrollView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutParams);

        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(linearParams);

        scrollView.addView(linearLayout);
/////////////////

        Intent mIntent = getIntent();
        gameId = mIntent.getIntExtra("gameId", 0);



        con = new Controller(getApplicationContext());
        pointsPerRound = con.getPointsPerRound(gameId);

        result = parser(pointsPerRound);
        for (int j : result) {
            totalScore += j;
        }



        for (int i = 0; i<pointsPerRound.length;i++){

            TextView points = new TextView(this);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params1.setMargins(0, 30, 0, 30);
            params1.gravity = Gravity.CENTER;
            points.setLayoutParams(params1);
            String satz = "Question " + (i+1) + ": " + pointsPerRound[i];
            points.setText(satz);
            points.setTextSize(35);
            points.setTextColor(Color.BLACK);
            points.setTypeface(Typeface.DEFAULT_BOLD);
            Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.architects_daughter);
            points.setTypeface(typeface);
            points.setGravity(Gravity.CENTER);
            linearLayout.addView(points);

        }
        LinearLayout linearLayout1 = findViewById(R.id.rootContainer);
        if (linearLayout1 != null) {
            linearLayout1.addView(scrollView);


        }
        txtTotalScore.setText(Integer.toString(totalScore));
        txtTotalScore.setTextSize(35);
        txtTotalScore.setTextColor(Color.BLACK);
        txtTotalScore.setTypeface(Typeface.DEFAULT_BOLD);
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.architects_daughter);
        txtTotalScore.setTypeface(typeface);

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

    public int[] parser(String[] args) {
        int [] result = new int[args.length];
        for(int i=0; i<args.length; i++) {
            result[i] = Integer.parseInt(args[i]);
        }
        return result;

    }
}
