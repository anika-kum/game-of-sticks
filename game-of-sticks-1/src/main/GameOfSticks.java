package main;
import java.util.*;
public class GameOfSticks {
	
	private static ArrayList<ArrayList<Integer>> computerHats = new ArrayList<ArrayList<Integer>>(); //each hat has balls in it numbered 1-3
	private static ArrayList<IndexBall> AIResponses = new ArrayList<IndexBall>();
	private static ArrayList<ArrayList<Integer>> trainedComputerHats1 = new ArrayList<ArrayList<Integer>>(); //for trained model
	private static ArrayList<IndexBall> AIResponses1 = new ArrayList<IndexBall>(); //for trained model
	private static ArrayList<ArrayList<Integer>> trainedComputerHats2 = new ArrayList<ArrayList<Integer>>(); //for trained model
	private static ArrayList<IndexBall> AIResponses2 = new ArrayList<IndexBall>(); //for trained model
	
	public static void main(String[] args) {
		System.out.println("Welcome to the Game of Sticks! \n");
		//options(new Scanner(System.in));
		AIVSAI();
		for (int i=0; i<trainedComputerHats1.size(); i++) {
			System.out.println("Index: " + i + " Balls:" + trainedComputerHats1.get(i));
		}
	}
	
	static void playerVSPlayer() {
		Scanner scan = new Scanner(System.in);
		System.out.println("How many sticks are on the table initially? (10-100)?");
		int numSticks = scan.nextInt();
		numSticks = invalidNumSticks(numSticks, scan);
		
		int counter = 0;
		while (numSticks > 0) {
			printSticks(numSticks);
			System.out.println("Player " + ((counter % 2) + 1) + ": How many sticks do you take (1-3)?");
			int response = scan.nextInt();
			numSticks -= invalidMove(response, numSticks, scan);
			counter++;
		}
		System.out.println("Player " + (((counter + 1) % 2) + 1) + ", you lose.");
		playAgain(scan);
	}
	
	static void AIVSPlayer() {
		Scanner scan = new Scanner(System.in);
		System.out.println("How many sticks are on the table initially? (10-100)?");
		int numSticks = scan.nextInt();
		numSticks = invalidNumSticks(numSticks, scan);
		

		for (int i=0; i<numSticks; i++) { //filling in initial outer level ArrayList
			computerHats.add(new ArrayList<Integer>());
		}
		
		for (int i=0; i<numSticks; i++) { //populating initial balls in inner ArrayList
			computerHats.get(i).add(1);
			computerHats.get(i).add(2);
			computerHats.get(i).add(3);
		}
		
		int counter = 0;
		while (numSticks > 0) {
			printSticks(numSticks);
			if (counter % 2 == 0) {
				System.out.println("Player 1: How many sticks do you take (1-3)?");
				int response = scan.nextInt();
				numSticks -= invalidMove(response, numSticks, scan);
			}
			else {
				int compNumIndices = computerHats.get(numSticks-1).size();
				int AIResponse = computerHats.get(numSticks-1).get((int) (Math.random()*compNumIndices));
				AIResponse = invalidAIMove(AIResponse, numSticks);
				AIResponses.add(new IndexBall(numSticks, AIResponse));
				System.out.println("AI selects " + AIResponse);
				numSticks-=AIResponse;
			}
			counter++;
		}
		if ((((counter + 1) % 2) + 1) == 1) { //if AI wins
			IndexBall randGoodResponse1 = AIResponses.get((int) (Math.random()*AIResponses.size()));
			IndexBall randGoodResponse2 = AIResponses.get((int) (Math.random()*AIResponses.size()));
			computerHats.get(randGoodResponse1.getIndex()).add(randGoodResponse1.getBall());
			computerHats.get(randGoodResponse2.getIndex()).add(randGoodResponse2.getBall());
			System.out.println("You lose.");
			playAgain(scan);
		}
		else {
			AIResponses = new ArrayList<IndexBall>(); 
			System.out.println("AI loses.");
			playAgain(scan);
		}
	}
	
