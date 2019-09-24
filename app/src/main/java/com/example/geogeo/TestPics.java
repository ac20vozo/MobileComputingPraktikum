package com.example.geogeo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TestPics extends Activity {
    ImageView image;
    Button button;
    int i;
    Controller con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_pics);

        button = findViewById(R.id.button2);
        image = findViewById(R.id.imageView);
        i = 1;
        con = new Controller((getApplicationContext()));
    }
    public void getpic(View view){
        con.showPic(image, i++);

    }
}
