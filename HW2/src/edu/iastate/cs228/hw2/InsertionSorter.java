package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Noah Nelson
 *
 */

/**
 * 
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter 
{
	// Other private instance variables if you need ... 
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 * 
	 * @param pts  
	 */
	public InsertionSorter(Point[] pts) {
		super(pts);
		algorithm = Algorithm.InsertionSort.name();

	}	

	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 */
	@Override
	public void sort() {
		for (int x = 1; x < getPoints().length; x++) {
			for (int y = x; y >= 1; y--) {
				if (getPoints()[y].compareTo(getPoints()[y - 1]) <= 0) {
					swap(y, y - 1);
				}

			}
		}
	}
}
