import java.util.*;
public class GameOfSticks {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to the Game of Sticks! \n");
		System.out.println("How many sticks are on the table initially? (10-100)?");
		int numSticks = scan.nextInt();
		while (numSticks < 10 || numSticks > 100) {
			System.out.println("Invalid Move! Try again.");
			numSticks = scan.nextInt();
		}
		int counter = 0;
		while (numSticks > 0) {
			if (numSticks == 1) {
				System.out.println("There is 1 stick on the board.");
			}
			else {
				System.out.println("There are " + numSticks + " sticks on the board.");
			}
		
			System.out.println("Player " + ((counter % 2) + 1) + " How many sticks do you take (1-3)?");
			
			
			int response = scan.nextInt();
			
			if ((numSticks-response)>=0 && (response <= 3) && (response >= 1)) {
				numSticks-=response;
			}
			else {
				System.out.println("Invalid Move! Try again.");
			}
			
			counter++;
		}
		System.out.println("Player " + (((counter + 1) % 2) + 1) + ", you lose.");
	}

}
