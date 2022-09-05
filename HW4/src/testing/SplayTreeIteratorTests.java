package testing;

import static org.junit.Assert.assertEquals;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.iastate.cs228.hw4.SplayTree;

class SplayTreeIteratorTests {
	
	static SplayTree<Integer> tree;
	static Iterator<Integer> iter;
	
	@BeforeEach
	void setUpBeforeClass() {
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
		
		iter = tree.iterator();
	}
	
	@Test
	void testHasNext() {
		assertEquals(true, iter.hasNext());
		SplayTree<Integer> tree = new SplayTree<Integer>();
		iter = tree.iterator();
		assertEquals(false, iter.hasNext());
	}
	
	@Test
	void testNext() {
		assertEquals((Integer) 1, iter.next());
		assertEquals((Integer) 2, iter.next());
		assertEquals((Integer) 3, iter.next());
		assertEquals((Integer) 4, iter.next());
		assertEquals((Integer) 5, iter.next());
		assertEquals((Integer) 6, iter.next());
		assertEquals((Integer) 7, iter.next());
		try {
			iter.next();
			assertEquals(new NoSuchElementException(), null);
		} catch (NoSuchElementException e) {}
	}
	
	@Test
	void testRemove1() {
		iter.next();
		iter.next();
		iter.remove();
		assertEquals( "4"        + "\n"
			    	+ "    3"      + "\n"
			    	+ "        1"    + "\n"
			    	+ "        null" + "\n"
			    	+ "    6"      + "\n"
			    	+ "        5"    + "\n"
			    	+ "        7"    + "\n", tree.toString());
	}
	
	@Test
	void testRemove2() {
		iter.next();
		iter.next();
		iter.next();
		iter.next();
		iter.next();
		iter.next();
		iter.remove();
		assertEquals( "4"        + "\n"
			    	+ "    2"      + "\n"
			    	+ "        1"    + "\n"
			    	+ "        3"    + "\n"
			    	+ "    7"      + "\n"
			    	+ "        5" + "\n"
			    	+ "        null"    + "\n", tree.toString());
	}
	
	@Test
	void testRemove3() {
		iter.next();
		iter.next();
		iter.next();
		iter.next();
		iter.remove();
		assertEquals( "5"        + "\n"
			    	+ "    2"      + "\n"
			    	+ "        1"    + "\n"
			    	+ "        3"    + "\n"
			    	+ "    6"      + "\n"
			    	+ "        null" + "\n"
			    	+ "        7"    + "\n", tree.toString());
	}

}
