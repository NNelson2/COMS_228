package testing;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.iastate.cs228.hw4.SplayTree;

class SplayTreeTests {

	static SplayTree<Integer> tree;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		tree = new SplayTree<Integer>();
		tree.addBST(4);
		tree.addBST(2);
		tree.addBST(1);
		tree.addBST(3);
		tree.addBST(6);
		tree.addBST(5);
		tree.addBST(7);
		
		//     4
		//  2     6
		// 1 3   5 7
	}

	@Test
	void testAddBST() {
		assertEquals( "4"         + "\n"
				    + "    2"     + "\n"
					+ "        1" + "\n"
					+ "        3" + "\n"
					+ "    6"     + "\n"
					+ "        5" + "\n"
					+ "        7" + "\n", tree.toString());
	}
	
	@Test
	void testCloneTreeRec() {
		SplayTree<Integer> treeClone = new SplayTree<Integer>(tree);
		assertEquals( "4"         + "\n"
			    	+ "    2"     + "\n"
			    	+ "        1" + "\n"
			    	+ "        3" + "\n"
			    	+ "    6"     + "\n"
			    	+ "        5" + "\n"
			    	+ "        7" + "\n", treeClone.toString());
	}
	
	@Test
	void testZig1() {
		tree.splay(2);
		assertEquals( "2"             + "\n"
					+ "    1"         + "\n"
					+ "    4"         + "\n"
					+ "        3"     + "\n"
					+ "        6"     + "\n"
					+ "            5" + "\n"
					+ "            7" + "\n", tree.toString());
	}
	
	@Test
	void testZig2() {
		tree.splay(6);
		assertEquals( "6"             + "\n"
					+ "    4"         + "\n"
					+ "        2"     + "\n"
					+ "            1" + "\n"
					+ "            3" + "\n"
					+ "        5"     + "\n"
					+ "    7"         + "\n", tree.toString());
	}
	
	@Test
	void testZigZig1() {
		tree.splay(1);
		assertEquals( "1"                 + "\n"
					+ "    null"          + "\n"
					+ "    2"             + "\n"
					+ "        null"      + "\n"
					+ "        4"         + "\n"
					+ "            3"     + "\n"
					+ "            6"     + "\n"
					+ "                5" + "\n"
					+ "                7" + "\n", tree.toString());
	}
	
	@Test
	void testZigZig2() {
		tree.splay(7);
		assertEquals( "7"                 + "\n"
					+ "    6"             + "\n"
					+ "        4"         + "\n"
					+ "            2"     + "\n"
					+ "                1" + "\n"
					+ "                3" + "\n"
					+ "            5"     + "\n"
					+ "        null"      + "\n"
					+ "    null"          + "\n", tree.toString());
	}
	
	@Test
	void testZigZag1() {
		tree.splay(3);
		assertEquals( "3"             + "\n"
					+ "    2"         + "\n"
					+ "        1"     + "\n"
					+ "        null"  + "\n"
					+ "    4"         + "\n"
					+ "        null"  + "\n"
					+ "        6"     + "\n"
					+ "            5" + "\n"
					+ "            7" + "\n", tree.toString());
	}
	
	@Test
	void testZigZag2() {
		tree.splay(5);
		assertEquals( "5"             + "\n"
					+ "    4"         + "\n"
					+ "        2"     + "\n"
					+ "            1" + "\n"
					+ "            3" + "\n"
					+ "        null"  + "\n"
					+ "    6"         + "\n"
					+ "        null"  + "\n"
					+ "        7"     + "\n", tree.toString());
	}
	
	@Test
	void testContains1() {
		assertEquals(tree.contains(3), true);
		assertEquals( "3"             + "\n"
					+ "    2"         + "\n"
					+ "        1"     + "\n"
					+ "        null"  + "\n"
					+ "    4"         + "\n"
					+ "        null"  + "\n"
					+ "        6"     + "\n"
					+ "            5" + "\n"
					+ "            7" + "\n", tree.toString());
	}
	
	@Test
	void testContains2() {
		assertEquals(tree.contains(8), false);
		assertEquals( "7"                 + "\n"
					+ "    6"             + "\n"
					+ "        4"         + "\n"
					+ "            2"     + "\n"
					+ "                1" + "\n"
					+ "                3" + "\n"
					+ "            5"     + "\n"
					+ "        null"      + "\n"
					+ "    null"          + "\n", tree.toString());
	}
	
	@Test
	void testAdd1() {
		tree.add(8);
		assertEquals( "8"                    + "\n"
					+ "    4"                + "\n"
					+ "        2"            + "\n"
					+ "            1"        + "\n"
					+ "            3"        + "\n"
					+ "        7"            + "\n"
					+ "            6"        + "\n"
					+ "                5"    + "\n"
					+ "                null" + "\n"
					+ "            null"     + "\n"
					+ "    null"             + "\n", tree.toString());
	}
	
}
