package testing;

import edu.iastate.cs228.hw2.CompareSorters;
import edu.iastate.cs228.hw2.Point;
import org.junit.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTests {

    @Test
    public void pointTest()
    {
        Random rand = new Random();
        rand.setSeed(3);
        Point[] points = CompareSorters.generateRandomPoints(6, rand);
        assertEquals(6, points.length);

    }
}
