package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuScreenActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnClassic,btnHistory;
    SQLiteHelper helper;
    TextView txtHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        initDB();
        initControls();
    }

    private void initDB(){
        helper = new SQLiteHelper(this);
        helper.openDB();
        helper.createGameTrackingTable();
    }

    private void initControls() {
        btnClassic = findViewById(R.id.btnClassic);
        btnHistory = findViewById(R.id.btnHistory);
        if(helper.getTracking("SIZE 4").getMatrix().compareTo("")!=0){
            btnClassic.setText("Continue");
        }else{
            btnClassic.setText("NewGame");
        }
        btnClassic.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnClassic.getId()) {
            Intent intent = new Intent(MenuScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }else if(view == btnHistory){
            Intent intent = new Intent(MenuScreenActivity.this,HistoryActivity.class);
            startActivity(intent);
        }
    }
}