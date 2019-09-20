package com.example.geogeo;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

public class Controller {
    private Context context;
    private DatabaseHandler db;
    Random random = new Random();

    public Controller(Context context) {
        this.context = context;
        db = DatabaseHandler.getInstance(context);
        db.open();
    }

    public void bloßeintest02() {
        DatabaseHandler db;
        db = DatabaseHandler.getInstance(context);
        db.open();
        System.out.println("Random Pic Question: " + db.getRandomPicQuestion());
    }

    public void showPic(byte[] bits, ImageView image, int questionId) {
        DatabaseHandler db;
        db = DatabaseHandler.getInstance(context);
        db.open();
        bits = db.getPicQuestion(questionId);
        Bitmap b = BitmapFactory.decodeByteArray(bits, 0, bits.length);
        image.setImageBitmap(Bitmap.createScaledBitmap(b, 120, 120, false));
        db.close();

    }

    public void showText(String input, TextView text, int questionId) {
        DatabaseHandler db;
        db = DatabaseHandler.getInstance(context);
        db.open();
        input = db.getTextQuestion(questionId);
        text.setText(input);
        db.close();

    }

    public void getitems() {
        DatabaseHandler db;
        db = DatabaseHandler.getInstance(context);
        db.open();
    }

    // kind can be random, text or pic
    // TODO: make sure that the amount is less or equal to the amount of questions
    public boolean createGame(int amount, String kind, String type) {
        // blacklist ist Array von Array von ints (Mit 1. 0 oder 1 2. id von question)
        db.open();
        if (kind.equals("random")) {
            if (!db.checkAmount(amount, "pic") || !db.checkAmount(amount, "text")) {
                return false;
            }
        } else if (!db.checkAmount(amount, kind)) {
            return false;
        }
        int gameId = db.createGame(amount);

        int id = 0;
        ArrayList<Integer[]> blacklist = new ArrayList<>();
        String randomChosen = "";

        for (int i = 0; i < amount; i++) {
            if (kind == "random") {
                if (random.nextBoolean()) {
                    randomChosen = "pic";
                } else {
                    randomChosen = "text";
                }
            }
            if (kind == "pic" || randomChosen == "pic") {
                id = db.getRandomPicQuestion(blacklist);
                blacklist.add(new Integer[]{1, id});
            }
            if (kind == "text" || randomChosen == "text") {
                id = db.getRandomTextQuestion(blacklist, type);
                blacklist.add(new Integer[]{0, id});
            }
            db.addRoundToGame(gameId, blacklist.get(i)[0], blacklist.get(i)[1]);
        }
        if (blacklist.size() == amount) {
            return true;
        } else {
            return false;
        }
    }

    public int[] getNextQuestion(int gameId) {
        SQLiteOpenHelper sqLiteOpenHelper = new DBHelper(this.context);
        SQLiteDatabase sqldb = sqLiteOpenHelper.getWritableDatabase();
        Cursor cur = sqldb.rawQuery("SELECT * FROM Round WHERE Points = -1;", null);
        int isPic = cur.getInt(cur.getColumnIndex("IsPicQuestion"));
        int qId = cur.getInt(cur.getColumnIndex("QuestionId"));
        cur.close();
        return new int[] {isPic, qId};
    }

    public void endGame(int gameId) {
        // Because for now we have only one user
        int id = 1;
        db.addGameToStatistics(id, gameId);
    }
    public String[] getStats(int userId){
        return db.getStats(userId);
    }
}

    // check stringConversion
    public int answerToRound(double x, double y, int gameId) {
        int[] QuestionInfo = db.getNextQuestion(gameId);
        int answerId = db.getAnswer(QuestionInfo[0], QuestionInfo[1]);
        int points = answerQuestion(gameId, answerId, x, y);
        return points;
    }

    // returns distance to of given answer to correct location
    public double checkDistanceToAnswer(int answerId, double xGuess, double yGuess) {
        Double[] Cords = new Double[2];
        Cords = db.getAnswerCords(answerId);

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

    public int answerQuestion(int gameId, int answerId, double xGuess, double yGuess) {
        int points = 1;
        double distance = checkDistanceToAnswer(answerId, xGuess, yGuess);
        // add a more sensible point calculation here
        points = (int) Math.ceil(points / distance);
        return points;

    }
}

