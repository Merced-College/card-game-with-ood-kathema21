// Katelynn Prater - OOD Lab
// 6/20/25
// This program allows you to play a modified pairwise version of Blackjack. You want to reach 21.
// Essentially, your score only increases if you draw a pair of cards with the same value.
// You can choose one or both cards to add to your score, and you discard accordingly.
//
// This is the card class with assessors, mutators, and other necessary methods.

public class Card {
    private String cardSuit;
    private String cardName;
    private int cardValue;
    private String cardPicture;

    public Card(String cardSuit, String cardName, int cardValue, String cardPicture) {
        this.cardSuit = cardSuit;
        this.cardName = cardName;
        this.cardValue = cardValue;
        this.cardPicture = cardPicture;
    }
    //constructor method


    @Override
    public String toString() {
        return cardName + " of " + cardSuit + "s: val of " + cardValue;

    }
    // toString method to print card details

    public boolean isSameCard(Card input) {
        return this.cardName.equals(input.cardName) && this.cardSuit.equals(input.cardSuit);
    }
    // checks if two cards are the same by name and suit

    public boolean isSameVal(Card input) {
        return this.cardValue == input.cardValue;
    }
    // checks if two cards have the same value

    // all the assessors and mutators for the card class from here down
    public int getCardValue() {
        return cardValue;
    }
    

    public String getCardSuit() {
        return cardSuit;
    }

    public String getCardPicture() {
        return cardPicture;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardSuit(String cardSuit) {
        this.cardSuit = cardSuit;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardValue(int cardValue) {
        this.cardValue = cardValue;
    }

    public void setCardPicture(String cardPicture) {
        this.cardPicture = cardPicture;
    }
}