package com.example.nhom10_laptrinhgame2048;

import java.io.Serializable;
import java.util.Date;

public class GameScore implements Serializable {
    private String name;
    private int score;
    private Date date_created;

    public GameScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public GameScore(String name, int score, Date date_created){
        this.name=name;
        this.score = score;
        this.date_created = date_created;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Date getDate(){return date_created;}
}
