package com.edgesoftusa.wargame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.edgesoftusa.Utilities.GameUtilities;

/**
 * Represents a card game where players compete against each other.
 * The game involves dealing cards to players, comparing card values,
 * and determining the winner of each hand.
 * Keeps track of the number of hands played and player statistics.
 */
public class Game {
    List<Player> players = new ArrayList<Player>();
    List<Card> startingDeck = new ArrayList<Card>();

    private int handsPlayed = 0;

    public int getHandsPlayed() {
        return handsPlayed;
    }

    public Game(List<Player> players) {
        this.players = players;
    }

    /**
     * Initiates and manages the gameplay of the card game.
     * This method performs the necessary steps for playing the game, including:
     * - Welcome message for players
     * - Generating and shuffling a new deck of cards
     * - Cutting the deck at a random position
     * - Dealing cards to players
     * - Continuously playing hands until the game is over
     * - Displaying player hands and game statistics
     */
    public void play() {
        boolean gameOver = false;

        System.out.println("\n");

        // welcome players
        for (Player player : players) {
            System.out.println("Welcome: " + player.getName() + "\n");
        }

        // get an already shuffled starting deck
        startingDeck = GameUtilities.generateNewDeck();

        Card.printDeck(startingDeck, "Starting Deck, BEFORE Cutting", 4);
        System.out.println();

        // split the starting deck at card x and put at bottom of starting deck
        // get random into between 10 and 24
        int x = new Random().nextInt(10, 25);

        List<Card> remainingDeck = new ArrayList<>(startingDeck.subList(0, x));

        startingDeck.addAll(remainingDeck);
        startingDeck.subList(0, x).clear();

        Card.printDeck(startingDeck, "Starting Deck, cut at card " + x, 4);
        System.out.println();

        // deal cards to players
        dealCards();

        while (!gameOver) {
            gameOver = playHand();
            showPlayerHands();
            handsPlayed++;
        }

        handsPlayed--;

        showStats();
    }

    /**
     * Deals a specified number of cards from the starting deck to each player.
     * Distributes the cards evenly among the players and sets their hands accordingly.
     * After dealing the cards, it calls the method to display player hands.
     */
    private void dealCards() {
        int numCardsPerPlayer = startingDeck.size() / players.size();

        for (int i = 0; i < players.size(); i++) {
            List<Card> playerHand = new ArrayList<>(startingDeck.subList(i * numCardsPerPlayer, (i + 1) * numCardsPerPlayer));
            players.get(i).setHand(playerHand);
        }

        showPlayerHands();
    }

