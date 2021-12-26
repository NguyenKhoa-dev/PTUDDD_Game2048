package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    SQLiteHelper helper;
    ListView lvScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initDatabase();
        LoadListView();
    }

    private void initDatabase(){
        helper = new SQLiteHelper(this);
        helper.createTable();
    }

    private void LoadListView(){
        lvScores = findViewById(R.id.lvScores);
        List<GameScore> list = helper.getListScore();
        Collections.sort(list);
        ListViewAdapter adapter = new ListViewAdapter(HistoryActivity.this,0, list);
        lvScores.setAdapter(adapter);
    }
}