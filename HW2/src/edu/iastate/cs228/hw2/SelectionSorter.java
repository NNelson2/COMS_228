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
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter {
	// Other private instance variables if you need ... 
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts) {
		super(pts);
		algorithm = Algorithm.SelectionSort.name();

	}	

	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 */
	@Override 
	public void sort() {
		for (int x = 0; x < getPoints().length; x++) {
			Point currentMin = getPoints()[x];
			int selectedIndex = x;
			for (int y = x; y < getPoints().length; y++) {
				if (currentMin.compareTo(getPoints()[y]) >= 0) {
					currentMin = getPoints()[y];
					selectedIndex = y;
				}
			}
			swap(x, selectedIndex);

		}
	}	
}
