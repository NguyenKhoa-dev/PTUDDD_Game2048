package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    SQLiteHelper helper;
    ListView lvScores;
    ImageButton btnhomemain;
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

        btnhomemain = findViewById(R.id.btnHomeMain);
        btnhomemain.setOnClickListener(this);
    }

    private void LoadListView(){
        lvScores = findViewById(R.id.lvScores);
        List<GameScore> list = helper.getListScore();
        Collections.sort(list);
        ListViewAdapter adapter = new ListViewAdapter(HistoryActivity.this,0, list);
        lvScores.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view == btnhomemain){
            Intent intent = new Intent(HistoryActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        }
    }
}