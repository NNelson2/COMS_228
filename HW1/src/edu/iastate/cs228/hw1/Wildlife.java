package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *  
 * @author Noah Nelson
 *
 */

/**
 * 
 * The Wildlife class performs a simulation of a grid plain with
 * squares inhabited by badgers, foxes, rabbits, grass, or none. 
 *
 */
public class Wildlife {


	/**
	 * Update the new plain from the old plain in one cycle. 
	 * @param pOld  old plain
	 * @param pNew  new plain 
	 */
	public static void updatePlain(Plain pOld, Plain pNew) {
		for (int x = 0; x < pOld.grid.length; x++) {
			for (int y = 0; y < pOld.grid.length; y++) {
				pOld.grid[x][y].next(pNew);
			}
		}

	}
	
	/**
	 * Repeatedly generates plains either randomly or from reading files. 
	 * Over each plain, carries out an input number of cycles of evolution. 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws IOException {
		int input = 0;
		int currentTrial = 1;
		System.out.println("Simulation of Wildlife of the Plain");
		System.out.println("keys: 1 (random plain) 2 (file input) 3 (exit)\n");

		while (input != 3) {

			Scanner kb = new Scanner(System.in);
			System.out.print("trial " + currentTrial + ": ");
			input = kb.nextInt();

			if (input == 1) { // Random Plain
				int width = 0;
				int numRuns = 0;
				System.out.print("Enter the grid width: ");
				width = kb.nextInt();
				System.out.print("Enter the number of cycles: ");
				numRuns = kb.nextInt();
				System.out.println();
				System.out.println("Initial Plain:\n");
				Plain currentPlain = new Plain(width);
				Plain newPlain = new Plain(width);
				currentPlain.randomInit();
				System.out.println(currentPlain.toString());
				System.out.println();
				for (int i = 0; i < numRuns; i++) {
					newPlain = new Plain(currentPlain.getWidth());
					updatePlain(currentPlain , newPlain);
					currentPlain = newPlain;

				}
				System.out.println("Final Plain: ");
				System.out.println(currentPlain.toString());
				currentPlain.write("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW1/public/saveFile.txt");
				System.out.println();

			} else if (input == 2) { // Plain from a file
				int numRuns = 0;
				System.out.println("What is the file PathName ?");
				String fileName = kb.next();
				System.out.println();
				System.out.println("Enter the number of cycles: ");
				numRuns = kb.nextInt();
				System.out.println();
				System.out.println("Initial Plain:\n");
				Plain currentPlain = new Plain(fileName);
				Plain newPlain = new Plain(currentPlain.getWidth());
				System.out.println(currentPlain.toString());
				System.out.println();
				for (int i = 0; i < numRuns; i++) {
					newPlain = new Plain(currentPlain.getWidth());
					updatePlain(currentPlain , newPlain);
					currentPlain = newPlain;

				}
				System.out.println("Final Plain: ");
				System.out.println(currentPlain.toString());
				currentPlain.write("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW1/public/saveFile.txt");
				System.out.println();

			} else if (input == 3) { // ends program
				System.exit(0);

			}
			currentTrial++;
		}
	}
}
