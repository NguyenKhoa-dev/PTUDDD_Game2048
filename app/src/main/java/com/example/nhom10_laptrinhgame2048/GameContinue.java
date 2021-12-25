package com.example.nhom10_laptrinhgame2048;

public class GameContinue extends GameScore{
    private String matrix;
    private int max;

    public GameContinue(String name, int score,int max, String matrix) {
        super(name, score);
        this.matrix = matrix;
        this.max =  max;
    }

    public String getMatrix(){return matrix;}

    public int getMax(){return max;}
}
