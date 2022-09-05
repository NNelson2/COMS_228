package testing;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.iastate.cs228.hw4.AllCopiesRentedOutException;
import edu.iastate.cs228.hw4.Video;

class VideoTests {

	static Video video;
	
	@BeforeEach
	void setUpBeforeClass() {
		video = new Video("Wall-E");
	}
	
	@Test
	void testAddNumCopies1() {
		try {
			video.addNumCopies(3);
			assertEquals("Wall-E (4:0)", video.toString());
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		}
	}
	
	@Test
	void testAddNumCopies2() {
		try {
			video.addNumCopies(-3);
			assertEquals(null, new IllegalArgumentException());
		} catch (IllegalArgumentException e) {
			assertEquals("Wall-E (1:0)", video.toString());
		}
	}
	
	@Test
	void testRentCopies1() {
		try {
			video.addNumCopies(2);
			video.rentCopies(2);
			assertEquals("Wall-E (3:2)", video.toString());
		} catch (AllCopiesRentedOutException e) {
			assertEquals(null, e);
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		}
	}
	
	@Test
	void testRentCopies2() {
		try {
			video.rentCopies(-3);
			assertEquals(null, new IllegalArgumentException());
		} catch (AllCopiesRentedOutException e) {
			assertEquals(new IllegalArgumentException(), e);
		} catch (IllegalArgumentException e) {
			assertEquals("Wall-E (1:0)", video.toString());
		}
	}
	
	@Test
	void testRentCopies3() {
		try {
			video.addNumCopies(2);
			video.rentCopies(3);
			video.rentCopies(1);
			assertEquals(null, new AllCopiesRentedOutException());
		} catch (AllCopiesRentedOutException e) {
			assertEquals("Wall-E (3:3)", video.toString());
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		}
	}
	
	@Test
	void testReturnCopies1() {
		try {
			video.addNumCopies(2);
			video.rentCopies(3);
			video.returnCopies(1);
			assertEquals("Wall-E (3:2)", video.toString());
		} catch (AllCopiesRentedOutException e) {
			assertEquals(null, e);
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		}
	}
	
	@Test
	void testReturnCopies2() {
		try {
			video.addNumCopies(2);
			video.rentCopies(3);
			video.returnCopies(4);
			assertEquals("Wall-E (3:0)", video.toString());
		} catch (AllCopiesRentedOutException e) {
			assertEquals(null, e);
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		}
	}
	
	@Test
	void testReturnCopies3() {
		try {
			video.addNumCopies(2);
			video.rentCopies(3);
			video.returnCopies(-3);
			assertEquals(new IllegalArgumentException(), null);
		} catch (AllCopiesRentedOutException e) {
			assertEquals(null, e);
		} catch (IllegalArgumentException e) {
			assertEquals("Wall-E (3:3)", video.toString());
		}
	}
	
}
