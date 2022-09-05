package edu.iastate.cs228.hw2;

/**
 * 
 * @author Noah Nelson
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 *
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner {
	public Point[] points;
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;

	private String fileName;
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		points = pts;
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	public PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {
		fileName = inputFileName;
		File theFile = new File(fileName);
		sortingAlgorithm = algo;
		Scanner file = new Scanner(theFile);
		ArrayList <Integer> sizeOfArray = new <Integer> ArrayList();
		while (file.hasNext()) {
			if (file.hasNextInt()) {
				sizeOfArray.add(file.nextInt());
			} else {
				file.next();
			}

		}
		file.close();
		points = new Point[sizeOfArray.size() / 2];
		int[] x = new int[sizeOfArray.size() / 2];
		int[] y = new int[sizeOfArray.size() / 2];
		int hold = 0;
		for (int i = 0; i < sizeOfArray.size() / 2; i ++) {
			x[i] = sizeOfArray.get(hold);
			y[i] = sizeOfArray.get(hold + 1);
			hold += 2;
		}

		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(x[i], y[i]);
		}

	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param
	 * @return
	 */
	public void scan() {

		long startTime = 0;
		long time1 = 0;
		long time2 = 0;
		AbstractSorter aSorter;
		int xCord = 0;
		int yCord = 0;
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime.

		if (sortingAlgorithm.equals(Algorithm.MergeSort) ) {
			aSorter = new MergeSorter(points);

		} else if (sortingAlgorithm.equals(Algorithm.QuickSort)) {
			aSorter = new QuickSorter(points);

		} else if (sortingAlgorithm.equals(Algorithm.InsertionSort)) {
			aSorter = new InsertionSorter(points);

		} else {
			aSorter = new SelectionSorter(points);

		}
		aSorter.setComparator(0);
		startTime = System.nanoTime();
		aSorter.sort();
		time1 = System.nanoTime() - startTime;
		xCord = aSorter.getMedian().getX();
		aSorter.setComparator(1);
		startTime = System.nanoTime();
		aSorter.sort();
		time2 = System.nanoTime() - startTime;
		scanTime = time1 + time2;
		yCord = aSorter.getMedian().getY();
		medianCoordinatePoint = new Point(xCord, yCord);

		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats() {
		if (sortingAlgorithm == Algorithm.MergeSort || sortingAlgorithm == Algorithm.QuickSort) {
			String outPut = sortingAlgorithm.name() + "        " + points.length +  "        " + scanTime;
			return outPut;
		} else {
			String outPut = sortingAlgorithm.name() + "    " + points.length +  "        " + scanTime;
			return outPut;
		}
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString() {
		String median = medianCoordinatePoint.toString();
		return "MCP: " + median;

	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws IOException
	 */
	public void writeMCPToFile() throws IOException {
		Path path = Paths.get("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW2/src/edu/iastate/cs228/hw2/MCPFile.txt");
		Files.writeString(path, medianCoordinatePoint.toString(), StandardCharsets.UTF_8);

	}	

	

		
}
