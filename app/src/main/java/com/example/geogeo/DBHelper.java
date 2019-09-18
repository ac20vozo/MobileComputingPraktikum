package com.example.geogeo;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {

    public static final String DBNAME = "mydb.sqlite3"; //<<<< must be same as file name
    public static final int DBVERSION = 1;

    public DBHelper(Context context) {
        super(context,DBNAME,null,DBVERSION);
    }
}