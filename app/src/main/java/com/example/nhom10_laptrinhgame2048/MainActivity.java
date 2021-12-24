package com.example.nhom10_laptrinhgame2048;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private GridView grdvGamePlay;
    private TextView txtMax, txtPoint, txtCountDown;
    private OSoAdapter adapter;
    private CountDownTimer countDownTimer;
    private boolean timerRunning,checkIsTouch=false;
    private long time = 60000; //1 minute
    private Button btnNewGame, btnUndo,btnPause;
    private View.OnTouchListener touchListener;
    private View.OnClickListener clickListener;
    private SQLiteHelper helper;
    private float xTouch, yTouch;
    private int tempMax = 0;
    private final int soCot = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabase();
        initControls();
        initGame();
        setData();
    }

    private void initDatabase(){
        helper = new SQLiteHelper(this);
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
        DataGame.getDataGame().setSize(soCot);
        DataGame.getDataGame().init(MainActivity.this);
        adapter = new OSoAdapter(MainActivity.this, 0, DataGame.getDataGame().getArrSo(), soCot);
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
                        int[][] matrix = DataGame.getDataGame().getMatrix();
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
                        if(DataGame.getDataGame().checkGameOver() == false){
                            int point = DataGame.getDataGame().getPoint();
                            Toast.makeText(MainActivity.this, "Game Over\nScore : "+point, Toast.LENGTH_LONG).show();
                            GameScore gameScore = new GameScore("SIZE "+soCot,point);
                            helper.insert(gameScore);
                        }
                        break;
                }
                return true;
            }
        };

        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList arr = new ArrayList();
                if(v==btnNewGame){
                    DataGame.getDataGame().init(MainActivity.this);
                    arr = DataGame.getDataGame().getArrSo();
                }
                else if(v==btnUndo&&checkIsTouch){
                    int point = DataGame.getDataGame().getPoint();
                    if(point >100){
                        DataGame.getDataGame().getUndo();
                        arr = DataGame.getDataGame().getArrSo();
                        point-=100;
                        DataGame.getDataGame().setPoint(point);
                        checkIsTouch = false;
                    }
                    else {
                        return;
                    }
                }else{
                    return;
                }
                adapter = new OSoAdapter(MainActivity.this, 0, arr, soCot);
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
        grdvGamePlay.setNumColumns(soCot);
        grdvGamePlay.setAdapter(adapter);
        grdvGamePlay.setOnTouchListener(touchListener);
        btnNewGame.setOnClickListener(clickListener);
        btnUndo.setOnClickListener(clickListener);
    }

    private void setPointAndMax() {
        int point = DataGame.getDataGame().getPoint();
        int max = DataGame.getDataGame().getMax();
        txtPoint.setText("" + point);
        txtMax.setText("" + max);

        if (tempMax < max && max > 200) {
            showMilestoneDialog(max);
            tempMax = max;
        }
    }

    private void showMilestoneDialog(int MaxPoint) {
        final Dialog dlg = new Dialog(MainActivity.this);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_achievement_layout);
        dlg.setCancelable(false);

        Window window = dlg.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);

        TextView txtMaxPoint = dlg.findViewById(R.id.txtMaxPointAchievement);
        Button btnContinue = dlg.findViewById(R.id.btnContinue);

        txtMaxPoint.setText("" + MaxPoint);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });

        dlg.show();
    }
}