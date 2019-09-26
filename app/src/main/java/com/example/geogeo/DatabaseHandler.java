package com.example.geogeo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;

public class DatabaseHandler {
    private static final String DATABASE_NAME = "db.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseHandler instance;

    public DatabaseHandler(Context context) {
        this.openHelper = new SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteAssetHelper createSQLiteAssetHelper(Context context) {
        return new SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
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

//    public int getRandomPicQuestion() {
//        open();
//        Random rand = new Random();
//        Cursor cur = db.rawQuery("SELECT * FROM PicQuestion", null);
//        int result = rand.nextInt(cur.getCount()) + 1;
//        cur.close();
//        db.close();
//        return result;
//    }

    public int getRandomPicQuestion(ArrayList<Integer[]> blacklist) {
        open();
        String sql = "SELECT Id FROM PicQuestion WHERE ";
        for (Integer[] v : blacklist) {
            if (v[0] == 1) {
                sql += " id <> " + v[1] + " AND";
            }
        }
        sql = sql.substring(0, sql.length() - 4);
        Random rand = new Random();
        Cursor cur = db.rawQuery(sql, null);
        int[] qIds = new int[cur.getCount()];
        for (int i = 0; i < cur.getCount(); i++) {
            cur.moveToNext();
            qIds[i] = cur.getInt(0);
        }
        cur.close();
        db.close();
        int result = rand.nextInt(qIds.length);
        System.out.println("Pic Question Id is: " + qIds[result]);
        return qIds[result];
    }

//    public int getRandomTextQuestion(String type) {
//        open();
//        Random rand = new Random();
//        Cursor cur;
//        if (type.equals("all")) {
//            cur = db.rawQuery("SELECT * FROM TextQuestion", null);
//        } else {
//            cur = db.rawQuery("SELECT * FROM TextQuestion WHERE Type = " + type, null);
//        }
//        int result = rand.nextInt(cur.getCount()) + 1;
//        cur.close();
//        db.close();
//        return result;
//    }

    public int getRandomTextQuestion(ArrayList<Integer[]> blacklist, String type) {
        open();
        String sql = "SELECT Id FROM TextQuestion WHERE ";
        if (!type.equals("all")) {
            sql += "Type = " + type + " AND ";
        }
        for (Integer[] v : blacklist) {
            if (v[0] == 0) {
                sql += " id <> " + v[1] + " AND";
            }
        }
        sql = sql.substring(0, sql.length() - 4);
        Random rand = new Random();
        Cursor cur = db.rawQuery(sql, null);
        int[] qIds = new int[cur.getCount()];
        for (int i = 0; i < cur.getCount(); i++) {
            cur.moveToNext();
            qIds[i] = cur.getInt(0);
        }
        cur.close();
        db.close();
        int result = rand.nextInt(qIds.length);
        System.out.println("Text Question Id is: " + qIds[result]);
        return qIds[result];
    }

    // returns picture path of a question given the questionId
    public byte[] getbytes(int questionId) {
        open();
        Cursor c = db.rawQuery("SELECT Pic FROM PicQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        byte[] result = c.getBlob(0);
        c.close();
        db.close();
        return result;
    }

    public int getQuestionCount(){
        open();
        Cursor c = db.rawQuery("SELECT * FROM PicQuestion", null);
        int count1 = c.getCount();
        c.close();
        c = db.rawQuery("SELECT * FROM TextQuestion", null);
        int count2 = c.getCount();
        c.close();
        db.close();

        return min(count1,count2);
    }

    public String getTextQuestion(int questionId) {
        open();
        Cursor c = db.rawQuery("SELECT Text FROM TextQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        String result = c.getString(0);
        c.close();
        db.close();
        return result;
    }

    public String getTextType(int questionId) {
        open();
        Cursor c = db.rawQuery("SELECT Type FROM TextQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        String Type = c.getString(0);
        c.close();
        db.close();
        return Type;
    }


    public String getAnswerAnswer(int answerId) {
        open();
        Cursor c = db.rawQuery("SELECT Answer FROM Answer WHERE Id =" + answerId, null);
        c.moveToFirst();
        String Answer = c.getString(0);
        c.close();
        db.close();
        return Answer;
    }

    public Double[] getAnswerCords(int answerId) {
        open();
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
        db.close();
        return Cords;
    }

    // returns answerId of given question
    public int getAnswerId(int isPicQuestion, int questionId) {
        open();
        int AnswerId;
        if (isPicQuestion != 0) {
            Cursor c = db.rawQuery("SELECT AnswerId FROM PicQuestion WHERE ID =" + questionId, null);
            c.moveToFirst();
            AnswerId = c.getInt(0);
            c.close();
        } else {
            Cursor c = db.rawQuery("SELECT AnswerId FROM TextQuestion WHERE ID =" + questionId, null);
            c.moveToFirst();
            AnswerId = c.getInt(0);
            c.close();
        }
        db.close();
        return AnswerId;
    }

    public int[] getNextQuestion(int gameId) {
        open();
        Cursor cur = db.rawQuery("SELECT IsPicQuestion, QuestionId FROM Round WHERE Points IS NULL AND GameId =" + gameId + " ;", null);
        cur.moveToFirst();
        if (cur.getCount() == 0) {
            db.close();
            return new int[]{-1, -1};
        }
        int isPic = cur.getInt(0);
        int qId = cur.getInt(1);
        cur.close();
        db.close();
        return new int[]{isPic, qId};
    }

    public int createGame(int amount) {
        open();
        db.execSQL("INSERT INTO Game (Amount, Points) VALUES (" + Integer.valueOf(amount) + ", 0)");
        Cursor c = db.rawQuery("SELECT MAX(Id) FROM Game", null);
        c.moveToFirst();
        String id = c.getString(0);
        c.close();
        db.close();
        return Integer.valueOf(id);
    }

    public boolean checkAmount(int amount, String kind) {
        open();
        boolean result;
        if (kind.equals("pic")) {
            result = amount <= db.rawQuery("SELECT * FROM PicQuestion", null).getCount();
        } else {
            result = amount <= db.rawQuery("SELECT * FROM TextQuestion", null).getCount();
        }
        db.close();
        return result;
    }

    public void addRoundToGame(int gameId, int isPicQuestion, int questionId) {
        open();
        db.execSQL("INSERT INTO Round (GameId, QuestionId, isPicQuestion, Points, AnswerX, AnswerY) " +
                "VALUES (" + Integer.toString(gameId) + ", " + Integer.toString(questionId) + ", " +
                Integer.toString(isPicQuestion) + ", null, null, null)");
        db.close();
    }

    public void addQuestion(int isPicQuestion, String PicPath, int categoryId, int answerId, String text,
                            String type) {
        open();
        if (isPicQuestion == 1) {
            db.execSQL("INSERT INTO PicQuestion (PicPath, AnswerId, CategoryId) VALUES('" +
                    PicPath + "', " + Integer.toString(answerId) + ", " + Integer.toString(categoryId) + ")");
        } else {
            db.execSQL("INSERT INTO TextQuestion (Text, Type, AnswerId, CategoryId) VALUES ('" +
                    text + "', '" + type + "', " + Integer.toString(answerId) + ", " + Integer.toString(categoryId) +
                    ")");
        }
        db.close();
    }

    public int addAnswer(String Answer, float x, float y) {
        open();
        db.execSQL("INSERT INTO Answer (Answer, X, Y) VALUES ('" + Answer + "', " + Float.toString(x) +
                ", " + Float.toString(y) + ")");
        Cursor c = db.rawQuery("SELECT MAX(Id) FROM Answer", null);
        c.moveToFirst();
        String id = c.getString(0);
        c.close();
        db.close();
        return Integer.valueOf(id);
    }

    public void addCategory(String country, String continent) {
        open();
        db.execSQL("INSERT INTO Category (Country, Continent) VALUES ('" +
                country + "', '" + continent + "')");
        db.close();
    }

    public void addGameToStatistics(int userId, int gameId) {
        open();
        Cursor c = db.rawQuery("SELECT Games, AverageScore, TotalPoints  FROM Statistics" +
                " WHERE Id = " + userId, null);
        System.out.println(c.getCount());
        if (c.getCount() == 0) {
            db.execSQL("INSERT INTO Statistics (Games, AverageScore, TotalPoints) VALUES " +
                    "(0, 0, 0)");
            c = db.rawQuery("SELECT Games, AverageScore, TotalPoints  FROM Statistics" +
                    " WHERE Id = " + userId, null);
        }
        c.moveToFirst();

        Cursor d = db.rawQuery("SELECT Points FROM Game WHERE Id =" + gameId, null);
        d.moveToFirst();
        int gamePoints = d.getInt(0);

        int newGames = c.getInt(0) + 1;
        int newTotal = c.getInt(2) + gamePoints;
        float newAverage = newTotal / newGames;

        db.execSQL("UPDATE Statistics SET Games =" + newGames +
                ", AverageScore =" + newAverage + ", TotalPoints =" + newTotal +
                " WHERE Id =" + userId);
        db.close();

    }

    // not tested yet
    public void updateRound(int gameId, int questionId, int isPicQuestion, double x, double y, int points) {
        open();
        db.execSQL("UPDATE Round " +
                "SET AnswerX = " + x + ", AnswerY = " + y + ", Points = " + points +
                " WHERE GameId = " + gameId + " AND questionId = " + questionId + " And " +
                "IsPicQuestion = " + isPicQuestion);
        db.close();
    }

    public String[] getStats(int userId) {
        open();
        Cursor c = db.rawQuery("SELECT Games, AverageScore, TotalPoints FROM Statistics WHERE Id = " + userId, null);
        if (c.getCount() == 0) {
            db.execSQL("INSERT INTO Statistics (Games, AverageScore, TotalPoints) VALUES " +
                    "(0, 0, 0)");
            c = db.rawQuery("SELECT Games, AverageScore, TotalPoints  FROM Statistics" +
                    " WHERE Id = " + Integer.toString(userId), null);
        }
        c.moveToFirst();
        String[] stats = {Integer.toString(c.getInt(0)), Integer.toString(c.getInt(1)), Integer.toString(c.getInt(2))};
        db.close();
        return stats;
    }

    public boolean isGameOver(int gameId) {
        open();
        Cursor c = db.rawQuery("SELECT AnswerX FROM Round " +
                "WHERE GameId = " + gameId + " AND AnswerX IS NUll", null);
        if (c.getCount() == 0) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public int getPoints(int gameId){
        open();
        Cursor c = db.rawQuery("SELECT SUM(Points) FROM ROUND WHERE GameId = " + gameId, null);
        c.moveToFirst();
        if (! (c.getCount() == 0)){
            db.close();
            return c.getInt(0);
        }
        db.close();
        return 0;
    }

    public void countPointsOfRounds(int gameId) {
        open();
        Cursor c = db.rawQuery("SELECT SUM(Points) FROM Round WHERE GameId = " + gameId, null);
        c.moveToFirst();
        String result = c.getString(0);
        db.execSQL("UPDATE Game SET Points = " + result + " WHERE Id = " + gameId);
        db.close();
    }

    public String[] getPointsPerRound(int gameId) {
        open();
        Cursor c = db.rawQuery("SELECT Points FROM Round WHERE GameId = " + gameId, null);
        c.moveToFirst();
        String[] result = new String[c.getCount()];
        for (int i = 0; i < c.getCount(); i++) {
            result[i] = c.getString(0);
            c.moveToNext();
        }
        db.close();
        return result;
    }
}
