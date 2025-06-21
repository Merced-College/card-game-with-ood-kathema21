// Katelynn Prater - OOD Lab
// 6/20/25
// This program allows you to play a modified pairwise version of Blackjack. You want to reach 21.
// Essentially, your score only increases if you draw a pair of cards with the same value.
// You can choose one or both cards to add to your score, and you discard accordingly.
//
// This is the CardGame class, containing game logic and where you play.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class CardGame {

	private static ArrayList<Card> deckOfCards = new ArrayList<Card>();
	private static ArrayList<Card> playerCards = new ArrayList<Card>();

	public static void main(String[] args) { // main method, where the game starts

		Scanner input = null;
		try {
			input = new Scanner(new File("cards.txt"));
		} catch (FileNotFoundException e) {
			// error
			System.out.println("error");
			e.printStackTrace();
		}
		// read in the cards from the file, checks for file not found

		while(input.hasNext()) {
			String[] fields  = input.nextLine().split(",");
			//	public Card(String cardSuit, String cardName, int cardValue, String cardPicture) {
			Card newCard = new Card(fields[0], fields[1].trim(),
					Integer.parseInt(fields[2].trim()), fields[3]);
			deckOfCards.add(newCard);	
		}
		// add cards to the deck from the file

		shuffle();
		// shuffle the deck of cards using shuffle method

		//for(Card c: deckOfCards)
			//System.out.println(c);
		
		playGame();
		//play the game using playGame method
	}

		
	public static void playGame() {
		//deal the player 5 cards
		boolean win = false;
		boolean bust = false;
		int tot = 0;
		// total value, trying to reach 21
		Scanner scnr = new Scanner(System.in);

		System.out.println("Starting game. Dealing 3 cards. The goal is to reach 21 using ONLY pair values.");
		System.out.println("Each time you find a pair, you may choose both values or one.");
		System.out.println("Each round you draw one card. Good luck!");
		System.out.println();

		for(int i = 0; i < 3; i++) {
			playerCards.add(deckOfCards.remove(0));
		}
		//deal 3 cards to the player
		
		while (!win && !bust) { // continue until the player wins or busts 
			System.out.println();
			System.out.println("Players Cards:");
			for(Card c: playerCards)
				System.out.println(c);

			int val = 0;
			boolean pairs = true; // flag to check if there are pairs
		

			if (checkFor2Kind()) {
				// if there are pairs, ask the user to select one or two values. uses checkFor2Kind method
				System.out.println();
				System.out.println("Your value: " + tot);
				System.out.println("You have a pair! Do you want to: ");
				System.out.println("1. Select one card value.");
				System.out.println("2. Select two card values. Enter the integer value.");
			
				boolean valid = false; 
				while (!valid) { // loop until a valid input is received, will throw an exception if not and try again
					try {
						val = scnr.nextInt();
						if (isValid(val)) {
							System.out.println("Selecting " + val);
							valid = true;
							//exits loop if input is valid
						}
						else {
							System.out.println("Invalid value. Input 1 or 2.");
						}
						// check if the input is valid using isValid method
					}
					catch (InputMismatchException e) {
						System.out.println("Input valid integer.");
						scnr.next();
						//exception if input is not an integer, will clear the scanner and try again
					}
				}
			}
			else {
				System.out.println("No pairs. Drawing new card.");
				System.out.println();
				playerCards.add(deckOfCards.remove(deckOfCards.size() - 1));
				pairs = false;
				// if no pairs, draw a new card
			}
			if (pairs) {
				if (val == 1) {
					tot += getSingleVal();
					// if the user selects one value, use getSingleVal method to get the value and adds to total
				}
				else {
					tot += getDoubleVal();
					// same here but two values, using getDoubleVal method
				}

				System.out.println("Current total: " + tot);

				if (tot == 21) {
					System.out.println("Congratulations! You won the game!");
					win = true;
					// if the total is 21, the player wins
				}
				else if (tot > 21) {
					System.out.println("Bust! Try again.");
					bust = true;
					// if the total is greater than 21, the player busts
				}
				else {
					playerCards.add(deckOfCards.remove(deckOfCards.size() - 1));
					// if the total is less than 21, draw a new card
				}
			} // end of if pairs
		} // end of while loop
	} // end of playGame method

	public static boolean isValid(int value) {
		return value == 1 || value == 2;
	} // end of isValid method
	
	public static void shuffle() { //shuffle the deck of cards using math.random()

		//shuffling the cards by deleting and reinserting
		for (int i = 0; i < deckOfCards.size(); i++) {
			int index = (int) (Math.random()*deckOfCards.size());
			Card c = deckOfCards.remove(index);
			//System.out.println("c is " + c + ", index is " + index);
			deckOfCards.add(c);
		} 
	}

	public static boolean checkFor2Kind() { // checks if there are two cards of the same value in the player's hand

		int count = 0;
		for(int i = 0; i < playerCards.size() - 1; i++) {
			Card current = playerCards.get(i);
			Card next = playerCards.get(i+1);
			//check the current card against the next card
			
			for(int j = i+1; j < playerCards.size(); j++) {
				next = playerCards.get(j);
				//System.out.println(" comparing " + current);
				//System.out.println(" to " + next);
				if(current.isSameVal(next))
					count++;
			}//end of inner for
			if(count == 1)
				return true;
				// if there is a pair, return true

		}//end outer for
		return false;
	} //end of checkFor2Kind method

	public static int getSingleVal() { // gets the value of a single card from a pair, removes it from the player's hand
        for (int i = 0; i < playerCards.size(); i++) {
            for (int j = i + 1; j < playerCards.size(); j++) {
                if (playerCards.get(i).getCardValue() == playerCards.get(j).getCardValue()) {
                    Card remove = playerCards.remove(j);
                    System.out.println("Used one card from pair: " + remove);
                    return remove.getCardValue();
                } //end inner if
            } //end inner for
        }// end outer for
		 return 0;
    } //end of getSingleVal method

    public static int getDoubleVal() { // gets the value of both cards from a pair, removes them from the player's hand
        for (int i = 0; i < playerCards.size(); i++) {
            for (int j = i + 1; j < playerCards.size(); j++) {
                if (playerCards.get(i).getCardValue() == playerCards.get(j).getCardValue()) {
                    int val = playerCards.get(i).getCardValue();
					playerCards.remove(j);
					playerCards.remove(i);
                    System.out.println("Used two cards from pair: Value = " + val);
                    return 2*val; // return double the value of the pair
                } //end inner if
            } //end inner for
        }// end outer for
		 return 0;
    } //end of getDoubleVal method
}
//end class
