package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayCustome extends AppCompatActivity implements View.OnClickListener {
    Button btn4x4,btn5x5,btn6x6,btn8x8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_custome);
        init();
    }
    private void init(){
        btn4x4 = findViewById(R.id.btn4x4);
        btn5x5 = findViewById(R.id.btn5x5);
        btn6x6 = findViewById(R.id.btn6x6);
        btn8x8 = findViewById(R.id.btn8x8);
        btn4x4.setOnClickListener(this);
        btn5x5.setOnClickListener(this);
        btn6x6.setOnClickListener(this);
        btn8x8.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btn4x4.getId()){
            Intent intent = new Intent(PlayCustome.this,MainActivity.class);
            intent.putExtra("size",4);
            startActivity(intent);
        } else if(view.getId() == btn5x5.getId()){
            Intent intent = new Intent(PlayCustome.this,MainActivity.class);
            intent.putExtra("size",5);
            startActivity(intent);
        } else if(view.getId() == btn6x6.getId()){
            Intent intent = new Intent(PlayCustome.this,MainActivity.class);
            intent.putExtra("size",6);
            startActivity(intent);
        }else if(view.getId() == btn8x8.getId()){
            Intent intent = new Intent(PlayCustome.this,MainActivity.class);
            intent.putExtra("size",8);
            startActivity(intent);
        }
    }
}