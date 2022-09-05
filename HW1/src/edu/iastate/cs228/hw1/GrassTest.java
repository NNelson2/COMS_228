package edu.iastate.cs228.hw1;


/**
 * @Author Noah Nelson
 */

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrassTest {
    @Test
    public void initTest() {
        Plain p = new Plain(4);
        Grass a = new Grass(p, 2, 1);
        assertEquals(State.GRASS, p.grid[2][1].who());
    }

    @Test
    public void logicTest() throws FileNotFoundException {
        Plain p = new Plain("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW1/src/edu/iastate/cs228/hw1/public1-3x3.txt");
        p.grid[2][2].next(p);
        assertEquals(State.GRASS, p.grid[2][2].who());
    }
}
