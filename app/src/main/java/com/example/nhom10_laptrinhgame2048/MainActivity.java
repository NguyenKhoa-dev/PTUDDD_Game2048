package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView grdvGamePlay;
    private TextView txtMax, txtPoint;
    private OSoAdapter adapter;
    private Button btnMewGame,btnUndo;

    private View.OnTouchListener touchListener;
    private View.OnClickListener clickListener;
    private float xTouch, yTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
        initGame();
        setData();
    }

    private void initControls() {
        grdvGamePlay = findViewById(R.id.gdvGamePlay);
        txtMax = findViewById(R.id.txtMax);
        txtPoint = findViewById(R.id.txtPoint);
        btnMewGame = findViewById(R.id.btnNewGame);
        btnUndo = findViewById(R.id.btnUndo);
    }

    private void initGame() {
        DataGame.getDataGame().init(MainActivity.this);
        adapter = new OSoAdapter(MainActivity.this, 0, DataGame.getDataGame().getArrSo());
        setPointAndMax();

        touchListener = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xTouch = motionEvent.getX();
                        yTouch = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        int matrix[][] = DataGame.getDataGame().getMatrix();
                        DataGame.getDataGame().saveUndo(matrix);
                        if (Math.abs(motionEvent.getX() - xTouch) > Math.abs(motionEvent.getY() - yTouch)) {
                            if (motionEvent.getX() < xTouch) {
                                DataGame.getDataGame().vuotTrai();
                                adapter.notifyDataSetChanged();
                            }
                            else {
                                DataGame.getDataGame().vuotPhai();
                                adapter.notifyDataSetChanged();
                            }
                        }
                        else {
                            if (motionEvent.getY() < yTouch) {
                                DataGame.getDataGame().vuotLen();
                                adapter.notifyDataSetChanged();
                            }
                            else {
                                DataGame.getDataGame().vuotXuong();
                                adapter.notifyDataSetChanged();
                            }
                        }
                        setPointAndMax();
                        break;
                }
                return true;
            }
        };

        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList arr = new ArrayList();
                if(v==btnMewGame){
                    DataGame.getDataGame().init(MainActivity.this);
                    arr = DataGame.getDataGame().getArrSo();
                }
                else if(v==btnUndo){
//                    Toast.makeText(MainActivity.this,"Test undo",Toast.LENGTH_LONG).show();
                    DataGame.getDataGame().getUndo();
                    arr = DataGame.getDataGame().getArrSo();
                }
                adapter = new OSoAdapter(MainActivity.this, 0, arr);
                adapter.notifyDataSetChanged();
                grdvGamePlay.setAdapter(adapter);
                setPointAndMax();
            }
        };
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setData() {
        grdvGamePlay.setAdapter(adapter);
        grdvGamePlay.setOnTouchListener(touchListener);
        btnMewGame.setOnClickListener(clickListener);
        btnUndo.setOnClickListener(clickListener);
    }

    private void setPointAndMax() {
        int point = DataGame.getDataGame().getPoint();
        int max = DataGame.getDataGame().getMax();
        txtPoint.setText("" + point);
        txtMax.setText("" + max);
    }
}