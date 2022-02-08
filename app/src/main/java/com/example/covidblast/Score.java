package com.example.covidblast;

public class Score {
    private String userName;
    private String difficulty;
    private int score;

    public Score(String userName, String difficulty, int score) {
        setUserName(userName);
        setDifficulty(difficulty);
        setScore(score);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
