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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SQLiteHelper extends SQLiteOpenHelper implements Serializable {
    private static String DB_Name = "score_game.db";
    private String tableName = "ScoreBoard";
    private String tableNameTracking = "GameContinue";
    public SQLiteDatabase myDB;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+tableName);
        onCreate(db);
    }

    public void updateHighScore(GameScore gs){
        myDB=getWritableDatabase();
        String query = String.format("insert or replace into %s(name,score) values('%s',%d);",tableName,gs.getName(),gs.getScore());
        myDB.execSQL(query);
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

    public ArrayList<GameScore> getListScore(){
        ArrayList<GameScore> list = new ArrayList<>();
        myDB=getReadableDatabase();
        String sql = "select * from "+tableName;
        Cursor cursor = myDB.rawQuery(sql,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                list.add(new GameScore(cursor.getString(0),cursor.getInt(1)));
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
        myDB=getWritableDatabase();
        String query = "create table if not exists "+tableName+" (name TEXT PRIMARY KEY, SCORE INTEGER);";
        myDB.execSQL(query);
        myDB.close();
    }

    //Tracking game
    public void createGameTrackingTable(){
        myDB=getWritableDatabase();
        String query = String.format("create table if not exists %s(name TEXT primary key, SCORE integer, MAX integer, MATRIX TEXT);",tableNameTracking);
        myDB.execSQL(query);
        myDB.close();
    }

    public void trackGameContinue(GameContinue gc){
        myDB=getWritableDatabase();
        String query = String.format("insert or replace into %s(name,score,max,matrix) values('%s',%d,%d,'%s');",tableNameTracking,gc.getName(),gc.getScore(),gc.getMax(),gc.getMatrix());
        myDB.execSQL(query);
        myDB.close();
    }

    public GameContinue getTracking(String type){
        myDB= getReadableDatabase();
        GameContinue gc = new GameContinue(type,0,0,"");
        try {
            String query = String.format("select * from %s where name = '%s'",tableNameTracking,type);
            Cursor cursor = myDB.rawQuery(query,null);
            if(cursor!=null){
                while (cursor.moveToNext()){
                    gc = new GameContinue(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3));
                }
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        myDB.close();
        return gc;
    }
}
