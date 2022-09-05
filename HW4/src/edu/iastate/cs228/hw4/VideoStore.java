package edu.iastate.cs228.hw4;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * 
 * @author Noah Nelson
 *
 */

public class VideoStore 
{
	public SplayTree<Video> inventory;     // all the videos at the store
	
	// ------------
	// Constructors 
	// ------------
	
    /**
     * Default constructor sets inventory to an empty tree. 
     */
    public VideoStore()
    {
    	// no need to implement. 
    }
    
    
	/**
	 * Constructor accepts a video file to create its inventory.  Refer to Section 3.2 of  
	 * the project description for details regarding the format of a video file. 
	 * 
	 * Calls setUpInventory(). 
	 * 
	 * @param videoFile  no format checking on the file
	 * @throws FileNotFoundException
	 */
    public VideoStore(String videoFile) throws FileNotFoundException  
    {
		inventory = new SplayTree<Video>();
		setUpInventory(videoFile);
    }
    
    
   /**
     * Accepts a video file to initialize the splay tree inventory.  To be efficient, 
     * add videos to the inventory by calling the addBST() method, which does not splay. 
     * 
     * Refer to Section 3.2 for the format of video file. 
     * 
     * @param  videoFile  correctly formated if exists
     * @throws FileNotFoundException 
     */
    public void setUpInventory(String videoFile) throws FileNotFoundException
    {
		File theFile = new File(videoFile);
		Scanner scanner = new Scanner(theFile);
		while(scanner.hasNextLine()) {
			String currentLine = scanner.nextLine();
			String videoName = parseFilmName(currentLine);
			int copies = parseNumCopies(currentLine);
			Video theVideo = new Video(videoName, copies);
			inventory.addBST(theVideo);
		}
    }
	
    
    // ------------------
    // Inventory Addition
    // ------------------
    
    /**
     * Find a Video object by film title. 
     * 
     * @param film
     * @return
     */
	public Video findVideo(String film) throws FilmNotInInventoryException {
		Video goal = new Video(film);
		Video result = inventory.findElement(goal);
		if (!Objects.equals(result.getFilm(), goal.getFilm())) {
			throw new FilmNotInInventoryException();
		} else {
			return result;
		}
	}


	/**
	 * Updates the splay tree inventory by adding a number of video copies of the film.  
	 * (Splaying is justified as new videos are more likely to be rented.) 
	 * 
	 * Calls the add() method of SplayTree to add the video object.  
	 * 
	 *     a) If true is returned, the film was not on the inventory before, and has been added.  
	 *     b) If false is returned, the film is already on the inventory. 
	 *     
	 * The root of the splay tree must store the corresponding Video object for the film. Update 
	 * the number of copies for the film.  
	 * 
	 * @param film  title of the film
	 * @param n     number of video copies 
	 */
	public void addVideo(String film, int n)  
	{
		Video movie = new Video(film, n);
		if (!inventory.add(movie)) {
			inventory.findEntry(movie).data.addNumCopies(n);
		}

	}
	

	/**
	 * Add one video copy of the film. 
	 * 
	 * @param film  title of the film
	 */
	public void addVideo(String film)
	{
		Video movie = new Video(film, 1);
		if (!inventory.add(movie)) {
			inventory.findEntry(movie).data.addNumCopies(1);
		}

	}
	

	/**
     * Update the splay trees inventory by adding videos.  Perform binary search additions by 
     * calling addBST() without splaying. 
     * 
     * The videoFile format is given in Section 3.2 of the project description. 
     * 
     * @param videoFile  correctly formated if exists 
     * @throws FileNotFoundException
     */
    public void bulkImport(String videoFile) throws FileNotFoundException 
    {
		File theFile = new File(videoFile);
		Scanner scanner = new Scanner(theFile);
		while(scanner.hasNextLine()) {
			String currentLine = scanner.nextLine();
			String videoName = parseFilmName(currentLine);
			int copies = parseNumCopies(currentLine);
			Video currentVideo = new Video(videoName, copies);
			if (!inventory.addBST(currentVideo)) {
				inventory.findEntry(currentVideo).data.addNumCopies(copies);
			}

		}
    }

    
    // ----------------------------
    // Video Query, Rental & Return 
    // ----------------------------
    
