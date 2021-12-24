package com.example.nhom10_laptrinhgame2048;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView grdvGamePlay;
    private TextView txtMax, txtPoint;
    private OSoAdapter adapter;
    private Button btnNewGame, btnUndo;
    private ImageButton btnHomeMain;

    private View.OnTouchListener touchListener;
    private View.OnClickListener clickListener;

    private float xTouch, yTouch;
    private int tempMax = 0;
    private final int soCot = 4;
    private boolean checkIsTouch = true;

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
        btnNewGame = findViewById(R.id.btnNewGame);
        btnUndo = findViewById(R.id.btnUndo);
        btnHomeMain = findViewById(R.id.btnHomeMain);

        btnHomeMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMenuScreen();
            }
        });
    }

    private void initGame() {
        DataGame.getDataGame().setSize(soCot);
        DataGame.getDataGame().init(MainActivity.this);
        adapter = new OSoAdapter(MainActivity.this, 0, DataGame.getDataGame().getArrSo(), soCot);
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
                            showGameOverDialog(DataGame.getDataGame().getPoint());
                        }
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