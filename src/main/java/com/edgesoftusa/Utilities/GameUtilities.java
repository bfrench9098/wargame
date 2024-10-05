package com.edgesoftusa.Utilities;

import com.edgesoftusa.wargame.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for card game operations.
 */
public class GameUtilities {
    /**
     * Compares two Card objects based on their rank values.
     *
     * @param card1 The first Card object to compare.
     * @param card2 The second Card object to compare.
     * @return 1 if card1 rank is greater than card2 rank, 2 if card2 rank is greater than card1 rank,
     *   and 0 if ranks are equal.
     */
    public static int compareCards(Card card1, Card card2) {
        int result;

        // compare face value of card 1 and card 2
        if (card1.rank() > card2.rank()) {
            result = 1;
        } else if (card1.rank() < card2.rank()) {
            result = 2;
        } else {
            result = 0;
        }

        return result;
    }

    /**
     * Generates a new deck of cards by getting a standard deck, shuffling it, and returning the shuffled deck.
     *
     * @return A List of Card objects representing a newly generated deck of cards.
     */
    public static List<Card> generateNewDeck () {
        List<Card> deck = new ArrayList<Card>();

        deck.addAll(Card.getStandardDeck());

        Collections.shuffle(deck);

        return deck;
    }
}
