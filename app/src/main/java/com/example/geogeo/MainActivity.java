package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //DatabaseHandler db;
    ImageView image;
    Controller con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.im);
        con = new Controller(getApplicationContext());

        //db = DatabaseHandler.getInstance(getApplicationContext());
        //db.open();

    }
    public void testfun(View view){
        con.showPic(image, 1);
        con.test01();

    }
}
