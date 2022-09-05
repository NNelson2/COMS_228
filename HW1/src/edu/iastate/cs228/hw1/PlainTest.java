package edu.iastate.cs228.hw1;

/**
 * @Author Noah Nelson
 */

import java.io.FileNotFoundException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlainTest {

    @Test
    public void fileTest() throws FileNotFoundException {
        String exp = new String("G  B0 F0 \n" +
                "F0 F0 R0 \n" +
                "F0 E  G  ");

        Plain testPlain = new Plain("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW1/public/public1-3x3.txt");
        assertEquals(testPlain.toString(), exp);
    }

    @Test
    public void censusTest() throws IOException {
        Plain testPlain = new Plain("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW1/public/public1-3x3.txt");
        testPlain.write("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW1/public/saveFile.txt");
        Plain savedPlain = new Plain("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW1/public/saveFile.txt");
        assertEquals(testPlain.toString(), savedPlain.toString());

    }



}
