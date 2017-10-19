package com.example.user.crudwithretrofit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 10/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "Railway.db";
    public static final String tableName = "Trains";
    public static final String col1 = "ID";
    public static final String col2 = "Train_name";
    public static final String col3 = "Train_type";

    public DatabaseHelper(Context context) {
        super(context,databaseName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Train_name TEXT,Train_type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        onCreate(db);
    }

    public boolean insertData(String name,String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2,name);
        contentValues.put(col3,type);
        long result = db.insert(tableName,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+tableName,null);
        return res;
    }

    public boolean emptyCheck(){
        //boolean flag;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select count(*) from "+tableName,null);
        res.moveToFirst();
        int count = res.getInt(0);
        if (count > 0)
            return true;
        else
            return false;
    }

    public void deleteEntry(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = col2+" = '"+name+"'";

        db.delete(tableName,where,null);


    }

    public void updateEntry(String uname,String utype){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = col2+" = '"+uname+"'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2,uname);
        contentValues.put(col3,utype);
        db.update(tableName,contentValues,where,null);

    }
}
