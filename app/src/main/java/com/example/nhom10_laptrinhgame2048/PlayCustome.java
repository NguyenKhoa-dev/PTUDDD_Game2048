package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayCustome extends AppCompatActivity implements View.OnClickListener {
    Button btn4x4,btn5x5,btn6x6,btn8x8,btnnewgamecustom;
    ImageButton btnhomemain;
    ImageView ivback,ivnext,imggame;
    TextView tvnamegame;
    int count,size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_custome);
        count=0;
        size=4;
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
                size =2;
                break;
            case 1:
                size =5;
                break;
            case 2:
                size = 6;
                break;
            case 3:
                size =8;
        }
        return size;
    }
    @Override
    public void onClick(View view) {
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

        if(view == btnnewgamecustom){
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