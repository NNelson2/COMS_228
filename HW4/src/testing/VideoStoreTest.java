package testing;

import edu.iastate.cs228.hw4.AllCopiesRentedOutException;
import edu.iastate.cs228.hw4.FilmNotInInventoryException;
import edu.iastate.cs228.hw4.Video;
import edu.iastate.cs228.hw4.VideoStore;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

public class VideoStoreTest {
    @Test
    public void parseFilmTest() {
        Assert.assertEquals("The Godfather", VideoStore.parseFilmName("The Godfather"));
        Assert.assertEquals("Brokeback Mountain", VideoStore.parseFilmName("Brokeback Mountain (5)"));
    }

    @Test
    public void parseNumCopiesTest() {
        Assert.assertEquals(1, VideoStore.parseNumCopies("The Godfather"));
        Assert.assertEquals(5, VideoStore.parseNumCopies("Brokeback Mountain (5)"));
    }

    @Test
    public void noArgsConstructorTest() {
        VideoStore videoStore = new VideoStore();
        Assert.assertEquals("Films in inventory:\n\n", videoStore.inventoryList());
    }

    @Test
    public void argConstructorTest() throws FileNotFoundException {
        VideoStore videoStore = new VideoStore("videoList1.txt");
        Assert.assertEquals("Films in inventory:\n\n" +
                "A Streetcar Named Desire (1)\n" +
                "Brokeback Mountain (1)\n" +
                "Forrest Gump (1)\n" +
                "Psycho (1)\n" +
                "Singin' in the Rain (2)\n" +
                "Slumdog Millionaire (5)\n" +
                "Taxi Driver (1)\n" +
                "The Godfather (1)\n", videoStore.inventoryList());
    }

