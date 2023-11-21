package main;
import java.util.*;
public class GameOfSticks {
	
	private static ArrayList<ArrayList<Integer>> computerHats = new ArrayList<ArrayList<Integer>>(); //each hat has balls in it numbered 1-3
	private static ArrayList<IndexBall> AIGoodResponses = new ArrayList<IndexBall>();
	
	public static void main(String[] args) {
		System.out.println("Welcome to the Game of Sticks! \n");
		playerVSPlayer();
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
		

		for (int i=0; i<numSticks; i++) { //filling in
			computerHats.add(new ArrayList<Integer>());
		}
		for (int i=0; i<numSticks; i++) { //populating initial balls
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
				int AIResponse = computerHats.get(numSticks-1).get((int) (Math.random()*compNumIndices) - 1);
				AIGoodResponses.add(new IndexBall(numSticks, AIResponse));
				System.out.println("AI selects " + AIResponse);
				numSticks-=AIResponse;
			}
			counter++;
		}
		if ((((counter + 1) % 2) + 1) == 1) {
			IndexBall randGoodResponse1 = AIGoodResponses.get((int) (Math.random()*AIGoodResponses.size()-1));
			IndexBall randGoodResponse2 = AIGoodResponses.get((int) (Math.random()*AIGoodResponses.size()-1));
			computerHats.get(randGoodResponse1.getIndex()).add(randGoodResponse1.getBall());
			computerHats.get(randGoodResponse2.getIndex()).add(randGoodResponse2.getBall());
			System.out.println("You lose.");
			playAgain(scan);
		}
		else {
			AIGoodResponses = new ArrayList<IndexBall>(); 
			System.out.println("AI loses.");
			playAgain(scan);
		}
	}
	
	static void playAgain(Scanner scan) {
		System.out.println("Would you like to play again? (Y or N)");
		System.out.println("If playing the AI version, it will get better as it learns!");
		String repeat = scan.next();
		if (repeat.equals("Y")) {
			AIVSPlayer();
		}
	}
	
	static void printSticks(int numSticks) {
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
	
	static int invalidNumSticks(int numSticks, Scanner scan) {
		while (numSticks < 10 || numSticks > 100) {
			System.out.println("Invalid number of sticks! Try again.");
			numSticks = scan.nextInt();
		}
		return numSticks;
	}
}
