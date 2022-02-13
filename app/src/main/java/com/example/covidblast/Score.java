package com.example.covidblast;

import java.util.ArrayList;

public class Score {
    static ArrayList<Score> scores = new ArrayList<>();
    private final String userName, difficulty;
    private final int score;

    public Score(String userName, String difficulty, int score) {
        this.userName = userName;
        this.difficulty = difficulty;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public int getScore() {
        return score;
    }
}
