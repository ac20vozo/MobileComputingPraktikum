package com.example.geogeo;

import android.content.Context;
import java.util.ArrayList;
import java.util.Random;
import android.database.sqlite.SQLiteDatabase;

public class Controller {
    private Context context;
    private DatabaseHandler db;
    Random random = new Random();
    public Controller(Context context){
        this.context=context;
        db = DatabaseHandler.getInstance(context);
    }
    public void test02() {
        DatabaseHandler db;
        db = DatabaseHandler.getInstance(context);
        db.open();
        System.out.println("Random Pic Question: " + db.getRandomPicQuestion());
    }

    public void getitems(){
        DatabaseHandler db;
        db = DatabaseHandler.getInstance(context);
        db.open();
    }

    // kind can be random, text or pic
    public boolean createGame(int amount, String kind, String type){
        // blacklist ist Array von Array von ints (Mit 1. 0 oder 1 2. id von question)
        db.open();
        int gameId = db.createGame(amount);

        int id = 0;
        ArrayList<Integer[]> blacklist = new ArrayList<>();
        String randomChosen = "";

        for (int i = 0; i< amount;i++){
            if (kind == "random"){
                if (random.nextBoolean()){
                    randomChosen = "pic";
                }else{
                    randomChosen = "text";
                }
            }
            if (kind == "pic" || randomChosen == "pic"){
                id = db.getRandomPicQuestion(blacklist);
                blacklist.add(new Integer[]{1,id});
            }
            if (kind == "text" || randomChosen == "text"){
                id = db.getRandomTextQuestion(blacklist, type);
                blacklist.add(new Integer[]{0,id});
            }
            db.addRoundToGame(gameId, blacklist.get(i)[0], blacklist.get(i)[1]);
        }
        if (blacklist.size() == amount){
            return true;
        }else{
            return false;
        }
    }

    public void endGame(int gameId){
        // Because for now we have only one user
        int id = 1;
        db.addGameToStatistics(id, gameId);
    }
}
