package com.example.geogeo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Controller {
    private Context context;
    public Controller(Context context){
        this.context=context;
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
}
