package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class MenuScreenActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnClassic,btnHistory;
    SQLiteHelper helper;
    TextView txtHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        initDatabase();
        initControls();
        action();
    }

    private void initDatabase(){
        helper = new SQLiteHelper(this);
        helper.openDB();
        helper.createTable();
    }

    private void initControls() {
        txtHighScore = findViewById(R.id.txtHighScore);
        btnClassic = findViewById(R.id.btnClassic);
        btnHistory = findViewById(R.id.btnHistory);
        btnClassic.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
    }

    public void action(){
        txtHighScore.setText("High Score: "+helper.getHighestScore("SIZE 4"));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnClassic.getId()) {
            GameScore d= new GameScore("s",0);
            Intent intent = new Intent(MenuScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }else if(view == btnHistory){
            Intent intent = new Intent(MenuScreenActivity.this,HistoryActivity.class);
            startActivity(intent);
        }
    }
}