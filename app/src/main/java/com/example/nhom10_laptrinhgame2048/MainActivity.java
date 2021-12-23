package com.example.nhom10_laptrinhgame2048;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView grdvGamePlay;
    private TextView txtMax, txtPoint, txtCountDown;
    private OSoAdapter adapter;
    private Button btnNewGame,btnUndo,btnPause;
    private int point;
    private int max;
    private View.OnTouchListener touchListener;
    private View.OnClickListener clickListener;
    private float xTouch, yTouch;
    private CountDownTimer countDownTimer;
    private boolean timerRunning,checkIsTouch=false;
    private long time = 60000; //1 minute

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
        txtCountDown = findViewById(R.id.txtCountDown);
        btnNewGame = findViewById(R.id.btnNewGame);
        btnUndo = findViewById(R.id.btnUndo);
        btnPause = findViewById(R.id.btnPause);
    }

    private void initGame() {
        DataGame.getDataGame().init(MainActivity.this);
        adapter = new OSoAdapter(MainActivity.this, 0, DataGame.getDataGame().getArrSo());
        setPointAndMax();

        touchListener = new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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
                        checkIsTouch = true;
                        setPointAndMax();
//                        startStop();
                        break;
                }
                return true;
            }
        };

        clickListener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ArrayList arr = new ArrayList();
                if(v== btnNewGame){
                    DataGame.getDataGame().init(MainActivity.this);
                    arr = DataGame.getDataGame().getArrSo();
                }
                else if(v==btnUndo&&checkIsTouch){
                    if(point>100){
                        DataGame.getDataGame().getUndo();
                        arr = DataGame.getDataGame().getArrSo();
                        point-=100;
                        DataGame.getDataGame().setPoint(point);
                        checkIsTouch = false;
                    }
                }else{
                    return;
                }
                adapter = new OSoAdapter(MainActivity.this, 0, arr);
                adapter.notifyDataSetChanged();
                grdvGamePlay.setAdapter(adapter);
                setPointAndMax();
            }
        };
    }

    private void startStop() {
        if(timerRunning){
            startTimer();
        }else{
            stopTimer();
        }
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = 1;
                updateTime();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerRunning = true;
    }

    private void updateTime() {
        int minute = (int)time/6000;

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setData() {
        grdvGamePlay.setAdapter(adapter);
        grdvGamePlay.setOnTouchListener(touchListener);
        btnNewGame.setOnClickListener(clickListener);
        btnUndo.setOnClickListener(clickListener);

    }

    private void setPointAndMax() {
        point = DataGame.getDataGame().getPoint();
        max = DataGame.getDataGame().getMax();
        txtPoint.setText("" + point);
        txtMax.setText("" + max);
    }
}