	/**
	 * Search the splay tree inventory to determine if a video is available. 
	 * 
	 * @param  film
	 * @return true if available
	 */
	public boolean available(String film) throws FilmNotInInventoryException {
		// TODO
		Video wantedVideo = new Video(film);
		Video foundVideo = inventory.findEntry(wantedVideo).data;
		if (!wantedVideo.getFilm().equals(foundVideo.getFilm())) {
			throw new FilmNotInInventoryException();
		} else if (foundVideo.getNumAvailableCopies() > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
     * Update inventory. 
     * 
     * Search if the film is in inventory by calling findElement(new Video(film, 1)). 
     * 
     * If the film is not in inventory, prints the message "Film <film> is not 
     * in inventory", where <film> shall be replaced with the string that is the value 
     * of the parameter film.  If the film is in inventory with no copy left, prints
     * the message "Film <film> has been rented out".
     * 
     * If there is at least one available copy but n is greater than the number of 
     * such copies, rent all available copies. In this case, no AllCopiesRentedOutException
     * is thrown.  
     * 
     * @param film   
     * @param n 
     * @throws IllegalArgumentException      if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException   if film is not in the inventory
	 * @throws AllCopiesRentedOutException   if there is zero available copy for the film.
	 */
	public void videoRent(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException,  
									     			 AllCopiesRentedOutException 
	{
		if (n <= 0 || film == null || film.isEmpty()) {
			throw new IllegalArgumentException();
		}
		Video wantedVideo = new Video(film);
		try {
			available(film);
			inventory.findEntry(wantedVideo).data.rentCopies(n);

		} catch (AllCopiesRentedOutException e) {
			System.out.println("Film " + wantedVideo.getFilm() + " has been rented out");
		} catch (FilmNotInInventoryException e) {
			System.out.println("Film " + wantedVideo.getFilm() + " is not in the inventory");
		}
	}

	
	/**
	 * Update inventory.
	 * 
	 *    1. Calls videoRent() repeatedly for every video listed in the file.  
	 *    2. For each requested video, do the following: 
	 *       a) If it is not in inventory or is rented out, an exception will be 
	 *          thrown from videoRent().  Based on the exception, prints out the following 
	 *          message: "Film <film> is not in inventory" or "Film <film> 
	 *          has been rented out." In the message, <film> shall be replaced with 
	 *          the name of the video. 
	 *       b) Otherwise, update the video record in the inventory.
	 * 
	 * For details on handling of multiple exceptions and message printing, please read Section 3.4 
	 * of the project description. 
	 *       
	 * @param videoFile  correctly formatted if exists
	 * @throws FileNotFoundException
     * @throws IllegalArgumentException     if the number of copies of any film is <= 0
	 * @throws FilmNotInInventoryException  if any film from the videoFile is not in the inventory 
	 * @throws AllCopiesRentedOutException  if there is zero available copy for some film in videoFile
	 */
	public void bulkRent(String videoFile) throws FileNotFoundException, IllegalArgumentException, 
												  FilmNotInInventoryException, AllCopiesRentedOutException 
	{
		ArrayList<Video> videos = new <Video>ArrayList();
		File theFile = new File(videoFile);
		Scanner scanner = new Scanner(theFile);
		while(scanner.hasNextLine()) {
			String currentLine = scanner.nextLine();
			String videoName = parseFilmName(currentLine);
			int copies = parseNumCopies(currentLine);

			if (copies <= 0) {
				throw new IllegalArgumentException();
			} else {
				Video theVideo = new Video(videoName, copies);
				videos.add(theVideo);
			}
		}

		for (int x = 0; x < videos.size(); x++) {
			videoRent(videos.get(x).getFilm(), videos.get(x).getNumCopies());
		}

	}

	
	/**
	 * Update inventory.
	 * 
	 * If n exceeds the number of rented video copies, accepts up to that number of rented copies
	 * while ignoring the extra copies. 
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException     if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException  if film is not in the inventory
	 */
	public void videoReturn(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException 
	{
		if (n <= 0 || film == null || film.isEmpty()) {
			throw new IllegalArgumentException();
		}
		Video returning = new Video(film);
		inventory.findEntry(returning).data.returnCopies(n);
	}
	
	
	/**
	 * Update inventory. 
	 * 
	 * Handles excessive returned copies of a film in the same way as videoReturn() does.  See Section 
	 * 3.4 of the project description on how to handle multiple exceptions. 
	 * 
	 * @param videoFile
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException    if the number of return copies of any film is <= 0
	 * @throws FilmNotInInventoryException if a film from videoFile is not in inventory
	 */
	public void bulkReturn(String videoFile) throws FileNotFoundException, IllegalArgumentException,
													FilmNotInInventoryException												
	{
		ArrayList<Video> returning = new <Video>ArrayList();
		File theFile = new File(videoFile);
		Scanner scanner = new Scanner(theFile);
		while(scanner.hasNextLine()) {
			String currentLine = scanner.nextLine();
			String videoName = currentLine.replaceAll("[(]" + "\\d" + "[)]", "");
			String stringNum = currentLine.replaceAll("\\D", "");
			Scanner stringScan = new Scanner(stringNum);
			if (!stringNum.isEmpty() && !stringNum.equals("1")) {
				int number = stringScan.nextInt();
				if ( number <= 0) {
					throw new IllegalArgumentException();
				}
				Video theVideo = new Video(videoName, number);
				returning.add(theVideo);

			} else {
				Video theVideo = new Video(videoName, 1);
				returning.add(theVideo);

			}
		}

		for (int x = 0; x < returning.size(); x++) {
			videoReturn(returning.get(x).getFilm(), returning.get(x).getNumCopies());
		}

	}
		
	

	// ------------------------
	// Methods without Splaying
	// ------------------------
		
	/**
	 * Performs inorder traversal on the splay tree inventory to list all the videos by film 
	 * title, whether rented or not.  Below is a sample string if printed out: 
	 * 
	 * 
	 * Films in inventory: 
	 * 
	 * A Streetcar Named Desire (1) 
	 * Brokeback Mountain (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Singin' in the Rain (2)
	 * Slumdog Millionaire (5) 
	 * Taxi Driver (1) 
	 * The Godfather (1) 
	 * 
	 * 
	 * @return
	 */
	public String inventoryList() {
		StringBuilder text = new StringBuilder();
		text.append("Films in inventory:\n\n");
		text.append(inventoryRec(inventory.root));

		return text.toString();
	}

	private String inventoryRec(SplayTree.Node node) {
		StringBuilder theString = new StringBuilder();
		if (node == null) {
			return "";
		}
		Video data = (Video) node.data;
		theString.append(data.getFilm()).append(" (").append(data.getNumCopies()).append(")\n");
		theString.append(inventoryRec(node.left));
		theString.append(inventoryRec(node.right));
		return theString.toString();
	}





	/**
	 * Calls rentedVideosList() and unrentedVideosList() sequentially.  For the string format, 
	 * see Transaction 5 in the sample simulation in Section 4 of the project description. 
	 *   
	 * @return 
	 */
	public String transactionsSummary()  {
		StringBuilder transaction = new StringBuilder();
		transaction.append(rentedVideosList() +"\n" + unrentedVideosList());
		return transaction.toString();
	}	
	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * Rented films: 
	 * 
	 * Brokeback Mountain (1)
	 * Forrest Gump (1) 
	 * Singin' in the Rain (2)
	 * The Godfather (1)
	 * 
	 * 
	 * @return
	 */
	public String rentedVideosList() {

		StringBuilder text = new StringBuilder();
		text.append("Rented films: \n\n");
		text.append(rentedRec(inventory.root));
		return text.toString();
	}

	private String rentedRec(SplayTree.Node node) {
		StringBuilder theString = new StringBuilder();
		if (node == null) {
			return "";
		}
		Video data = (Video) node.data;
		if (data.getNumRentedCopies() > 0) {
			theString.append(data.getFilm()).append(" (").append(data.getNumRentedCopies()).append(")\n");
		}
		theString.append(rentedRec(node.left));
		theString.append(rentedRec(node.right));
		return theString.toString();
	}

	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * Prints only the films that have unrented copies. 
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Films remaining in inventory:
	 * 
	 * A Streetcar Named Desire (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Slumdog Millionaire (4) 
	 * Taxi Driver (1) 
	 * 
	 * 
	 * @return
	 */
	public String unrentedVideosList()
	{
		StringBuilder text = new StringBuilder();
		text.append("Films remaining in inventory: \n\n");
		text.append(unrentedRec(inventory.root));
		return text.toString();
	}

	private String unrentedRec(SplayTree.Node node) {
		StringBuilder theString = new StringBuilder();
		if (node == null) {
			return "";
		}
		Video data = (Video) node.data;
		if (data.getNumAvailableCopies() > 0) {
			theString.append(data.getFilm()).append(" (").append(data.getNumAvailableCopies()).append(")\n");
		}
		theString.append(unrentedRec(node.left));
		theString.append(unrentedRec(node.right));
		return theString.toString();
	}




	
	/**
	 * Parse the film name from an input line. 
	 * 
	 * @param line
	 * @return
	 */
	public static String parseFilmName(String line) 
	{
		StringBuilder name = new StringBuilder();
		for (int x = 0; x < line.length(); x++) {
			if (line.charAt(x) == '(') {
				break;
			} else {
				name.append(line.charAt(x));
			}
		}
		return name.toString().trim();
	}
	
	
	/**
	 * Parse the number of copies from an input line. 
	 * 
	 * @param line
	 * @return
	 */
	public static int parseNumCopies(String line) 
	{
		String number = "";
		for (int x = 0; x < line.length(); x++) {
			if (line.charAt(x) == '(') {
				number = line.substring(x);
				break;
			}
		}
		number = number.replaceAll("[(]" , "" );
		number = number.replaceAll("[)]" , "" );
		number.trim();

		if (number.equals("") || Integer.parseInt(number) <= 0){
			return 1;
		} else {
			return Integer.parseInt(number);
		}
	}
}
