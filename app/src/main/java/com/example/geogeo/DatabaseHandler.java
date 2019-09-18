package com.example.geogeo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHandler {

    SQLiteDatabase db;

    DatabaseHandler() {
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.geogeo/databases/db.db", null);

        db.execSQL("DROP TABLE IF EXISTS Answer");
        db.execSQL("CREATE TABLE IF NOT EXISTS Answer(Id INTEGER PRIMARY KEY AUTOINCREMENT, Answer TEXT, X Numeric, Y Numeric)");

        db.execSQL("CREATE TABLE IF NOT EXISTS Game(Id INTEGER PRIMARY KEY AUTOINCREMENT, Amount INTEGER, Points INTEGER)");

        // Can't add Foreign Key to QuestionId because there exist two tables with questions.
        db.execSQL("CREATE TABLE IF NOT EXISTS Round(Id INTEGER PRIMARY KEY AUTOINCREMENT, GameId INTEGER, QuestionId INTEGER, IsPicQuestion Integer, Points INTEGER," +
                "AnswerX NUMERIC, AnswerY NUMERIC,  FOREIGN KEY (GameId) REFERENCES Game(Id))");

        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("CREATE TABLE IF NOT EXISTS Category (Id INTEGER PRIMARY KEY AUTOINCREMENT, Country TEXT, Continent TEXT)");

        db.execSQL("DROP TABLE IF EXISTS TextQuestions");
        db.execSQL("CREATE TABLE IF NOT EXISTS TextQuestions(Id INTEGER PRIMARY KEY AUTOINCREMENT, Text TEXT, Type TEXT, AnswerId INTEGER, CategoryId INTEGER, FOREIGN KEY (AnswerId) REFERENCES Answer(id), FOREIGN KEY (CategoryId) REFERENCES Category(Id))");

        db.execSQL("DROP TABLE IF EXISTS PicQuestions");
        db.execSQL("CREATE TABLE IF NOT EXISTS PicQuestions(Id INTEGER PRIMARY KEY AUTOINCREMENT, PicPath TEXT, AnswerId INTEGER, CategoryId INTEGER, FOREIGN KEY (AnswerId) REFERENCES Answer(id), FOREIGN KEY (CategoryId) REFERENCES Category(Id))");

        db.execSQL("CREATE TABLE IF NOT EXISTS Statistics(Id INTEGER PRIMARY KEY AUTOINCREMENT, Games INTEGER, AverageScore REAL, TotalPoints INTEGER)");

        db.execSQL("INSERT INTO Answers (Answer, X, Y) VALUES ('Belin', 0, 0)");
        db.execSQL("INSERT INTO Category (Country, Continent) VALUES ('Germany', 'Europe')");
        db.execSQL("INSERT INTO PicQuestions (PicPath, AnswerId, CategoryId) VALUES ('berlin.png', 1, 1)");
        db.execSQL("INSERT INTO TextQuestions (Text, Type, AnswerId, CategoryId) VALUES ('What is the Capital of Germany', 'Quiz', 1, 1)");

        System.out.println(getRandomPicQuestion());
        System.out.println(getRandomTextQuestion("All"));
    }


    public int getRandomPicQuestion() {
        Random rand = new Random();
        Cursor cur = SQLiteDatabase.openDatabase("/data/data/com.example.geogeo/databases/db.db", null, 0).rawQuery("SELECT * FROM PicQuestions", null);
        int result = rand.nextInt(cur.getCount()) + 1;
        cur.close();
        return result;
    }

    public int getRandomPicQuestion(ArrayList<Integer> blacklist) {
        return 1;
    }

    public int getRandomTextQuestion(String type) {
        Random rand = new Random();
        SQLiteDatabase db_temp = SQLiteDatabase.openDatabase("/data/data/com.example.geogeo/databases/db.db", null, 0);
        Cursor cur;
        if (type.equals("All")) {
            cur = db_temp.rawQuery("SELECT * FROM TextQuestions", null);
        } else {
            cur = db_temp.rawQuery("SELECT * FROM TextQuestions WHERE Type " + type, null);
        }
        int result = rand.nextInt(cur.getCount()) + 1;
        cur.close();
        return result;
    }

    public int getRandomTextQuestion(ArrayList<Integer> blacklist, String type) {
        return 1;
    }

    public Bitmap getPic(int questionId) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap b = Bitmap.createBitmap(0, 0, conf);
        return b;
    }

    public String getText(int questionId) {
        return "Test";
    }

    public String[] getAnswer(int questionId) {
        String[] answer = {"0.0", "0.0", "New York"};
        return answer;
    }

    public int createGame(int amount) {
        int id = 0;
        return id;
    }

    public void addRoundToGame(int gameId, int isPicQuestion, int questionId){
        db.execSQL("INSERT INTO Round (GameId, QuestionId, isPicQuestion, Points, AnswerX, AnswerY) " +
                "VALUES ("+ Integer.toString(gameId) + ", " + Integer.toString(questionId) + ", " +
                Integer.toString(isPicQuestion) + ", null, null, null)");
    }

    // Not allowed to use dotts in PicPath
    public void addQuestion(int isPicQuestion, String PicPath, int categoryId, int answerId, String text,
                                String type){
        if (isPicQuestion == 1){
            db.execSQL("INSERT INTO PicQuestions (PicPath, AnswerId, CategoryId) VALUES('" +
                    PicPath + "', " + Integer.toString(answerId) + ", " + Integer.toString(categoryId) + ")");
        }
        else{
            db.execSQL("INSERT INTO TextQuestions (Text, Type, AnswerId, CategoryId) VALUES ('" +
                    text + "', '" + type + "', " + Integer.toString(answerId) + ", " + Integer.toString(categoryId) +
                    ")");
        }
    }

    public int addAnswer(String Answer, float x, float y){
        db.execSQL("INSERT INTO Answer (Answer, X, Y) VALUES ('" + Answer + "', " + Float.toString(x) +
                ", " + Float.toString(y) + ")");
        Cursor c = db.rawQuery("SELECT MAX(Id) FROM Answer", null);
        c.moveToFirst();
        String id = c.getString(0);
        c.close();
        return Integer.valueOf(id);
    }

    public void addCategory(String country, String continent){
        db.execSQL("INSERT INTO Category (Country, Continent) VALUES ('" +
                country +"', '" + continent + "')");
    }

    public int answerQuestion(int gameId, int questionId) {
        int points = 0;
        return points;
    }
}
