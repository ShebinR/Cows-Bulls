import java.util.*;
import java.io.*;

class Game {
	int number;
	String playerName;
	HashMap<Integer, Integer> map;

	public void initGame() {
		int option = 0;
		while(true) {
			gameMenu();
			Scanner s = new Scanner(System.in);
			System.out.print("Enter an Option : ");
			option = s.nextInt();
			System.out.println();
			if(option == 1)
				startGame();
			else if(option == 2)
				showRules("./Rules.txt");
			else if(option == 0)
				break;
			System.out.println();
		}
	}

	public void clearConsole() {
		System.out.print("\033[H\033[2J");  
    		System.out.flush();
	}

	public void clearLine() {
		System.out.print(String.format("\033[%dA",1)); // Move up
		System.out.print("\033[2K");
	}

	public void startGame() {
		Scanner in = new Scanner(System.in);
		System.out.println("Game starts....!!");
		System.out.println();
		System.out.print("Enter your name : ");
		playerName = in.nextLine();
		this.number = generateRandomNumber();
		//System.out.println("RN : " + this.number);
		generateMap();
		int score = startGuessing();
	}

	public void showRules(String fileName) {
		clearConsole();
		System.out.println();
		try {
			Scanner in = new Scanner(new File(fileName));
			while(in.hasNext()) {
				String line = in.nextLine();
				System.out.println(line);
			}
			in.close();
		} catch(IOException e) {
			System.out.println(e);
		}
		System.out.println();
	}

	public void generateMap() {
		map = new HashMap<Integer, Integer>();
		int curr = number;
		int digit = 4;
		while(curr != 0) {
			map.put(digit, curr % 10);
			curr = curr / 10;
			digit--;
		}
		//System.out.println(map);
	}

	public int startGuessing() {
		int cows = 0, bulls = 0;
		Scanner in = new Scanner(System.in);
		int count = 0;
		printHeader();
		while(bulls != 4) {
			System.out.print("Guess : ");
			int guess = in.nextInt();
			count++;
			
			cows = countCows(guess);
			bulls = countBulls(guess);
			//System.out.println("Cows : " + cows);
			//System.out.println("Bulls : " + bulls);
			printResult(guess, cows, bulls);
			if(bulls == 4) {
				printCloseLine();
				System.out.println("Congrats, You won!! You are a guessing genius, " + playerName + "!!");
				break;
			}
			if(count > 10) {
				printCloseLine();
				System.out.println("You loose! You should have guess by now!");
				System.out.println("Number to guess : " + number);
				break;
			}
		}
		return count;
	}

	public int countBulls(int guess) {
		int bulls = 0;
		int digitPlace = 4;
		while(guess != 0) {
			int curr = guess % 10;
			for(Integer place: map.keySet()) {
				if(map.get(place) == curr && place == digitPlace) {
					bulls++;
					break;
				}
			}
			guess = guess / 10;
			digitPlace--;
		}
		return bulls;
	}

	public int countCows(int guess) {
		Collection<Integer> digits = map.values();
		int cows = 0;
		while(guess != 0) {
			int digit = guess % 10;
			if(digits.contains(digit))
				cows++;
			guess = guess / 10;
		}
		return cows;
	}

	public int generateRandomNumber() {
		Random r = new Random();
		int low = 1000;
		int high = 9999;
		return r.nextInt(high - low) + low;
	}

	public void gameMenu() {
		System.out.println("*** Cows & Bulls Game ***");
		System.out.println("--------------------------");
		System.out.println("1. New Game");
		System.out.println("2. Game Rules");
		System.out.println("0. Exit");
	}

	public void printHeader() {
		System.out.println("-------------------------------------------------");
		System.out.println("|	Guess	|	Cows	|	Bulls	|");
		System.out.println("-------------------------------------------------");
		System.out.println("|		|		|		|");
	}

	public void printCloseLine() {
		clearLine();
		System.out.println("-------------------------------------------------");
	}

	public void printResult(int guess, int cows, int bulls) {
		clearLine();
		clearLine();
		System.out.println("|	" + guess + "	|	" + cows + "	|	" + bulls + "	|");
		System.out.println("");
	}

	public static void main(String args[]) {
		Game g = new Game();
		g.clearConsole();
		g.initGame();
	}
}