    /**
     * Plays a hand of the card game among players.
     * The method simulates the gameplay by drawing cards for each player,
     * determining the outcome of the hand based on card comparison, handling WAR scenario,
     * updating player hands and statistics as necessary.
     *
     * @return true if the game is over after playing the hand, false otherwise
     */
    private boolean playHand() {
        boolean haveWinner = false;
        boolean gameOver = false;
        int result = 0;

        List<Card> hand = new ArrayList<>();
        List<Card> pile = new ArrayList<>();

        while (!haveWinner) {
            try {
                hand.add(players.get(0).drawCardFromTop());
            } catch (IllegalStateException e) {
                players.get(1).setWinner(true);
                if (hand.size() > 0) {
                    pile.addAll(hand);
                    hand.clear();
                }
                if (pile.size() > 0) {
                    players.get(1).addCards(pile);
                }
                if (players.get(0).handSize() > 0) {
                    players.get(1).addCards(players.get(0).returnAllCards());
                }
                System.out.println("Player " + players.get(0).getName() + " does not have enough cards to continue and has lost!\n");
                gameOver = true;
                return gameOver;
            }
            try {
                hand.add(players.get(1).drawCardFromTop());
            } catch (IllegalStateException e) {
                players.get(0).setWinner(true);
                if (hand.size() > 0) {
                    pile.addAll(hand);
                    hand.clear();
                }
                if (pile.size() > 0) {
                    players.get(0).addCards(pile);
                }
                if (players.get(1).handSize() > 0) {
                    players.get(0).addCards(players.get(1).returnAllCards());
                }
                System.out.println("Player " + players.get(1).getName() + " does not have enough cards to continue and has lost!\n");
                gameOver = true;
                return gameOver;
            }

            showCardsInPlay(hand);

            switch (GameUtilities.compareCards(hand.get(0), hand.get(1))) {
                case 1 -> {
                    haveWinner = true;
                    System.out.println("Player " + players.get(0).getName() + " won the hand!");
                    System.out.println();
                    players.get(0).wonHand();
                    players.get(1).lostHand();
                    players.get(0).addCards(hand);
                    hand.clear();
                    if (pile.size() > 0) {
                        players.get(0).addCards(pile);
                    }
                }
                case 2 -> {
                    haveWinner = true;
                    System.out.println("Player " + players.get(1).getName() + " won the hand!");
                    System.out.println();
                    players.get(1).wonHand();
                    players.get(0).lostHand();
                    players.get(1).addCards(hand);
                    hand.clear();
                    if (pile.size() > 0) {
                        players.get(1).addCards(pile);
                    }
                }
                default -> {
                    haveWinner = false;
                    System.out.println("\n *****  W A R *****\n");
                    try {
                        pile.addAll(players.get(0).drawThreeCardsFromTop());
                    } catch (IllegalStateException e) {
                        players.get(1).setWinner(true);
                        if (hand.size() > 0) {
                            pile.addAll(hand);
                            hand.clear();
                        }
                        if (pile.size() > 0) {
                            players.get(1).addCards(pile);
                        }
                        if (players.get(0).handSize() > 0) {
                            players.get(1).addCards(players.get(0).returnAllCards());
                        }
                        System.out.println("Player " + players.get(0).getName() + " does not have enough cards to continue and has lost!\n");
                        gameOver = true;
                        return gameOver;
                    }
                    try {
                        pile.addAll(players.get(1).drawThreeCardsFromTop());
                    } catch (IllegalStateException e) {
                        players.get(0).setWinner(true);
                        if (hand.size() > 0) {
                            pile.addAll(hand);
                            hand.clear();
                        }
                        if (pile.size() > 0) {
                            players.get(0).addCards(pile);
                        }
                        if (players.get(1).handSize() > 0) {
                            players.get(0).addCards(players.get(1).returnAllCards());
                        }
                        System.out.println("Player " + players.get(1).getName() + " does not have enough cards to continue and has lost!\n");
                        gameOver = true;
                        return gameOver;
                    }
                    pile.add(hand.remove(0));
                    pile.add(hand.remove(0));
                }
            }
        }

        return gameOver;
    }

    /**
     * Displays the hands of all players in the game.
     * Iterates through each player in the game and prints their hand of cards along with the player's name and hand size.
     */
    private void showPlayerHands() {
        for (Player player : players) {
            Card.printDeck(player.getHand(), player.getName() + " (" + player.handSize() + ")", 1);
            System.out.println("\n");
        }
    }

    /**
     * Displays the game statistics including total hands played, hands won and lost by each player,
     * and the name of the winner.
     */
    private void showStats() {
        System.out.println("Total hands played: " + handsPlayed);
        for (Player player : players) {
            System.out.println(player.getName() + " - Hands won: " + player.getHandsWon() + ", Hands lost: " + player.getHandsLost());
        }

        if (players.get(0).isWinner()) {
            System.out.println(players.get(0).getName() + " Is the Winner!!");
        } else {
            System.out.println(players.get(1).getName() + " Is the Winner!!");
        }
    }

    /**
     * Displays the cards in play for the current hand.
     *
     * @param hand the list of Card objects representing the current hand
     */
    private void showCardsInPlay(List<Card> hand) {
        System.out.println(players.get(0).getName() + "'s card " + hand.get(0));
        System.out.println(players.get(1).getName() + "'s card " + hand.get(1));
        System.out.println();
    }
}
