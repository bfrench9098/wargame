package com.edgesoftusa;

import com.edgesoftusa.wargame.Game;
import com.edgesoftusa.wargame.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);

        // prompt for player 1 name
        System.out.println("Enter player 1 name: ");
        String player1Name = myScanner.nextLine();

        // prompt for player 2 name
        System.out.println("Enter player 2 name: ");
        String player2Name = myScanner.nextLine();

        // make an array list of player1 and player2
        List<Player> players = new ArrayList<>();
        players.add(new Player(player1Name));
        players.add(new Player(player2Name));

        Game game = new Game(players);

        game.play();

    }
}