	static void AIVSAI() {
		for (int i=0; i<100; i++) { //filling in initial outer level ArrayList
			trainedComputerHats1.add(new ArrayList<Integer>());
			trainedComputerHats2.add(new ArrayList<Integer>());
		}
		for (int i=0; i<100; i++) { //populating initial balls in inner ArrayList
			trainedComputerHats1.get(i).add(1);
			trainedComputerHats1.get(i).add(2);
			trainedComputerHats1.get(i).add(3);
			
			trainedComputerHats2.get(i).add(1);
			trainedComputerHats2.get(i).add(2);
			trainedComputerHats2.get(i).add(3);
		}
		for (int iteration = 0; iteration < 100000; iteration++) {
			int numSticks = (int) (Math.random()*90+10);
			int counter = 0;
			while (numSticks > 0) {
				if (counter % 2 == 0) {
					int compNumIndices1 = trainedComputerHats1.get(numSticks-1).size();
					int AIResponse1 = trainedComputerHats1.get(numSticks-1).get((int) (Math.random()*compNumIndices1));
					AIResponse1 = invalidAIMove(AIResponse1, numSticks);
					AIResponses1.add(new IndexBall(numSticks, AIResponse1));
					numSticks-=AIResponse1;
				}
				else {
					int compNumIndices2 = trainedComputerHats2.get(numSticks-1).size();
					int AIResponse2 = trainedComputerHats2.get(numSticks-1).get((int) (Math.random()*compNumIndices2));
					AIResponse2 = invalidAIMove(AIResponse2, numSticks);
					AIResponses2.add(new IndexBall(numSticks, AIResponse2));
					numSticks-=AIResponse2;
				}
				counter++;
			}
			if ((((counter + 1) % 2) + 1) == 1) { //if AI2 wins
				IndexBall randGoodResponse1Second = AIResponses2.get((int) (Math.random()*AIResponses2.size()));
				IndexBall randGoodResponse2Second = AIResponses2.get((int) (Math.random()*AIResponses2.size()));
				trainedComputerHats1.get(randGoodResponse1Second.getIndex()).add(randGoodResponse1Second.getBall());
				trainedComputerHats1.get(randGoodResponse2Second.getIndex()).add(randGoodResponse2Second.getBall());
			}
			else { //if AI1 wins
				IndexBall randGoodResponse1First = AIResponses1.get((int) (Math.random()*AIResponses1.size()));
				IndexBall randGoodResponse2First = AIResponses1.get((int) (Math.random()*AIResponses1.size()));
				trainedComputerHats1.get(randGoodResponse1First.getIndex()).add(randGoodResponse1First.getBall());
				trainedComputerHats1.get(randGoodResponse2First.getIndex()).add(randGoodResponse2First.getBall());
			}
		}
	}
	
	static void trainedAIVSPlayer() {
		
	}
	
	static void playAgain(Scanner scan) {
		System.out.println("Would you like to play again? (Y or N)");
		System.out.println("If playing the AI version, it will get better as it learns!");
		String repeat = scan.next();
		if (repeat.equals("Y") || repeat.equals("y")) {
			AIVSPlayer();
		}
	}
	
	static void printSticks(int numSticks) {
		System.out.println();
		if (numSticks == 1) {
			System.out.println("There is 1 stick on the board.");
		}
		else {
			System.out.println("There are " + numSticks + " sticks on the board.");
		}
	}
	
	static int invalidMove(int response, int numSticks, Scanner scan) {
		while (!((numSticks-response)>=0 && (response <= 3) && (response >= 1))) {
			System.out.println("Invalid Move! Try again.");
			response = scan.nextInt();
		}
		return response;
	}
	
	static int invalidAIMove(int AIResponse, int numSticks) {
		if (numSticks == 1) {
			return 1;
		}
		else if (numSticks == 2) {
			return 1;
		}
		else if (numSticks == 3) {
			return 2;
		}
		else {
			return AIResponse;
		}
	}
	
	static int invalidNumSticks(int numSticks, Scanner scan) {
		while (numSticks < 10 || numSticks > 100) {
			System.out.println("Invalid number of sticks! Try again.");
			numSticks = scan.nextInt();
		}
		return numSticks;
	}
	static void options(Scanner scan) {
		System.out.println("Options");
		System.out.println("Play against a friend (1)\n" + "Play against the computer (2)\n" + "Play against the trained computer (3)");
		System.out.println("Which option do you take (1-3)?");
		int option = scan.nextInt();
		if (option == 1) {
			playerVSPlayer();
		}
		else if (option == 2) {
			AIVSPlayer();
		}
		else if (option == 3) {
			System.out.println("Training AI, please wait...");
			System.out.println();
			trainedAIVSPlayer();
		}
		else {
			System.out.println("Invalid option! Try again.");
			invalidOptions(scan);
		} 
;	}
	
	static void invalidOptions(Scanner scan) {
		int option = scan.nextInt();
		if (option == 1) {
			playerVSPlayer();
		}
		else if (option == 2) {
			AIVSPlayer();
		}
		else if (option == 3) {
			System.out.println("Training AI, please wait...");
			System.out.println();
			trainedAIVSPlayer();
		}
		else {
			System.out.println("Invalid option! Try again.");
			invalidOptions(scan);
		} 
	}
}
