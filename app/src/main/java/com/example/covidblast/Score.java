package com.example.covidblast;

public class Score {
    private final String userName;
    private final String difficulty;
    private int score;

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

    public void setScore(int score) {
        this.score = score;
    }

}
