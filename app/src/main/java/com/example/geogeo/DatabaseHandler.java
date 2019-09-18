package com.example.geogeo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class DatabaseHandler {

    SQLiteDatabase db;

    DatabaseHandler() {
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.geogeo/databases/db.db", null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Answer(Id INTEGER PRIMARY KEY AUTOINCREMENT, Answer TEXT, X Numeric, Y Numeric)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Game(Id INTEGER PRIMARY KEY AUTOINCREMENT, Amount INTEGER, Points INTEGER)");

        // Can't add Foreign Key to QuestionId because there exist two tables with questions.
        db.execSQL("CREATE TABLE IF NOT EXISTS Round(Id INTEGER PRIMARY KEY AUTOINCREMENT, GameId INTEGER, QuestionId INTEGER, IsPicQuestion Integer, Points INTEGER," +
                "AnswerX NUMERIC, AnswerY NUMERIC,  FOREIGN KEY (GameId) REFERENCES Game(Id))");

        db.execSQL("CREATE TABLE IF NOT EXISTS Category (Id INTEGER PRIMARY KEY AUTOINCREMENT, Country TEXT, Continent TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS TextQuestions(Id INTEGER PRIMARY KEY AUTOINCREMENT, Text TEXT, Type TEXT, AnswerId INTEGER, CategoryId INTEGER, FOREIGN KEY (AnswerId) REFERENCES Answer(id), FOREIGN KEY (CategoryId) REFERENCES Category(Id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS PicQuestions(Id INTEGER PRIMARY KEY AUTOINCREMENT, PicPath TEXT, AnswerId INTEGER, CategoryId INTEGER, FOREIGN KEY (AnswerId) REFERENCES Answer(id), FOREIGN KEY (CategoryId) REFERENCES Category(Id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS Statistics(Id INTEGER PRIMARY KEY AUTOINCREMENT, Games INTEGER, AverageScore REAL, TotalPoints INTEGER)");
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
