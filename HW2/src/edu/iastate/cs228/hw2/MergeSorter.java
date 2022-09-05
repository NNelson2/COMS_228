package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 *  
 * @author Noah Nelson
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter {
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		algorithm = Algorithm.MergeSort.toString();
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort() {
		mergeSortRec(getPoints());
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 *
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts) {

		if (pts.length <= 1) {
			return;

		}
		int mid = pts.length / 2;
		Point[] left = new Point[mid];
		Point[] right;

		if (pts.length %2 == 0) {
			 right = new Point[mid];
		} else {
			right = new Point[mid + 1];
		}

		// populate left array
		System.arraycopy(pts, 0, left, 0, left.length);
		// populate right array
		System.arraycopy(pts, mid, right, 0, right.length);

		mergeSortRec(left);
		mergeSortRec(right);
		merge(pts, left, right);


	}

	/**
	 * merges the left and right array into pts[]
	 * @param left
	 * @param right
	 * @param pts
	 * @return the merged array
	 */
	private void merge(Point[] pts, Point[] left, Point[] right) {

		int leftIndex = 0, rightIndex = 0, ptsIndex = 0;

		while (leftIndex < left.length || rightIndex < right.length) {

			if (leftIndex < left.length && rightIndex < right.length) {

				if (left[leftIndex].compareTo(right[rightIndex]) <= 0) {
					pts[ptsIndex++] = left[leftIndex++];

				} else {
					pts[ptsIndex++] = right[rightIndex++];

				}

			} else if (leftIndex < left.length) {
				pts[ptsIndex++] = left[leftIndex++];

			} else if (rightIndex < right.length) {
				pts[ptsIndex++] = right[rightIndex++];

			}
		}
	}

}


