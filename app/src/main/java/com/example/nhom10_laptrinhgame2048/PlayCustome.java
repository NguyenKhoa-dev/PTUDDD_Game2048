package com.example.nhom10_laptrinhgame2048;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class PlayCustome extends AppCompatActivity implements View.OnClickListener {
    Button btnnewgamecustom,btncontinuecustom;
    ImageButton btnhomemain;
    ImageView ivback,ivnext,imggame;
    TextView tvnamegame,tvhighscore;
    SQLiteHelper helper;
    int count,size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_custome);
        count=0;
        size=2;
        initDB();
        init();
    }

    private void init(){
        ivback = findViewById(R.id.ivBack);
        ivnext = findViewById(R.id.ivNext);
        imggame = findViewById(R.id.imgGame);
        tvnamegame = findViewById(R.id.tvNameGame);
        ivback.setOnClickListener(this);
        ivnext.setOnClickListener(this);

        btnhomemain= findViewById(R.id.btnHomeCustom);
        btnhomemain.setOnClickListener(this);

        btnnewgamecustom = findViewById(R.id.btnNewGameCustom);
        btnnewgamecustom.setOnClickListener(this);

        btncontinuecustom = findViewById(R.id.btnContinueCustom);
        btncontinuecustom.setOnClickListener(this);

        tvhighscore = findViewById(R.id.txtHighScoreCustom);
        tvhighscore.setText(""+helper.getHighestScore("SIZE "+size));

        if(helper.getTracking("SIZE "+size).getMatrix().compareTo("")!=0){
            btncontinuecustom.setVisibility(VISIBLE);
        }else{
            btncontinuecustom.setVisibility(INVISIBLE);
        }
        tvnamegame.setText(DataGame.getDataGame().arrName[count]);
        imggame.setImageResource(DataGame.getDataGame().arrImage[count]);
    }

    private void initDB(){
        helper = new SQLiteHelper(this);
        helper.openDB();
        helper.createTable();
        helper.createGameTrackingTable();
    }

    private void getCount(){
        if(count > DataGame.getDataGame().arrImage.length -1){
            count =0;
        }else if (count < 0){
            count = DataGame.getDataGame().arrImage.length -1;
        }
    }
    private int setSize(int count){
        int size = 0;
        switch (count){
            case 0:
                size = 2;
                break;
            case 1:
                size =4;
                break;
            case 2:
                size =5;
                break;
            case 3:
                size = 6;
                break;
            case 4:
                size =8;
                break;
        }
        return size;
    }
    @Override
    public void onClick(View view) {
        ArrayList arr = new ArrayList();
        if(view == ivback){
            count--;
        }
        if(view == ivnext){
            count++;
        }
        getCount();
        size = setSize(count);

        imggame.setImageResource(DataGame.getDataGame().arrImage[count]);
        tvnamegame.setText(DataGame.getDataGame().arrName[count]);

        if(helper.getTracking("SIZE "+size).getMatrix().compareTo("")!=0){
            btncontinuecustom.setVisibility(VISIBLE);
        }else{
            btncontinuecustom.setVisibility(INVISIBLE);
        }

        tvhighscore.setText(""+helper.getHighestScore("SIZE "+size));

        if(view == btncontinuecustom){
//            size = setSize(count);
            Intent intent = new Intent(PlayCustome.this,MainActivity.class);
            intent.putExtra("size",size);
            startActivity(intent);
        }
        if(view == btnnewgamecustom){
            helper.trackGameContinue(new GameContinue(DataGame.getDataGame().NameOfGame(size),0,0,""));
            Intent intent = new Intent(PlayCustome.this,MainActivity.class);
            intent.putExtra("size",size);
            startActivity(intent);
        }
        else if(view == btnhomemain){
            Intent intent = new Intent(PlayCustome.this,MenuScreenActivity.class);
            startActivity(intent);
        }
    }
}