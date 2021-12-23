package com.example.nhom10_laptrinhgame2048;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class OVuong extends TextView {

    public OVuong(Context context) {
        super(context);
    }

    public OVuong(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OVuong(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dai = getMeasuredWidth();
        setMeasuredDimension(dai, dai);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    public void TextFormat(int so, int soCot) {
        if (so < 100) {
            setTextSize((4 * 40) / soCot);
        }
        else if (so < 1000) {
            setTextSize((4 * 35) / soCot);
        }
        else {
            setTextSize((4 * 30) / soCot);
        }
        if (so > 8) {
            setTextColor(Color.WHITE);
        }
        else {
            setTextColor(Color.BLACK);
        }
        GradientDrawable drawable = (GradientDrawable)this.getBackground();
        drawable.setColor(DataGame.getDataGame().colorr(so));
        setBackground(drawable);

        if (so == 0) {
            setText(" ");
        }
        else {
            setText("" + so);
        }
    }
}
