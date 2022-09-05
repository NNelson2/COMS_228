package edu.iastate.cs228.hw1;

/**
 *  
 * @author Noah Nelson
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random; 

/**
 * 
 * The plain is represented as a square grid of size width x width. 
 *
 */
public class Plain {
	private int width; // grid size: width X width 
	
	public Living[][] grid; 
	
	/**
	 *  Default constructor reads from a file 
	 */
	public Plain(String inputFileName) throws FileNotFoundException {
		File file0 = new File(inputFileName);
		width = 0;
		String widthFinder = " ";

		Scanner sc0 = new Scanner(file0);
		while(sc0.hasNextLine()) { // find the first line of the plain for find the info needed for the width
			widthFinder = sc0.nextLine();
			break;
		}
		sc0.close();

		for (int x = 0; x < widthFinder.length(); x++) { // finds the width needed
			if (Character.isLetter(widthFinder.charAt(x))) {
				width++;

			}
		}

		File file = new File(inputFileName);
		grid = new Living[width][width];
		Scanner sc = new Scanner(file);
		int currentRow = 0;

		String currentLine = "";
		while (sc.hasNextLine()) {
			int currentCol = 0;
			currentLine = sc.nextLine();
			for (int x = 0; x < currentLine.length(); x++) {
				if (Character.isLetter(currentLine.charAt(x))) {
					if (currentLine.charAt(x) == 'B') {
						grid[currentRow][currentCol] = new Badger(this, currentRow, currentCol,  Character.getNumericValue(currentLine.charAt(x+1)));

					} else if (currentLine.charAt(x) == 'F') {
						grid[currentRow][currentCol] = new Fox(this, currentRow, currentCol,  Character.getNumericValue(currentLine.charAt(x+1)));

					} else if (currentLine.charAt(x) == 'R') {
						grid[currentRow][currentCol] = new Rabbit(this, currentRow, currentCol,  Character.getNumericValue(currentLine.charAt(x+1)));

					} else if (currentLine.charAt(x) == 'E') {
						grid[currentRow][currentCol] = new Empty(this, currentRow, currentCol);

					} else if (currentLine.charAt(x) == 'G') {
						grid[currentRow][currentCol] = new Grass(this, currentRow, currentCol);

					}
				}
				if (!Character.isDigit(currentLine.charAt(x)) && !Character.isSpaceChar(currentLine.charAt(x))) {
					currentCol++;

				}

			}
			currentRow++;
		}
		sc.close();
	}
	
	/**
	 * Constructor that builds a w x w grid without initializing it. 
	 * @param w  the grid width
	 */
	public Plain(int w) {
		width = w;
		grid = new Living[width][width];

	}
	
	
	public int getWidth() {
		return width;
	}
	
	/**
	 * Initialize the plain by randomly assigning to every square of the grid  
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.  
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit() {
		Random generator = new Random(); 
		 for (int x = 0; x < width; x++) {
		 	for (int y = 0; y <width; y++) {
				int picked = generator.nextInt(5);
				if (picked == 0) {
					grid[x][y] = new Badger(this, x, y, 0);

				} else if (picked == 1) {
					grid[x][y] = new Empty(this, x, y);

				} else if (picked == 2) {
					grid[x][y] = new Fox(this, x, y, 0);

				} else if (picked == 3) {
					grid[x][y] = new Grass(this, x, y);

				} else {
					grid[x][y] = new Rabbit(this, x, y, 0);

				}
			}
		}
	}
	
	
	/**
	 * Output the plain grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.  
	 */
	public String toString() {
		// TODO
		StringBuilder gridToShow = new StringBuilder(new String());
		for (int x = 0; x < width; x++){
			if (grid[0][x].who() == State.BADGER) {
				gridToShow.append("B");

			} else if (grid[0][x].who() == State.EMPTY) {
				gridToShow.append("E");

			} else if (grid[0][x].who() == State.FOX) {
				gridToShow.append("F");

			} else if (grid[0][x].who() == State.GRASS) {
				gridToShow.append("G");

			} else if (grid[0][x].who() == State.RABBIT) {
				gridToShow.append("R");

			}

			if (grid[0][x].who() == State.BADGER) {
				Animal badger = (Animal) grid[0][x];
				gridToShow.append(badger.age);

			} else if (grid[0][x].who() == State.EMPTY) {
				gridToShow.append(" ");

			} else if (grid[0][x].who() == State.FOX) {
				Animal fox = (Animal) grid[0][x];
				gridToShow.append(fox.age);

			} else if (grid[0][x].who() == State.GRASS) {
				gridToShow.append(" ");

			} else if (grid[0][x].who() == State.RABBIT) {
				Animal rabit = (Animal) grid[0][x];
				gridToShow.append(rabit.age);

			}
			gridToShow.append(" ");
		}


		for (int x = 1; x < width; x++) {
			gridToShow.append("\n");
			for (int y = 0; y < width; y++) {
				if (grid[x][y].who() == State.BADGER) {
					gridToShow.append("B");

				} else if (grid[x][y].who() == State.EMPTY) {
					gridToShow.append("E");

				} else if (grid[x][y].who() == State.FOX) {
					gridToShow.append("F");

				} else if (grid[x][y].who() == State.GRASS) {
					gridToShow.append("G");

				} else if (grid[x][y].who() == State.RABBIT) {
					gridToShow.append("R");

				}

				if (grid[x][y].who() == State.BADGER) {
					Animal badger = (Animal) grid[x][y];
					gridToShow.append(badger.age);

				} else if (grid[x][y].who() == State.EMPTY) {
					gridToShow.append(" ");

				} else if (grid[x][y].who() == State.FOX) {
					Animal fox = (Animal) grid[x][y];
					gridToShow.append(fox.age);

				} else if (grid[x][y].who() == State.GRASS) {
					gridToShow.append(" ");

				} else if (grid[x][y].who() == State.RABBIT) {
					Animal rabit = (Animal) grid[x][y];
					gridToShow.append(rabit.age);

				}
				gridToShow.append(" ");
			}
		}
		return gridToShow.toString();
	}
	

	/**
	 * Write the plain grid to an output file.  Also useful for saving a randomly 
	 * generated plain for debugging purpose. 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws IOException {
		try {
			FileWriter file = new FileWriter(outputFileName);
			String save = this.toString();
			file.write(save);
			file.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();

		}
	}			
}
