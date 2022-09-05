package testing;
import static org.junit.jupiter.api.Assertions.*;

import edu.iastate.cs228.hw4.SplayTree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class OpTesting {
    static SplayTree<Integer> tree;
    @BeforeEach
    void setUp() {
         tree = new SplayTree<Integer>(3);
         tree.addBST(2);
         tree.addBST(44);
         tree.addBST(100);
    }

    @Test
    void test1() {
        assertTrue(tree.contains(100));
    }

    @Test
    void test2() {
        assertTrue(tree.contains(3));
    }

    @Test
    void size() {
        assertEquals(4, tree.size());
    }
}
