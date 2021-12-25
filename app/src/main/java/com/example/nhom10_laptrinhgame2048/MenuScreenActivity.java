package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuScreenActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnClassic,btnHistory, btnCustome;
    ImageButton btnVolume;
    SQLiteHelper helper;
    TextView txtHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        initDB();
        initControls();
        setSound();
    }

    private void initDB(){
        helper = new SQLiteHelper(this);
        helper.openDB();
        helper.createGameTrackingTable();
    }

    private void initControls() {
        btnClassic = findViewById(R.id.btnClassic);
        btnHistory = findViewById(R.id.btnHistory);
        btnVolume = findViewById(R.id.btnVolume);

        if(helper.getTracking("SIZE 4").getMatrix().compareTo("")!=0){
            btnClassic.setText("Continue");
        }else{
            btnClassic.setText("NewGame");
        }
        btnClassic.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnCustome = findViewById(R.id.btnCustome);
        btnCustome.setOnClickListener(this);
        btnVolume.setOnClickListener(this);
    }

    private void setSound() {
        if (DataGame.getDataGame().sound) {
            btnVolume.setImageResource(R.drawable.ic_volume_up);
        }
        else {
            btnVolume.setImageResource(R.drawable.ic_volume_off);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnVolume.getId()) {
            if (DataGame.getDataGame().sound) {
                btnVolume.setImageResource(R.drawable.ic_volume_off);
                DataGame.getDataGame().sound = false;
            }
            else {
                btnVolume.setImageResource(R.drawable.ic_volume_up);
                DataGame.getDataGame().sound = true;
            }
        }
        if (view.getId() == btnClassic.getId()) {
            Intent intent = new Intent(MenuScreenActivity.this, MainActivity.class);
            intent.putExtra("size",4);
            startActivity(intent);
        }else if(view.getId() == btnCustome.getId()){
            Intent intent = new Intent(MenuScreenActivity.this, PlayCustome.class);
            startActivity(intent);
        }else if(view == btnHistory){
            Intent intent = new Intent(MenuScreenActivity.this,HistoryActivity.class);
            startActivity(intent);
        }
    }
}