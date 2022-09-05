package testing;

import static org.junit.Assert.assertEquals;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.iastate.cs228.hw4.AllCopiesRentedOutException;
import edu.iastate.cs228.hw4.FilmNotInInventoryException;
import edu.iastate.cs228.hw4.VideoStore;

class VideoStoreTests {
	
	static VideoStore videoStore;
	
	@BeforeEach
	void setUpBeforeClass() throws FileNotFoundException {
		videoStore = new VideoStore("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
	}
	@Test
	void testSetupInventory() throws IOException {
		assertEquals( "A Streetcar Named Desire (1) "
					+ "Brokeback Mountain (1) "
					+ "Forrest Gump (1) "
					+ "Psycho (1) "
					+ "Singin'in the Rain (2) "
					+ "Slumdog Millionaire (5) "
					+ "Taxi Driver (1) "
					+ "The Godfather (1)", videoStore.inventoryList());
	}
	
	@Test
	void testFindVideo() throws FilmNotInInventoryException {
		assertEquals("A Streetcar Named Desire", videoStore.findVideo("A Streetcar Named Desire").getFilm());
		assertEquals(new FilmNotInInventoryException(), videoStore.findVideo("ur mom lol"));
	}
	
	@Test
	void testAddVideo() throws FilmNotInInventoryException {
		videoStore.addVideo("Forrest Gump", 2);
		assertEquals(3, videoStore.findVideo("Forrest Gump").getNumCopies());
		videoStore.addVideo("ur mom lol", 5);
		assertEquals(5, videoStore.findVideo("ur mom lol").getNumCopies());
	}
	
	@Test
	void testBulkImport() throws IOException {
		videoStore.bulkImport("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/moreFilms");
		assertEquals( "A Streetcar Named Desire (1) "
				   	+ "Brokeback Mountain (1) "
				   	+ "Forrest Gump (4) "
				   	+ "Inception (1) "
				   	+ "Psycho (2) "
				   	+ "Singin'in the Rain (2) "
				   	+ "Slumdog Millionaire (5) "
				   	+ "Spider-Man (1) "
				   	+ "Taxi Driver (2) "
				   	+ "The Godfather (1) "
					+ "The Thing (2)", videoStore.inventoryList());
	}
	
	@Test
	void testVideoRent1() {
		try {
			videoStore.addVideo("Forrest Gump");
			videoStore.videoRent("Forrest Gump", 1);
			assertEquals(true, videoStore.available("Forrest Gump"));
			videoStore.videoRent("Forrest Gump", 1);
			assertEquals(false, videoStore.available("Forrest Gump"));
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		} catch (AllCopiesRentedOutException e) {
			assertEquals(null, e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(null, e);
		}
	}
	
	@Test
	void testVideoRent2() {
		try {
			videoStore.videoRent("Forrest Gump", 1);
			videoStore.videoRent("Forrest Gump", 1);
			assertEquals(new AllCopiesRentedOutException(), null);
		} catch (IllegalArgumentException e) {
			assertEquals(new AllCopiesRentedOutException(), e);
		} catch (AllCopiesRentedOutException e) {
			assertEquals(e, e);
			System.out.println("testVideoRent2^ \n");
		} catch (FilmNotInInventoryException e) {
			assertEquals(new AllCopiesRentedOutException(), e);
		}
	}
	
	@Test
	void testVideoRent3() {
		try {
			videoStore.videoRent("ur mom lol", 1);
			assertEquals(new FilmNotInInventoryException(), null);
		} catch (IllegalArgumentException e) {
			assertEquals(new FilmNotInInventoryException(), e);
		} catch (AllCopiesRentedOutException e) {
			assertEquals(new FilmNotInInventoryException(), e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(e, e);
			System.out.println("testVideoRent3^ \n");
		}
	}
	
	@Test
	void testVideoRent4() {
		try {
			videoStore.videoRent(null, -1);
			assertEquals(new IllegalArgumentException(), null);
		} catch (IllegalArgumentException e) {
			assertEquals(e, e);
		} catch (AllCopiesRentedOutException e) {
			assertEquals(new IllegalArgumentException(), e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(new IllegalArgumentException(), e);
		}
	}
	
	@Test
	void testVideoReturn1() {
		try {
			videoStore.videoRent("Forrest Gump", 1);
			assertEquals(false, videoStore.available("Forrest Gump"));
			videoStore.videoReturn("Forrest Gump", 1);
			assertEquals(true, videoStore.available("Forrest Gump"));
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		} catch (AllCopiesRentedOutException e) {
			assertEquals(null, e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(null, e);
		}
	}
	
	@Test
	void testVideoReturn2() {
		try {
			videoStore.videoReturn("ur mom lol", 1);
			assertEquals(new FilmNotInInventoryException(), null);
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(e, e);
			System.out.println("testVideoReturn2^ \n");
		}
	}
	
	@Test
	void testVideoReturn3() {
		try {
			videoStore.videoReturn("Forrest Gump", -1);
			assertEquals(new IllegalArgumentException(), null);
		} catch (IllegalArgumentException e) {
			assertEquals(e, e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(null, e);
		}
	}
	
	@Test
	void testBulkRent1() {
		try {
			videoStore.bulkRent("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
		} catch (FileNotFoundException e) {
			assertEquals(null, e);
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		} catch (AllCopiesRentedOutException e) {
			assertEquals(null, e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(null, e);
		}
	}
	
	@Test
	void testBulkRent2() {
		try {
			videoStore.bulkRent("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/bulkRentFilms1");
			assertEquals(new IllegalArgumentException(), null);
		} catch (FileNotFoundException e) {
			assertEquals(new IllegalArgumentException(), e);
		} catch (IllegalArgumentException e) {
			assertEquals(e, e);
			System.out.println("testBulkRent2^ \n");
		} catch (AllCopiesRentedOutException e) {
			assertEquals(new IllegalArgumentException(), e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(new IllegalArgumentException(), e);
		}
	}
	
	@Test
	void testBulkRent3() {
		try {
			videoStore.bulkRent("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/bulkRentFilms2");
			assertEquals(new FilmNotInInventoryException(), null);
		} catch (FileNotFoundException e) {
			assertEquals(new FilmNotInInventoryException(), e);
		} catch (IllegalArgumentException e) {
			assertEquals(new FilmNotInInventoryException(), e);
		} catch (AllCopiesRentedOutException e) {
			assertEquals(new FilmNotInInventoryException(), e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(e, e);
			System.out.println("testBulkRent3^ \n");
		}
	}
	
	@Test
	void testBulkReturn1() {
		try {
			videoStore.bulkReturn("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
		} catch (FileNotFoundException e) {
			assertEquals(null, e);
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(null, e);
		}
	}
	
	@Test
	void testBulkReturn2() {
		try {
			videoStore.bulkReturn("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/bulkReturnFilms");
			assertEquals(new IllegalArgumentException(), null);
		} catch (FileNotFoundException e) {
			assertEquals(new IllegalArgumentException(), e);
		} catch (IllegalArgumentException e) {
			assertEquals(e, e);
			System.out.println("testBulkRent2^ \n");
		} catch (FilmNotInInventoryException e) {
			assertEquals(new IllegalArgumentException(), e);
		}
	}
	
	@Test
	void testTransactionSummary() {
		try {
			videoStore.addVideo("ur mom lol", 3);
			videoStore.videoRent("ur mom lol", 1);
			videoStore.videoRent("Forrest Gump", 1);
			videoStore.videoRent("Slumdog Millionaire", 3);
			System.out.println(videoStore.transactionsSummary());
			assertEquals("Rented films:\n"
					   + "\n"
					   + "Forrest Gump (1)\n"
					   + "Slumdog Millionaire (3)\n"
					   + "ur mom lol (1)\n"
					   + "\n"
					   + "Films remaining in inventory:\n"
					   + "\n"
					   + "A Streetcar Named Desire (1)\n"
					   + "Brokeback Mountain (1)\n"
					   + "Psycho (1)\n"
					   + "Singin'in the Rain (2)\n"
					   + "Slumdog Millionaire (2)\n"
					   + "Taxi Driver (1)\n"
					   + "The Godfather (1)\n"
					   + "ur mom lol (2)\n", videoStore.transactionsSummary());
		} catch (IllegalArgumentException e) {
			assertEquals(null, e);
		} catch (AllCopiesRentedOutException e) {
			assertEquals(null, e);
		} catch (FilmNotInInventoryException e) {
			assertEquals(null, e);
		}
	}
	
}
