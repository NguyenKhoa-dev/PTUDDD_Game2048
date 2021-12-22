package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private GridView grdvGamePlay;
    private TextView txtMax, txtPoint;
    private OSoAdapter adapter;

    private View.OnTouchListener listener;
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
    }

    private void initGame() {
        DataGame.getDataGame().init(MainActivity.this);
        adapter = new OSoAdapter(MainActivity.this, 0, DataGame.getDataGame().getArrSo());
        setPointAndMax();

        listener = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xTouch = motionEvent.getX();
                        yTouch = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
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
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setData() {
        grdvGamePlay.setAdapter(adapter);
        grdvGamePlay.setOnTouchListener(listener);
    }

    private void setPointAndMax() {
        int point = DataGame.getDataGame().getPoint();
        int max = DataGame.getDataGame().getMax();
        txtPoint.setText("" + point);
        txtMax.setText("" + max);
    }
}