package com.example.covidblast;

enum Difficulties{EASY, MEDIUM, HARD, EXTREME}

public class Difficulty {
    private Difficulties currentDifficulty;
    private int virusAmount; //max amount of viruses on screen at the same time
    private int pointsPerCoin;

    public Difficulty(Difficulties currentDifficulty){
        setCurrentDifficulty(currentDifficulty);
        setVirusAmount();
    }

    public Difficulties getCurrentDifficulty() {
        return currentDifficulty;
    }

    public int getPointsPerCoin(){
        return this.pointsPerCoin;
    }

    public void setCurrentDifficulty(Difficulties currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    public int getVirusAmount() {
        return virusAmount;
    }

    public void setVirusAmount() {
        switch (getCurrentDifficulty()){
            case EASY:
                this.virusAmount = 3;
                this.pointsPerCoin = 1;
                break;
            case MEDIUM:
                this.virusAmount = 4;
                this.pointsPerCoin = 2;
                break;
            case HARD:
                this.virusAmount = 5;
                this.pointsPerCoin = 3;
                break;
            case EXTREME:
                this.virusAmount = 7;
                this.pointsPerCoin = 5;
                break;
        }
    }
}
