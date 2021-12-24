package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuScreenActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnClassic,btnCustome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        initControls();
    }

    private void initControls() {
        btnClassic = findViewById(R.id.btnClassic);
        btnClassic.setOnClickListener(this);
        btnCustome = findViewById(R.id.btnCustome);
        btnCustome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnClassic.getId()) {
            Intent intent = new Intent(MenuScreenActivity.this, MainActivity.class);
            intent.putExtra("size",4);
            startActivity(intent);
        }else if(view.getId() == btnCustome.getId()){
            Intent intent = new Intent(MenuScreenActivity.this, PlayCustome.class);
            startActivity(intent);
        }
    }
}