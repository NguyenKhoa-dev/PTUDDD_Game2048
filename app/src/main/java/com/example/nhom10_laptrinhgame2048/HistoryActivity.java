package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

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
    }

    private void LoadListView(){
        lvScores = findViewById(R.id.lvScores);
        ListViewAdapter adapter = new ListViewAdapter(HistoryActivity.this,0,helper.getListScore("SIZE 4"));
        lvScores.setAdapter(adapter);
    }
}