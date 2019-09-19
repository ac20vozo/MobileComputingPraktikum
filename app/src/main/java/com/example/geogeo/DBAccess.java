//package com.example.geogeo;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DBAccess {
//    private SQLiteOpenHelper openHelper;
//    private SQLiteDatabase db;
//    private static DBAccess instance;
//
//    private DBAccess(Context context) {
//        this.openHelper = new DBHelper(context);
//    }
//
//
//    public static DBAccess getInstance(Context context) {
//        if (instance == null) {
//            instance = new DBAccess(context);
//        }
//        return instance;
//    }
//
//    public void open() {
//        this.db = openHelper.getWritableDatabase();
//    }
//
//    public void close() {
//        if (db != null) {
//            this.db.close();
//        }
//    }
//}
