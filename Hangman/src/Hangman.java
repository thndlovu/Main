import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class Hangman {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("1 or 2 Players?");
		String players = keyboard.nextLine();
		String word;
		
		if(players.equals("1")) {
			Scanner scanner = new Scanner(new File("/Users/blacboy26/Desktop/hangman.words.txt"));
			
			List<String> words = new ArrayList<>();
			while(scanner.hasNext()) {
				words.add(scanner.nextLine()); //add words in file to ArrayList
			}
			
			Random rand = new Random();
			word = words.get(rand.nextInt(words.size())); //random word we pick from 0-size of ArrayList
		} else {
			System.out.println("Player 1, please enter your word: ");
			word = keyboard.nextLine();
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("Ready for player 2! Good luck!");
		}
		//System.out.println(word);		
		List<Character> playerGuesses = new ArrayList<>(); //List of characters
		
		int wrongCount = 0;
		while(true) { 
			printHangedMan(wrongCount);
			if(wrongCount >= 6) {
				System.out.println("You lose!");
				System.out.println("The word was: " + word);
				break;
			}
			
			printWordState(word, playerGuesses);
			if(!getPlayerGuess(keyboard, word, playerGuesses)) { //if letterGuess is not contained in word increment wrongCount
				wrongCount++;
			}
			if(printWordState(word, playerGuesses)) { //if player has won break since function returns true if # of correct characters = length of word
				System.out.println("You win!");
				break;
			}
			
			System.out.print("Please enter your guess for the word: ");
			if(keyboard.nextLine().equals(word)){ //if player guesses complete word
				System.out.println("You win!");
				break;
		} else {
			System.out.println("Incorrect Guess. Try Again!");
		}
	} 
}
		
	private static boolean printWordState(String word, List<Character> playerGuesses) {
		int correctCount = 0;
		for(int i = 0; i < word.length(); i++) {
			if(playerGuesses.contains(word.charAt(i))) { //if player has guessed letter print if not dash
				System.out.print(word.charAt(i)); //print letter player has correctly guessed
				correctCount++;
			} else {
				System.out.print("-");
			}
		}
		System.out.println();
		return (word.length() == correctCount);
		
	}
	
	private static boolean getPlayerGuess(Scanner keyboard, String word, List<Character> playerGuesses) {
		System.out.print("Please enter a letter: ");
		String letterGuess = keyboard.nextLine(); //Letter user enters
		playerGuesses.add(letterGuess.charAt(0)); //Put letterGuess in playerGuesses by only taking character at first index
		return word.contains(letterGuess);
	}
	
	private static void printHangedMan(Integer wrongCount) {
		System.out.println(" -------");
		System.out.println(" |     |");
		if(wrongCount >= 1) {
			System.out.println(" O");
		}
		if(wrongCount >= 2) {
			System.out.print("\\ "); //need to slashes becauses 1 is to escape
			if(wrongCount >= 3) {
				System.out.println("/");
			} else {
				System.out.println(""); //if wrongCount = 2
			}
		}
		
		if(wrongCount >= 4) {
			System.out.println(" |");
		}
		
		if(wrongCount >= 5) {
			System.out.print("/ "); //need to slashes becauses 1 is to escape
			if(wrongCount >= 6) {
				System.out.println("\\");
			} else {
				System.out.println(""); //if wrongCount = 2
			}
		}
	}
}
