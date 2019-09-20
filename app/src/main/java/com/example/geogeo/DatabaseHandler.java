package com.example.geogeo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHandler {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseHandler instance;

    public DatabaseHandler(Context context) {
        this.openHelper = new DBHelper(context);
    }

    public static DatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHandler(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public int getRandomPicQuestion() {
        Random rand = new Random();
        Cursor cur = db.rawQuery("SELECT * FROM PicQuestion", null);
        int result = rand.nextInt(cur.getCount()) + 1;
        cur.close();
        return result;
    }

    public int getRandomPicQuestion(ArrayList<Integer[]> blacklist) {
        String sql = "SELECT * FROM PicQuestion WHERE ";
        for (Integer[] v : blacklist) {
            if (v[0] == 1) {
                sql += "id <> AND " + v[1];
            }
        }
        sql = sql.substring(0, sql.length() - 4);
        System.out.println(sql);
        Random rand = new Random();
        Cursor cur = db.rawQuery(sql, null);
        int result = rand.nextInt(cur.getCount()) + 1;
        cur.close();
        return result;
    }

    public int getRandomTextQuestion(String type) {
        Random rand = new Random();
        Cursor cur;
        if (type.equals("all")) {
            cur = db.rawQuery("SELECT * FROM TextQuestion", null);
        } else {
            cur = db.rawQuery("SELECT * FROM TextQuestion WHERE Type = " + type, null);
        }
        int result = rand.nextInt(cur.getCount()) + 1;
        cur.close();
        return result;
    }

    public int getRandomTextQuestion(ArrayList<Integer[]> blacklist, String type) {
        String sql = "SELECT * FROM TextQuestion WHERE ";
        if (!type.equals("all")) {
            sql += "Type = " + type + " AND ";
        }
        for (Integer[] v : blacklist) {
            if (v[0] == 0) {
                sql += "id <> AND " + v[1];
            }
        }
        sql = sql.substring(0, sql.length() - 4);
        System.out.println(sql);
        Random rand = new Random();
        Cursor cur = db.rawQuery(sql, null);
        int result = rand.nextInt(cur.getCount()) + 1;
        cur.close();
        return result;
    }

    // returns picture path of a question given the questionId
    public byte[] getbytes(int questionId) {
        Cursor c = db.rawQuery("SELECT Pic FROM PicQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        byte[] result = c.getBlob(0);
        c.close();
        return result;
    }

    public String getTextQuestion(int questionId) {
        Cursor c = db.rawQuery("SELECT Text FROM TextQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        String result = c.getString(0);
        c.close();
        return result;
    }

    public String getTextType(int questionId) {
        Cursor c = db.rawQuery("SELECT Type FROM TextQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        String Type = c.getString(0);
        c.close();
        return Type;
    }



    public String getAnswerAnswer(int answerId) {
        Cursor c = db.rawQuery("SELECT Answer FROM Answer WHERE Id =" + answerId, null);
        c.moveToFirst();
        String Answer = c.getString(0);
        c.close();
        return Answer;
    }

    public Double[] getAnswerCords(int answerId) {
        Double[] Cords = new Double[2];
        Cursor c = db.rawQuery("SELECT X FROM Answer WHERE Id =" + answerId, null);
        c.moveToFirst();
        Double x = c.getDouble(0);
        Cords[0] = x;
        c.close();

        c = db.rawQuery("SELECT Y FROM Answer WHERE Id =" + answerId, null);
        c.moveToFirst();
        Double y = c.getDouble(0);
        Cords[1] = y;
        c.close();
        return Cords;
    }

    // returns answerId of given question
    public int getAnswer(int isPicQuestion, int questionId) {
        int AnswerId;
        if (isPicQuestion != 0) {
            Cursor c = db.rawQuery("SELECT AnswerId FROM PicQuestion WHERE ID =" + questionId, null);
            c.moveToFirst();
            AnswerId = c.getInt(0);
        } else {
            Cursor c = db.rawQuery("SELECT AnswerId FROM TextQuestion WHERE ID =" + questionId, null);
            c.moveToFirst();
            AnswerId = c.getInt(0);
        }
        return AnswerId;
    }

    // moved from Controller, not checked
    public int[] getNextQuestion(int gameId) {
        Cursor cur = db.rawQuery("SELECT * FROM Round WHERE Points = -1 AND GameId =" + gameId + " ;", null);
        int isPic = cur.getInt(cur.getColumnIndex("IsPicQuestion"));
        int qId = cur.getInt(cur.getColumnIndex("QuestionId"));
        cur.close();
        return new int[]{isPic, qId};
    }

    public int createGame(int amount) {
        db.execSQL("INSERT INTO Game (Amount, Points) VALUES (" + Integer.valueOf(amount) + ", 0)");
        Cursor c = db.rawQuery("SELECT MAX(Id) FROM Game", null);
        c.moveToFirst();
        String id = c.getString(0);
        c.close();
        return Integer.valueOf(id);
    }

    public boolean checkAmount(int amount, String type) {
        if (type.equals("pic")) {
            return amount <= db.rawQuery("SELECT * FROM PicQuestions", null).getCount();
        } else {
            return amount <= db.rawQuery("SELECT * FROM TextQuestions", null).getCount();
        }
    }

    public void addRoundToGame(int gameId, int isPicQuestion, int questionId) {
        db.execSQL("INSERT INTO Round (GameId, QuestionId, isPicQuestion, Points, AnswerX, AnswerY) " +
                "VALUES (" + Integer.toString(gameId) + ", " + Integer.toString(questionId) + ", " +
                Integer.toString(isPicQuestion) + ", null, null, null)");
    }

    // Not allowed to use dotts in PicPath
    public void addQuestion(int isPicQuestion, String PicPath, int categoryId, int answerId, String text,
                            String type) {
        if (isPicQuestion == 1) {
            db.execSQL("INSERT INTO PicQuestion (PicPath, AnswerId, CategoryId) VALUES('" +
                    PicPath + "', " + Integer.toString(answerId) + ", " + Integer.toString(categoryId) + ")");
        } else {
            db.execSQL("INSERT INTO TextQuestion (Text, Type, AnswerId, CategoryId) VALUES ('" +
                    text + "', '" + type + "', " + Integer.toString(answerId) + ", " + Integer.toString(categoryId) +
                    ")");
        }
    }

    public int addAnswer(String Answer, float x, float y) {
        db.execSQL("INSERT INTO Answer (Answer, X, Y) VALUES ('" + Answer + "', " + Float.toString(x) +
                ", " + Float.toString(y) + ")");
        Cursor c = db.rawQuery("SELECT MAX(Id) FROM Answer", null);
        c.moveToFirst();
        String id = c.getString(0);
        c.close();
        return Integer.valueOf(id);
    }

    public void addCategory(String country, String continent) {
        db.execSQL("INSERT INTO Category (Country, Continent) VALUES ('" +
                country + "', '" + continent + "')");
    }

    public void addGameToStatistics(int userId, int gameId) {
        Cursor c = db.rawQuery("SELECT Games, AverageScore, TotalPoints  FROM Statistics" +
                " WHERE Id = " + Integer.toString(userId), null);
        System.out.println(c.getCount());
        if (c.getCount() == 0) {
            db.execSQL("INSERT INTO Statistics (Games, AverageScore, TotalPoints) VALUES " +
                    "(0, 0, 0)");
            c = db.rawQuery("SELECT Games, AverageScore, TotalPoints  FROM Statistics" +
                    " WHERE Id = " + Integer.toString(userId), null);
        }
        c.moveToFirst();
        int newGames = 0;
        float newAverage = 0;
        int newTotal = 0;

        Cursor d = db.rawQuery("SELECT Points FROM Game WHERE Id =" + Integer.toString(gameId), null);
        d.moveToFirst();
        int gamePoints = d.getInt(0);

        newGames = c.getInt(0) + 1;
        newTotal = c.getInt(2) + gamePoints;
        newAverage = newTotal / newGames;

        db.execSQL("UPDATE Statistics SET Games =" + Integer.toString(newGames) +
                ", AverageScore =" + newAverage + ", TotalPoints =" + Integer.toString(newTotal) +
                " WHERE Id =" + Integer.toString(userId));
        db.close();
    }
    // not tested yet
    public void updateRound(int gameId, int questionId, double x, double y, int points){
        db.execSQL("UPDATE Round " +
                "SET AnswerX = " + x + ", AnswerY = " + y + ", Points = " + points +
                " WHERE GameId =" + gameId + " AND questionId =" + questionId + ";");
        db.close();
    }

    public String[] getStats(int userId){
        Cursor c = db.rawQuery("SELECT Games, AverageScore, TotalPoints FROM Statistics WHERE Id = " + userId, null);
        if (c.getCount() == 0){
            db.execSQL("INSERT INTO Statistics (Games, AverageScore, TotalPoints) VALUES " +
                    "(0, 0, 0)");
            c = db.rawQuery("SELECT Games, AverageScore, TotalPoints  FROM Statistics" +
                    " WHERE Id = " + Integer.toString(userId), null);
        }
        c.moveToFirst();
        String[] stats = {Integer.toString(c.getInt(0)), Integer.toString(c.getInt(1)), Integer.toString(c.getInt(2))};
        return stats;
    }

}
