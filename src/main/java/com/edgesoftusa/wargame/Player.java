package com.edgesoftusa.wargame;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in a card game.
 */
public class Player {
    private String name;
    private List<Card> hand;
    private int handsWon = 0;
    private int handsLost = 0;
    private boolean isWinner = false;

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public int getHandsWon() {
        return handsWon;
    }

    public int getHandsLost() {
        return handsLost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    /**
     * Adds a list of cards to the bottom of the player's hand.
     *
     * @param cards the list of cards to be added to the player's hand
     */
    public void addCards(List<Card> cards) {
        hand.addAll(cards);
    }

    /**
     * Draws a card from the top of the player's hand.
     *
     * @return the card removed from the top of the hand
     * @throws IllegalStateException if the hand is empty
     */
    public Card drawCardFromTop() {
        if (hand.isEmpty()) {
            throw new IllegalStateException("Hand is empty");
        }
        return hand.remove(0);
    }

    /**
     * Draws three cards from the top of the player's hand.
     *
     * @return a List containing the three cards drawn from the top of the hand
     * @throws IllegalStateException if there are less than three cards in the hand
     */
    public List<Card> drawThreeCardsFromTop() {
        if (hand.size() < 3) {
            throw new IllegalStateException("Not enough cards in hand to draw three cards");
        }
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            drawnCards.add(hand.remove(0));
        }
        return drawnCards;
    }

    /**
     * Clears the player's hand and returns a list of all cards previously in the hand.
     *
     * @return a List of all cards that were in the player's hand before it was cleared
     */
    public List<Card> returnAllCards() {
        List<Card> allCards = new ArrayList<>(hand);
        hand.clear();
        return allCards;
    }

    public int handSize() {
        return hand.size();
    }

    public void wonHand() {
        handsWon++;
    }

    public void lostHand() {
        handsLost++;
    }
}
