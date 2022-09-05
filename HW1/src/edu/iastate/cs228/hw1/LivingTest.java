package edu.iastate.cs228.hw1;


/**
 * @Author Noah Nelson
 */

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LivingTest {

    @Test
    public void populationTest() {
        int[] population = {2,3,4,1,1};
        Living test = new Living() {
            @Override
            public State who() {
                return null;
            }

            @Override
            public Living next(Plain pNew) {
                return null;
            }
        };

        test.census(population);
        assertEquals(2, test.numBadgers);
        assertEquals(1, test.numRabbits);

    }
}
