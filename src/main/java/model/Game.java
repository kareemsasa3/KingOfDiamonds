package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int playersRemaining;
    private int currentRound;
    private List<Integer> playerGuesses;
    private Player roundWinner;

    public Game() {
        setPlayersRemaining(5);
        setCurrentRound(1);
        this.playerGuesses = new ArrayList<>();
    }

    public int getPlayersRemaining() {
        return playersRemaining;
    }

    public void setPlayersRemaining(int playersRemaining) {
        this.playersRemaining = playersRemaining;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public List<Integer> getPlayerGuesses() {
        return playerGuesses;
    }

    public void setPlayerGuesses(List<Integer> playerGuesses) {
        this.playerGuesses = playerGuesses;
    }

    public Player getRoundWinner() {
        return roundWinner;
    }

    public void setRoundWinner(Player roundWinner) {
        this.roundWinner = roundWinner;
    }
}
