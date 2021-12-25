package com.example.nhom10_laptrinhgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private GridView grdvGamePlay;
    private TextView txtHighScore, txtPoint, txtCountDown;
    private OSoAdapter adapter;
    private ImageButton btnHomeMain;

    private CountDownTimer countDownTimer;
    private boolean timerRunning,checkIsTouch=false;
    private long time = 60000; //1 minute
    private Button btnNewGame, btnUndo,btnPause;
    private View.OnTouchListener touchListener;
    private View.OnClickListener clickListener;
    private SQLiteHelper helper;

    private float xTouch, yTouch;
    private int tempMax = 0;
    private int soCot = 4;
    private int highScore;
    private String matrixString;

    MediaPlayer move_sound, achievement_sound, gameOver_sound;

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
        helper.openDB();
        helper.createTable();
        helper.createGameTrackingTable();
    }

    private void initControls() {
        grdvGamePlay = findViewById(R.id.gdvGamePlay);
        txtHighScore = findViewById(R.id.txtHighScore);
        txtPoint = findViewById(R.id.txtPoint);
        //txtCountDown = findViewById(R.id.txtCountDown);
        btnNewGame = findViewById(R.id.btnNewGame);
        btnUndo = findViewById(R.id.btnUndo);
        btnHomeMain = findViewById(R.id.btnHomeMain);

        btnHomeMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMenuScreen();
            }
        });
        btnPause = findViewById(R.id.btnPause);

        move_sound = MediaPlayer.create(MainActivity.this,R.raw.move_sound);
        achievement_sound = MediaPlayer.create(MainActivity.this,R.raw.achievement_sound);
        gameOver_sound = MediaPlayer.create(MainActivity.this, R.raw.game_over_sound);
    }

    private void initGame() {
        Bundle b = getIntent().getExtras();
        soCot = b.getInt("size");
        DataGame.getDataGame().setSize(soCot);
        DataGame.getDataGame().init(MainActivity.this);
        ArrayList arr = new ArrayList();
        GameContinue gc =  helper.getTracking(DataGame.getDataGame().NameOfGame(soCot));
        matrixString = gc.getMatrix();
        if(matrixString.compareTo("")!=0){
            DataGame.getDataGame().convertStringToMatrix(matrixString);
            DataGame.getDataGame().setPoint(gc.getScore());
            DataGame.getDataGame().setMax(gc.getMax());
        }else{
            DataGame.getDataGame().setPoint(0);
            DataGame.getDataGame().setMax(0);
        }
        arr = DataGame.getDataGame().getArrSo();
        adapter = new OSoAdapter(MainActivity.this, 0, arr, soCot);
        highScore = helper.getHighestScore(DataGame.getDataGame().NameOfGame(soCot));
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
                        //DataGame.getDataGame().saveUndo(matrix);
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
                        move_sound.start();
                        checkIsTouch = true;
                        setPointAndMax();
                        int point = DataGame.getDataGame().getPoint();
                        int max = DataGame.getDataGame().getMax();
                        helper.trackGameContinue(new GameContinue(DataGame.getDataGame().NameOfGame(soCot),point,max,DataGame.getDataGame().convertMatrixToString()));
                        if(DataGame.getDataGame().checkGameOver() == false){
                            int maxScore = helper.getHighestScore("SIZE "+soCot);
                            if(maxScore<point)
                                helper.updateHighScore(new GameScore("SIZE "+soCot,point));
                            helper.trackGameContinue(new GameContinue(DataGame.getDataGame().NameOfGame(soCot),0,0,""));
                            showGameOverDialog(point);
                            gameOver_sound.start();
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
                    helper.trackGameContinue(new GameContinue(DataGame.getDataGame().NameOfGame(soCot),0,0,""));
                    DataGame.getDataGame().setPoint(0);
                    DataGame.getDataGame().setMax(0);
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
        int point,max;
        point = DataGame.getDataGame().getPoint();
        max = DataGame.getDataGame().getMax();
        txtPoint.setText("" + point);

        if (point > highScore) {
            txtHighScore.setText("" + point);
        }
        else {
            txtHighScore.setText("" + highScore);
            helper.updateHighScore(new GameScore("SIZE "+soCot,highScore));
        }

        if (tempMax < max && max > 200) {
            showMilestoneDialog(max);
            tempMax = max;
            achievement_sound.start();
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

    private void showGameOverDialog(int point) {
        final Dialog dlg = new Dialog(MainActivity.this);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_game_over_layout);
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

        TextView txtScoreGameOver = dlg.findViewById(R.id.txtScoreGameOver);
        ImageButton btnHome = dlg.findViewById(R.id.btnHomeGameOver);
        ImageButton btnRestart = dlg.findViewById(R.id.btnRestartGameOver);

        txtScoreGameOver.setText("" + point);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMenuScreen();
                dlg.dismiss();
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataGame.getDataGame().init(MainActivity.this);
                adapter = new OSoAdapter(MainActivity.this, 0, DataGame.getDataGame().getArrSo(), soCot);
                adapter.notifyDataSetChanged();
                grdvGamePlay.setAdapter(adapter);
                helper.trackGameContinue(new GameContinue(DataGame.getDataGame().NameOfGame(soCot),0,0,""));
                DataGame.getDataGame().setPoint(0);
                DataGame.getDataGame().setMax(0);
                highScore = helper.getHighestScore(DataGame.getDataGame().NameOfGame(soCot));
                setPointAndMax();
                dlg.dismiss();
            }
        });
        dlg.show();
    }

    private void goToMenuScreen() {
        Intent intent = new Intent(MainActivity.this, MenuScreenActivity.class);
        startActivity(intent);
    }
}