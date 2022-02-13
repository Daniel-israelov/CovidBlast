package com.example.covidblast;

enum Difficulties{EASY, MEDIUM, HARD, EXTREME}

public class Difficulty {
    private static Difficulty instance = null;
    private Difficulties currentDifficulty;

    private Difficulty(){}

    public static Difficulty getInstance() {
        if(instance == null)
            instance = new Difficulty();
        return instance;
    }

    public Difficulties getCurrentDifficulty() { return currentDifficulty; }

    public void setCurrentDifficulty(Difficulties currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

}
