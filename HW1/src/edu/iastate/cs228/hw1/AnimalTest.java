package edu.iastate.cs228.hw1;


/**
 * @Author Noah Nelson
 */

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {

    @Test
    public void test() {
        Animal animal = new Animal() {
            @Override
            public State who() {
                return null;
            }

            @Override
            public Living next(Plain pNew) {
                return null;
            }
        };
        assertEquals(0, animal.myAge());
    }

}
