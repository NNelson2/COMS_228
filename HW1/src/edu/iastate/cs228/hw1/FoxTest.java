package edu.iastate.cs228.hw1;


/**
 * @Author Noah Nelson
 */

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoxTest {
    @Test
    public void initTestBadger() {
        Plain p = new Plain(4);
        Fox a = new Fox(p, 2, 1, 4);
        assertEquals(4, a.myAge());
        assertEquals(State.FOX, p.grid[2][1].who());
    }

    @Test
    public void logicTest() throws FileNotFoundException {
        Plain p = new Plain("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW1/public/public1-3x3.txt");
        p.grid[2][0].next(p);
        assertEquals(State.EMPTY, p.grid[2][0].who());
    }
}
