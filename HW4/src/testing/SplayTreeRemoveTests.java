package testing;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw4.SplayTree;

public class SplayTreeRemoveTests {
	
	static SplayTree<Integer> tree;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		tree = new SplayTree<Integer>();
		tree.addBST(8);
		tree.addBST(4);
		tree.addBST(2);
		tree.addBST(6);
		tree.addBST(1);
		tree.addBST(3);
		tree.addBST(5);
		tree.addBST(7);
		tree.addBST(12);
		tree.addBST(10);
		tree.addBST(14);
		tree.addBST(9);
		tree.addBST(11);
		tree.addBST(13);
		tree.addBST(15);

		//             8
		//      4             12
		//   2     6      10      14
		//  1 3   5 7    9  11  13  15
	}
	
	@Test
	void testRemove1() {
		tree.remove(4);
		assertEquals( "8"                + "\n"
			    	+ "    3"            + "\n"
			    	+ "        2"        + "\n"
			    	+ "            1"    + "\n"
			    	+ "            null" + "\n"
			    	+ "        6"        + "\n"
			    	+ "            5"    + "\n"
			    	+ "            7"    + "\n"
			    	+ "    12"           + "\n"
    				+ "        10"       + "\n"
					+ "            9"    + "\n"
					+ "            11"   + "\n"
                    + "        14"       + "\n"
					+ "            13"   + "\n"
					+ "            15"   + "\n", tree.toString());
	}
	
	@Test
	void testRemove2() {
		tree.remove(12);
		assertEquals( "8"                + "\n"
		    		+ "    4"            + "\n"
		    		+ "        2"        + "\n"
		    		+ "            1"    + "\n"
		    		+ "            3"    + "\n"
                	+ "        6"        + "\n"
                	+ "            5"    + "\n"
                	+ "            7"    + "\n"
		    		+ "    11"           + "\n"
                	+ "        10"       + "\n"
                	+ "            9"    + "\n"
                	+ "            null" + "\n"
                	+ "        14"       + "\n"
                	+ "            13"   + "\n"
                	+ "            15"   + "\n", tree.toString());
	}
	
	@Test
	void testRemove3() {
		tree.remove(6);
		assertEquals( "4"                  + "\n"
	    			+ "    2"              + "\n"
	    			+ "        1"          + "\n"
	    			+ "        3"          + "\n"
			    	+ "    8"              + "\n"
			    	+ "        5"          + "\n"
            		+ "            null"   + "\n"
            		+ "            7"      + "\n"
			    	+ "        12"         + "\n"
    				+ "            10"     + "\n"
					+ "                9"  + "\n"
					+ "                11" + "\n"
                    + "            14"     + "\n"
					+ "                13" + "\n"
					+ "                15" + "\n", tree.toString());
	}
	
	@Test
	void testRemove4() {
		tree.remove(10);
		assertEquals( "12"                + "\n"
    				+ "    8"             + "\n"
    				+ "        4"         + "\n"
    				+ "            2"     + "\n"
    				+ "                1" + "\n"
    				+ "                3" + "\n"
    				+ "            6"     + "\n"
    				+ "                5" + "\n"
    				+ "                7" + "\n"
    				+ "        9"         + "\n"
    				+ "            null"  + "\n"
    				+ "            11"    + "\n"
    				+ "    14"            + "\n"
    				+ "        13"        + "\n"
    				+ "        15"        + "\n", tree.toString());
	}

	@Test
	void testRemoveRoot() {
		tree.remove(8);

	}
}
