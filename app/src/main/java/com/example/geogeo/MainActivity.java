package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //DatabaseHandler db;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.im);

        //db = DatabaseHandler.getInstance(getApplicationContext());
        //db.open();
        //Controller con = new Controller(this);
        //con.bloßeintest02();
    }
    public void testfun(View view){
        Controller con = new Controller(this);
        con.showPic(image, 1);

    }
}
