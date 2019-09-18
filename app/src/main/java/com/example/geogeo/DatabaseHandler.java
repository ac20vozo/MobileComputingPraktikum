package com.example.geogeo;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class DatabaseHandler {

    public int getRandomPicQuestion(ArrayList<Integer> blacklist){
        return 1;
    }

    public Bitmap getPic(int questionId){
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap b = Bitmap.createBitmap(0,0,conf);
        return b;
    }

    public int getRandomTextQuestion(ArrayList<Integer> blacklist, String type){
        return 1;
    }

    public String getText(int questionId){
        return "Test";
    }

    public String[] getAnswer(int questionId){
        String[] answer = {"0.0","0.0", "New York"};
        return answer;
    }

    public int createGame(int amount){
        int id = 0;
        return id;
    }

    public void addQuestionToGame(int gameId, boolean isPicQuestion){
        return;
    }

    public int answerQuestion(int gameId, int questionId){
        int points = 0;
        return points;
    }
}
