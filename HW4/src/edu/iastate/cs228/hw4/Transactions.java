package edu.iastate.cs228.hw4;


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
 * The Transactions class simulates video transactions at a video store. 
 *
 */
public class Transactions 
{
	
	/**
	 * The main method generates a simulation of rental and return activities.  
	 *  
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws IOException, FilmNotInInventoryException, AllCopiesRentedOutException {
		// TODO 
		// 
		// 1. Construct a VideoStore object.
		// 2. Simulate transactions as in the example given in Section 4 of the 
		//    the project description.
		VideoStore blockBuster = new VideoStore("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
		blockBuster.bulkImport("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/moreFilms");
		int userInput = 0;
		String requestedFilm = "";
		String givenRentFile = "";
		String givenReturnFile = "";
		String options = "1 (rent)     2 (bulk rent)\n" +
				         "3 (return)   4 (bulk return)\n" +
				         "5 (summary)  6 (exit)\n";
		Scanner kb = new Scanner(System.in);
		System.out.println(options);
		while (userInput != 6) {
			System.out.print("Transaction: ");
			userInput = Integer.parseInt(kb.nextLine());
			System.out.println();

			if (userInput == 1) {
				System.out.print("Film to rent: ");
				requestedFilm = kb.nextLine();
				System.out.println();
				blockBuster.videoRent(requestedFilm, 1);
				// end of option 1
			}  else if (userInput == 2) {
				System.out.print("Video file (rent): ");
				givenRentFile = kb.nextLine();
				System.out.println();
				try {
					blockBuster.bulkRent(givenRentFile);

				} catch (FileNotFoundException e) {
					System.out.println("File invalid");
				}
				// end of option 2
			} else if (userInput == 3) {
				System.out.print("Film to return: ");
				requestedFilm = kb.nextLine();
				blockBuster.videoReturn(requestedFilm, 1);
				System.out.println();
				// end of option 3
			} else if (userInput == 4) {
				System.out.print("Video file (return): ");
				givenReturnFile = kb.nextLine();
				System.out.println();
				try {
					blockBuster.bulkReturn(givenReturnFile);

				} catch (FileNotFoundException e) {
					System.out.println("Invalid file");
				}
				// end of option 4
			} else if (userInput == 5) {
				System.out.println(blockBuster.transactionsSummary());

			} else if (userInput == 6) {
				break;
			} else {
				System.out.println("not a valid number");
			}

		}
	}
}
