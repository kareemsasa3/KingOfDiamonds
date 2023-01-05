package controller;

import model.Game;
import model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameLogic {

    private static Game game = new Game();

    private static Player cpu1 = new Player("CPU 1");
    private static Player cpu2 = new Player("CPU 2");
    private static Player cpu3 = new Player("CPU 3");
    private static Player cpu4 = new Player("CPU 4");
    private static Player user = new Player("USER");

    private static ArrayList<Player> players = new ArrayList<>();

    private static void addPlayersToPlayerList() {
        players.add(cpu1);
        players.add(cpu2);
        players.add(cpu3);
        players.add(cpu4);
        players.add(user);
    }

    private static void printCurrentRoundNumber() {
        System.out.println("-------------------------------");
        System.out.println("ROUND " + game.getCurrentRound());
        System.out.println("-------------------------------");
    }

    private static void printCurrentScores() {
        System.out.println("Current Score:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore());
        }
    }

    private static void generateGuessesForCPU() {
        for (Player player : players) {
            if (!player.getName().equals("USER")) {
                player.setCurrentGuess(getRandomNumber());
                System.out.println(player.getName() + " entered -- " + player.getCurrentGuess());
            }
        }
        System.out.println("\n");
    }

    private static void takeUserInputAsGuess() {
        System.out.println("Please enter a number within the range: [0-100]\n");
        Scanner scanner = new Scanner(System.in);
        int userInput = scanner.nextInt();
        System.out.println("You have entered -- " + userInput);
        user.setCurrentGuess(userInput);
    }

    private static void deductPointsFromLosers(Player roundWinner, ArrayList<Player> players) {
        ArrayList<Player> playersToRemove = new ArrayList<>();
        for (Player player : players) {
            if (player != roundWinner) {
                player.setScore(player.getScore()-1);
                if (player.getScore() <= -10) {
                    game.setPlayersRemaining(game.getPlayersRemaining()-1);
                    playersToRemove.add(player);
                }
            }
        }
        players.removeAll(playersToRemove);
    }

    private static void checkForLastManStanding() {
        if (players.size() == 1) {
            System.out.println("The King of Diamonds has ended.");
            System.out.println("The winner is " + players.get(0).getName());
            System.exit(0);
        }
    }

    public void play() {
        System.out.println("Welcome to the King of Diamonds\n");
        addPlayersToPlayerList();
        while (game.getPlayersRemaining() != 1) {
            printCurrentRoundNumber();
            printCurrentScores();
            takeUserInputAsGuess();
            generateGuessesForCPU();
            Double roundValue = calculateRoundValue(players);
            System.out.println("The average of all players numbers multiplied by 0.8 is (" +  String.format("%.2f", roundValue) + ")");
            Player roundWinner = calculateRoundWinner(players, roundValue);
            System.out.println("The winner of the round is -- " + roundWinner.getName());
            deductPointsFromLosers(roundWinner, players);
            checkForLastManStanding();
            game.setCurrentRound(game.getCurrentRound()+1);
        }
    }

    private static int getRandomNumber() {
        return (int) ((Math.random() * 100));
    }

    private static Double calculateRoundValue(List<Player> players) {
        int currentRoundValue = 0;
        for (Player player : players)
            currentRoundValue += player.getCurrentGuess();
        currentRoundValue = currentRoundValue / players.size();
        return currentRoundValue * 0.8;
    }

    private static Player calculateRoundWinner(List<Player> players, double roundValue) {
        List<Double> differences = new ArrayList<>();
        for (Player player : players) {
            double difference = 100.0;
            if (player.getCurrentGuess() > roundValue)
                difference = player.getCurrentGuess() - roundValue;
            else if (player.getCurrentGuess() < roundValue)
                difference = roundValue - player.getCurrentGuess();
            else if (player.getCurrentGuess() == roundValue)
                difference = 0.0;
            differences.add(difference);
        }
        double min = differences.get(0);
        int minIndex = 0;
        for (int i = 1; i < differences.size(); i++) {
            if (differences.get(i) < min) {
                min = differences.get(i);
                minIndex = i;
            }
        }
        return players.get(minIndex);
    }
}
