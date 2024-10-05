package com.edgesoftusa.wargame;

import java.util.ArrayList;
import java.util.List;

/**
 * A record representing a playing card with a suit, face value, and rank.
 */
public record Card(Suit suit, String face, int rank) {

    public int getRank() {
        return this.rank;
    }

    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE;

        public char getImage() {
            return (new char[]{9827, 9830, 9829, 9824})[this.ordinal()];
        }
    }

    @Override
    public String toString() {

        int index = face.equals("10") ? 2 : 1;
        String faceString = face.substring(0, index);
        return "%s%c(%d)".formatted(faceString, suit.getImage(), rank);
    }

    /**
     * Retrieves a numeric card based on the provided suit and card number.
     *
     * @param suit The suit of the card (CLUB, DIAMOND, HEART, SPADE).
     * @param cardNumber The number of the card, ranging from 2 to 10.
     * @return A Card object representing the numeric card, or null if an invalid card number is provided.
     */
    public static Card getNumericCard(Suit suit, int cardNumber) {

        if (cardNumber > 1 && cardNumber < 11) {
            return new Card(suit, String.valueOf(cardNumber), cardNumber - 2);
        }
        System.out.println("Invalid Numeric card selected");
        return null;
    }

    /**
     * Retrieves a face card based on the provided suit and abbreviation.
     *
     * @param suit The Suit of the card (CLUB, DIAMOND, HEART, SPADE).
     * @param abbrev The abbreviation of the face card (J, Q, K, A).
     * @return A Card object representing the face card, or null if an invalid face card abbreviation is provided.
     */
    public static Card getFaceCard(Suit suit, char abbrev) {

        int charIndex = "JQKA".indexOf(abbrev);
        if (charIndex > -1) {
            return new Card(suit, "" + abbrev, charIndex + 9);
        }
        System.out.println("Invalid Face card selected");
        return null;
    }

    /**
     * Retrieves a standard deck of playing cards with all numeric and face cards for each suit.
     *
     * @return A List of Card objects representing a standard deck of 52 playing cards, including numeric and face cards for each suit.
     */
    public static List<Card> getStandardDeck() {

        List<Card> deck = new ArrayList<>(52);
        for (Suit suit : Suit.values()) {
            for (int i = 2; i <= 10; i++) {
                deck.add(getNumericCard(suit, i));
            }
            for (char c : new char[]{'J', 'Q', 'K', 'A'}) {
                deck.add(getFaceCard(suit, c));
            }
        }
        return deck;
    }

    /**
     * Prints the specified deck of cards with the given description and rows.
     *
     * @param deck The list of cards to be printed.
     */
    public static void printDeck(List<Card> deck) {

        printDeck(deck, "Current Deck", 4);

    }

    /**
     * Prints the specified deck of cards with the given description and rows.
     *
     * @param deck The list of cards to be printed.
     * @param description The description of the deck to be displayed. Can be null.
     * @param rows The number of rows to print the cards in.
     */
    public static void printDeck(List<Card> deck, String description, int rows) {

        System.out.println("---------------------------");
        if (description != null) {
            System.out.println(description);
        }
        int cardsInRow = deck.size() / rows;
        for (int i = 0; i < rows; i++) {
            int startIndex = i * cardsInRow;
            int endIndex = startIndex + cardsInRow;
            deck.subList(startIndex, endIndex).forEach(c -> System.out.print(c + " "));
            System.out.println();
        }
    }

}