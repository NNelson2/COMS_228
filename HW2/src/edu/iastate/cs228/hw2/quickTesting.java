package edu.iastate.cs228.hw2;

import edu.iastate.cs228.hw2.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class quickTesting {
    public static void main(String[] args) throws IOException {
//       Random rand = new Random();
//        rand.setSeed(5);
//       Point[] points = CompareSorters.generateRandomPoints(2, rand);
        PointScanner scanner = new PointScanner("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW2/src/edu/iastate/cs228/hw2/readTest.txt", Algorithm.InsertionSort);
        scanner.scan();
        scanner.writeMCPToFile();
        System.out.println(scanner.stats());

        AbstractSorter aSort = new InsertionSorter(scanner.points);
        aSort.sort();
        System.out.println(Arrays.stream(scanner.points).toList());
//        System.out.println(Arrays.stream(aSort.getPoints(points)).toList());
    }
}
