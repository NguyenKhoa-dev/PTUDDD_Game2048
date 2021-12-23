package com.example.nhom10_laptrinhgame2048;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    Context context;
    private static String DB_Name = "score_game.db";
    SQLiteDatabase myDB;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_Name, null, 1);
        this.context = context;
    }

    public void openDB()throws SQLException{
        if(myDB==null)
            myDB=getWritableDatabase();
    }

    public void closeDB(){
        if(myDB!=null){
            myDB.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CreateTable(){
        String query = "create table if not exists Score (name text PRIMARY KEY, password, text)";

    }
}
