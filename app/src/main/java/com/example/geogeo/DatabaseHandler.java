package com.example.geogeo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHandler {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseHandler instance;

    private DatabaseHandler(Context context) {
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

    public int getRandomPicQuestion(ArrayList<Integer> blacklist) {
        return 1;
    }

    public int getRandomTextQuestion(String type) {
        Random rand = new Random();
        Cursor cur;
        if (type.equals("all")) {
            cur = db.rawQuery("SELECT * FROM TextQuestion", null);
        } else {
            cur = db.rawQuery("SELECT * FROM TextQuestion WHERE Type " + type, null);
        }
        int result = rand.nextInt(cur.getCount()) + 1;
        cur.close();
        return result;
    }

    public int getRandomTextQuestion(ArrayList<Integer> blacklist, String type) {
        return 1;
    }

    // returns picture path of a question given the questionId
    public String getPic(int questionId) {
        Cursor c = db.rawQuery("SELECT PicPath FROM PicQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        String PicPath = c.getString(0);
        c.close();
        return PicPath;
    }

    public String getTexti(int questionId) {
        Cursor c = db.rawQuery("SELECT Text FROM TextQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        String Text = c.getString(0);
        c.close();
        return Text;
    }

    public byte[] getblop(int id) {
        Cursor c = db.rawQuery("SELECT PicPath FROM PicQuestion WHERE ID =" + id, null);
        c.moveToFirst();
        byte[] result = c.getBlob(0);
        return result;
    }

    public String getAnswerName(int answerId) {
        Cursor c = db.rawQuery("SELECT Answer FROM Answer WHERE Id =" + answerId, null);
        c.moveToFirst();
        String Answer = c.getString(0);
        c.close();
        return Answer;
    }

    public Double[] getAnswerCords(int answerId) {
        Double[] Cords = new Double[2];
        Cursor c = db.rawQuery("SELECT Answer FROM Answer WHERE Id =" + answerId, null);
        c.moveToFirst();
        Double x = c.getDouble(0);
        Double y = c.getDouble(1);
        Cords[0] = x;
        Cords[1] = y;
        c.close();
        return Cords;
    }

    public String[] getAnswer(int questionId) {
        String[] answer = {"0.0", "0.0", "New York"};
        return answer;
    }

    public int createGame(int amount) {
        int id = 0;
        return id;
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

    // returns distance to of given answer to correct location
    public double checkDistanceToAnswer(int gameId, int answerId, double xGuess, double yGuess) {
        Double[] Cords = new Double[2];
        Cords = getAnswerCords(answerId);

        return coordDistance(Cords[0], Cords[1], xGuess, yGuess);
    }

    // calculates distance between to coordinates in km
    public double coordDistance(double lon1, double lat1, double lon2, double lat2) {
        double xDifference = degreeToRadians(lon1) - degreeToRadians(lon2);
        double yDifference = degreeToRadians(lat1) - degreeToRadians(lat2);
        double angle = (Math.pow(Math.sin(xDifference / 2), 2) + Math.cos(yDifference) * Math.cos(xDifference) * (Math.pow(Math.sin(yDifference / 2), 2)));
        double c = 2 * Math.atan2(Math.sqrt(angle), Math.sqrt(1 - angle));
        double distance = 6378 * c;
        return distance;
    }

    // convert degree to radians
    public double degreeToRadians(double degree) {
        return degree * Math.PI / 180;
    }
}
