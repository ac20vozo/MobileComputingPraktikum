package com.example.geogeo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

public class Controller {
    private Context context;
    public Controller(Context context){
        this.context=context;
    }
    public void bloßeintest02() {
        DatabaseHandler db;
        db = DatabaseHandler.getInstance(context);
        db.open();
        System.out.println("Random Pic Question: " + db.getRandomPicQuestion());
    }
    public void showPic(byte[] bits, ImageView image, int questionId){
        DatabaseHandler db;
        db = DatabaseHandler.getInstance(context);
        db.open();
        bits = db.getPicQuestion(questionId);
        Bitmap b = BitmapFactory.decodeByteArray(bits, 0, bits.length);
        image.setImageBitmap(Bitmap.createScaledBitmap(b, 120, 120, false));
        db.close();

    }
    public void showText(String input, TextView text, int questionId){
        DatabaseHandler db;
        db = DatabaseHandler.getInstance(context);
        db.open();
        input = db.getTextQuestion(questionId);
        text.setText(input);
        db.close();

    }

    public void getitems(){
        DatabaseHandler db;
        db = DatabaseHandler.getInstance(context);
        db.open();
    }
}
