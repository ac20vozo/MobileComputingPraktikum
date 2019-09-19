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
}
