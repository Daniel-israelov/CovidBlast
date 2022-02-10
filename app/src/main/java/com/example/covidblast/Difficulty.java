package com.example.covidblast;

enum Difficulties{EASY, MEDIUM, HARD, EXTREME}

public class Difficulty {
    private static Difficulty instance = null;
    private Difficulties currentDifficulty;
    private int virusAmount; //max amount of viruses on screen at the same time
    private int pointsForKill;

    private Difficulty(){}

    public static Difficulty getInstance() {
        if(instance == null)
            instance = new Difficulty();
        return instance;
    }

    public Difficulties getCurrentDifficulty() {
        return currentDifficulty;
    }

    public int getPointsForKill(){
        return this.pointsForKill;
    }

    public void setCurrentDifficulty(Difficulties currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
        setVirusAmount();
    }

    public int getVirusAmount() {
        return virusAmount;
    }

    public void setVirusAmount() {
        switch (getCurrentDifficulty()){
            case EASY:
                this.virusAmount = 3;
                this.pointsForKill = 1;
                break;
            case MEDIUM:
                this.virusAmount = 4;
                this.pointsForKill = 2;
                break;
            case HARD:
                this.virusAmount = 5;
                this.pointsForKill = 3;
                break;
            case EXTREME:
                this.virusAmount = 7;
                this.pointsForKill = 5;
                break;
        }
    }
}
