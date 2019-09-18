package com.example.geogeo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class DatabaseHandler {

    SQLiteDatabase db;

    DatabaseHandler() {
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.geogeo/databases/db.db", null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Answer(Id INTEGER, Answer TEXT, X Numeric, Y Numeric)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Game(Id INTEGER, Amount INTEGER, Points INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS GameQuestions(GameId INTEGER, QuestionId INTEGER, Points INTEGER, " +
                "AnswerX NUMERIC, AnswerY NUMERIC)");
        db.execSQL("CREATE TABLE IF NOT EXISTS PicQuestions(Id INTEGER, Pic BLOB, AnswerId INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Statistics(Id INTEGER, Games INTEGER, AverageScore INTEGER, TotalPoints INTEGER)");
    }


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
