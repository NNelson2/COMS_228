package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 * @author Noah Nelson
 *
 *
 * this class is used for implementing a comparator
 *
 *
 */
public class Comparing implements Comparator<Point> {
    @Override
    public int compare(Point a, Point b) {
        if (a.compareTo(b) == - 1) {
            return -1;
        } else if (a.compareTo(b) == 1) {
            return 1;
        } else {
            return 0;
        }
    }
}
