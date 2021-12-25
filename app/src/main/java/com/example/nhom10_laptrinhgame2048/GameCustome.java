package com.example.nhom10_laptrinhgame2048;

import android.graphics.Bitmap;

public class GameCustome {
    private String name;
    private Bitmap image;
    private String matrix;

    public GameCustome(String name, Bitmap image, String matrix) {
        this.name = name;
        this.image = image;
        this.matrix = matrix;
    }
    public GameCustome(){

    }
}
