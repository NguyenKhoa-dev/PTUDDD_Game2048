package com.example.nhom10_laptrinhgame2048;

import java.io.Serializable;
import java.util.Date;

public class GameScore implements Serializable {
    private String name;
    private int score;

    public GameScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
