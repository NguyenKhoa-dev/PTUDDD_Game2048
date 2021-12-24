package com.example.nhom10_laptrinhgame2048;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper implements Serializable {
    private static String DB_Name = "score_game.db";
    private String tableName = "ScoreBoard";
    SQLiteDatabase myDB;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String query = "create table if not exists "+tableName+" ( ID INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, SCORE INTEGER, created_date date default CURRENT_DATE);";
//        myDB.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+tableName);
        onCreate(db);
    }

    public void insert(GameScore gs){
        myDB=getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",gs.getName());
        contentValues.put("score",gs.getScore());
        myDB.insert("ScoreBoard",null,contentValues);
        myDB.close();
    }

    public int getHighestScore(String name){
        myDB=getReadableDatabase();
        String query = "select MAX(score) from "+tableName+" where name = '"+name+"';";
        Cursor cursor = myDB.rawQuery(query,null);
        cursor.moveToFirst();
        @SuppressLint("Range") int max = cursor.getInt(0);
        myDB.close();
        return max;
    }

    public ArrayList<GameScore> getListScore(String name){
        ArrayList<GameScore> list = new ArrayList<>();
        myDB=getReadableDatabase();
        String sql = "select * from "+tableName+" where name = '"+name+"';";
        Cursor cursor = myDB.rawQuery(sql,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                list.add(new GameScore(cursor.getString(1),cursor.getInt(2)));
            }
        }
        myDB.close();
        return list;
    }

    public void openDB()throws SQLException {
        if(myDB==null)
            myDB=getWritableDatabase();
    }

    public void createTable(){
        String query = "create table if not exists "+tableName+" ( ID INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, SCORE INTEGER, created_date date default CURRENT_DATE);";
        myDB.execSQL(query);
        myDB.close();
    }
}
