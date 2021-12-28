package com.example.nhom10_laptrinhgame2048;

import android.location.GnssAntennaInfo;

import java.io.Serializable;
import java.util.Date;

public class GameScore implements Comparable {
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

    @Override
    public int compareTo(Object o) {
        GameScore g = (GameScore) o;
        return this.name.compareTo(g.name);
    }
}