    @Test
    public void setUpInventoryTest() throws FileNotFoundException {
        VideoStore videoStore = new VideoStore();
        videoStore.setUpInventory("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
        Assert.assertEquals("Films in inventory:\n\n" +
                "A Streetcar Named Desire (1)\n" +
                "Brokeback Mountain (1)\n" +
                "Forrest Gump (1)\n" +
                "Psycho (1)\n" +
                "Singin' in the Rain (2)\n" +
                "Slumdog Millionaire (5)\n" +
                "Taxi Driver (1)\n" +
                "The Godfather (1)\n", videoStore.inventoryList());
    }

    @Test
    public void findVideoTest() throws FileNotFoundException, FilmNotInInventoryException {
        VideoStore videoStore = new VideoStore("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
        Video video1 = videoStore.findVideo("Psycho");
        Assert.assertNotNull(video1);
        Assert.assertEquals("Psycho", video1.getFilm());
        Assert.assertEquals(1, video1.getNumCopies());

        Video video2 = videoStore.findVideo("Slumdog Millionaire");
        Assert.assertNotNull(video2);
        Assert.assertEquals("Slumdog Millionaire", video2.getFilm());
        Assert.assertEquals(5, video2.getNumCopies());

        Video video3 = videoStore.findVideo("The Silence of the Lambs");
        Assert.assertNull(video3);
    }

    @Test
    public void addVideoTest() throws FilmNotInInventoryException {
        VideoStore videoStore = new VideoStore();

        videoStore.addVideo("The Silence of the Lambs", 4);
        Video video1 = videoStore.findVideo("The Silence of the Lambs");
        Assert.assertNotNull(video1);
        Assert.assertEquals("The Silence of the Lambs", video1.getFilm());
        Assert.assertEquals(4, video1.getNumCopies());

        videoStore.addVideo("The Godfather");
        Video video2 = videoStore.findVideo("The Godfather");
        Assert.assertNotNull(video2);
        Assert.assertEquals("The Godfather", video2.getFilm());
        Assert.assertEquals(1, video2.getNumCopies());
    }

    @Test
    public void bulkImportTest() throws FileNotFoundException {
        VideoStore videoStore = new VideoStore();
        videoStore.bulkImport("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
        Assert.assertEquals("Films in inventory:\n\n" +
                "A Streetcar Named Desire (1)\n" +
                "Brokeback Mountain (1)\n" +
                "Forrest Gump (1)\n" +
                "Psycho (1)\n" +
                "Singin' in the Rain (2)\n" +
                "Slumdog Millionaire (5)\n" +
                "Taxi Driver (1)\n" +
                "The Godfather (1)\n", videoStore.inventoryList());
    }

    @Test
    public void availableTest() throws FileNotFoundException, FilmNotInInventoryException {
        VideoStore videoStore = new VideoStore("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
        Assert.assertTrue(videoStore.available("Forrest Gump"));
        Assert.assertFalse(videoStore.available("The Silence of the Lambs"));
    }

    @Test
    public void videoRentTest() throws FileNotFoundException, FilmNotInInventoryException {
        VideoStore videoStore = new VideoStore("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
        try {
            videoStore.videoRent("The Silence of the Lambs", 1);
            Assert.fail();
        } catch (FilmNotInInventoryException ignored) {
        } catch (AllCopiesRentedOutException e) {
            Assert.fail();
        }

        try {
            videoStore.videoRent("The Godfather", 0);
            Assert.fail();
        } catch (FilmNotInInventoryException | AllCopiesRentedOutException e) {
            Assert.fail();
        } catch (IllegalArgumentException ignored) {

        }

        try {
            videoStore.videoRent("The Godfather", 1);
        } catch (AllCopiesRentedOutException ignored) {
        }
        catch (FilmNotInInventoryException e) {
            Assert.fail();
        }

        Video video = videoStore.findVideo("The Godfather");
        Assert.assertEquals("The Godfather", video.getFilm());
        Assert.assertEquals(0, video.getNumAvailableCopies());
        Assert.assertEquals(1, video.getNumRentedCopies());

        try {
            videoStore.videoRent("The Godfather", 1);
        } catch (FilmNotInInventoryException e) {
            Assert.fail();
        } catch (AllCopiesRentedOutException ignored) {
        }
    }

    @Test
    public void bulkRentTest() throws FileNotFoundException, FilmNotInInventoryException {
        VideoStore videoStore = new VideoStore("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
        try {
            videoStore.bulkRent("videoList2.txt");
        }
        catch (IllegalArgumentException | FilmNotInInventoryException | AllCopiesRentedOutException e) {
            Assert.fail();
        }

        Assert.assertEquals(1, videoStore.findVideo("Slumdog Millionaire").getNumAvailableCopies());
        Assert.assertEquals(0, videoStore.findVideo("The Godfather").getNumAvailableCopies());
        Assert.assertEquals(0, videoStore.findVideo("Forrest Gump").getNumAvailableCopies());
    }

    @Test
    public void videoReturnTest() throws FileNotFoundException, FilmNotInInventoryException {
        VideoStore videoStore = new VideoStore("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/films");
        try {
            videoStore.videoReturn("The Silence of the Lambs", 1);
            Assert.fail();
        } catch (FilmNotInInventoryException ignored) {
        }

        try {
            videoStore.videoReturn("The Godfather", 0);
            Assert.fail();
        } catch (FilmNotInInventoryException e) {
            Assert.fail();
        } catch (IllegalArgumentException ignored) {

        }

        try {
            videoStore.videoReturn("The Godfather", 1);
        } catch (FilmNotInInventoryException e) {
            Assert.fail();
        }

        Video video = videoStore.findVideo("The Godfather");
        Assert.assertEquals("The Godfather", video.getFilm());
        Assert.assertEquals(1, video.getNumAvailableCopies());
        Assert.assertEquals(0, video.getNumRentedCopies());
    }

    @Test
    public void bulkReturnTest() throws FileNotFoundException, FilmNotInInventoryException {
        VideoStore videoStore = new VideoStore("videoList1.txt");
        try {
            videoStore.bulkRent("videoList2.txt");
        }
        catch (IllegalArgumentException | FilmNotInInventoryException | AllCopiesRentedOutException e) {
            Assert.fail();
        }

        try {
            videoStore.bulkReturn("videoList3.txt");
        }
        catch (IllegalArgumentException | FilmNotInInventoryException e) {
            Assert.fail();
        }

        Assert.assertEquals(2, videoStore.findVideo("Slumdog Millionaire").getNumAvailableCopies());
        Assert.assertEquals(0, videoStore.findVideo("The Godfather").getNumAvailableCopies());
        Assert.assertEquals(1, videoStore.findVideo("Forrest Gump").getNumAvailableCopies());
    }

    @Test
    public void transactionSummaryTest() throws FileNotFoundException {
        VideoStore videoStore = new VideoStore("videoList1.txt");

        try {
            videoStore.videoRent("The Godfather", 1);
        }
        catch (IllegalArgumentException | FilmNotInInventoryException | AllCopiesRentedOutException e) {
            Assert.fail();
        }

        try {
            videoStore.bulkRent("videoList2.txt");
            Assert.fail();
        }
        catch (IllegalArgumentException | FilmNotInInventoryException e ) {
            Assert.fail();
        }
        catch (AllCopiesRentedOutException ignored) {
        }

        try {
            videoStore.videoRent("Brokeback Mountain", 1);
        }
        catch (IllegalArgumentException | FilmNotInInventoryException | AllCopiesRentedOutException e) {
            Assert.fail();
        }

        try {
            videoStore.videoReturn("Slumdog Millionaire", 2);
        }
        catch (IllegalArgumentException | FilmNotInInventoryException e) {
            Assert.fail();
        }

        try {
            videoStore.videoRent("The Silence of the Lambs", 1);
            Assert.fail();
        }
        catch (FilmNotInInventoryException ignored) {
        }
        catch (AllCopiesRentedOutException e) {
            Assert.fail();
        }

        try {
            videoStore.videoRent("Singin' in the Rain", 2);
        }
        catch (FilmNotInInventoryException | AllCopiesRentedOutException e) {
            Assert.fail();
        }

        try {
            videoStore.bulkReturn("videoList3.txt");
        }
        catch (FilmNotInInventoryException e) {
            Assert.fail();
        }

        Assert.assertEquals("Rented films:\n\n" +
                "Brokeback Mountain (1)\n" +
                "Singin' in the Rain (2)\n" +
                "Slumdog Millionaire (1)\n" +
                "The Godfather (1)\n\n" +
                "Films remaining in inventory:\n\n" +
                "A Streetcar Named Desire (1)\n" +
                "Forrest Gump (1)\n" +
                "Psycho (1)\n" +
                "Slumdog Millionaire (4)\n" +
                "Taxi Driver (1)\n", videoStore.transactionsSummary());
    }
}
