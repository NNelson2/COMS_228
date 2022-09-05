package testing;

import edu.iastate.cs228.hw4.AllCopiesRentedOutException;
import edu.iastate.cs228.hw4.Video;
import org.junit.Assert;
import org.junit.Test;

public class VideoTest {

    @Test
    public void args2ConstructorTest() {
        try {
            new Video("Test", 0);
            Assert.fail();
        }
        catch (IllegalArgumentException ignored){}

        Video video = new Video("Forrest Gump", 4);
        Assert.assertEquals("Forrest Gump", video.getFilm());
        Assert.assertEquals(4, video.getNumCopies());
        Assert.assertEquals(0, video.getNumRentedCopies());
        Assert.assertEquals(4, video.getNumAvailableCopies());
    }

    @Test
    public void args1ConstructorTest() {
        Video video = new Video("Forrest Gump");
        Assert.assertEquals("Forrest Gump", video.getFilm());
        Assert.assertEquals(1, video.getNumCopies());
        Assert.assertEquals(0, video.getNumRentedCopies());
        Assert.assertEquals(1, video.getNumAvailableCopies());
    }

    @Test
    public void addNumCopiesTest() {
        Video video = new Video("Forrest Gump");
        try {
            video.addNumCopies(0);
            Assert.fail();
        }
        catch (IllegalArgumentException ignored) {}

        video.addNumCopies(2);
        Assert.assertEquals(3, video.getNumCopies());
        Assert.assertEquals(0, video.getNumRentedCopies());
        Assert.assertEquals(3, video.getNumAvailableCopies());
    }

    @Test
    public void rentCopiesTest() {
        Video video = new Video("Forrest Gump", 3);
        try {
            video.rentCopies(0);
            Assert.fail();
        }
        catch (AllCopiesRentedOutException e) {
            Assert.fail();
        }
        catch (IllegalArgumentException ignored) {}


        try {
            video.rentCopies(1);
            Assert.assertEquals(3, video.getNumCopies());
            Assert.assertEquals(1, video.getNumRentedCopies());
            Assert.assertEquals(2, video.getNumAvailableCopies());
        }
        catch (AllCopiesRentedOutException e) {
            Assert.fail();
        }

        try {
            video.rentCopies(4);
            Assert.assertEquals(3, video.getNumCopies());
            Assert.assertEquals(3, video.getNumRentedCopies());
            Assert.assertEquals(0, video.getNumAvailableCopies());
        }
        catch (AllCopiesRentedOutException e) {
            Assert.fail();
        }

        try {
            video.rentCopies(4);
            Assert.fail();
        }
        catch (AllCopiesRentedOutException ignored) {
        }
    }

    @Test
    public void returnCopiesTest() {
        Video video = new Video("Forrest Gump", 3);
        try {
            video.rentCopies(3);
        }
        catch (AllCopiesRentedOutException e) {
            Assert.fail();
        }

        try {
            video.returnCopies(0);
            Assert.fail();
        }
        catch (IllegalArgumentException ignored) {}

        video.returnCopies(1);
        Assert.assertEquals(3, video.getNumCopies());
        Assert.assertEquals(2, video.getNumRentedCopies());
        Assert.assertEquals(1, video.getNumAvailableCopies());


        video.returnCopies(4);
        Assert.assertEquals(3, video.getNumCopies());
        Assert.assertEquals(0, video.getNumRentedCopies());
        Assert.assertEquals(3, video.getNumAvailableCopies());
    }

    @Test
    public void compareTest() {
        Video video1 = new Video("Forrest Gump");
        Video video2 = new Video("The Godfather");
        Video video3 = new Video("Brokeback Mountain");
        Assert.assertTrue(video1.compareTo(video2) < 0);
        Assert.assertTrue(video2.compareTo(video3) > 0);
        Assert.assertEquals(0, video2.compareTo(video2));
    }

    @Test
    public void toStringTest() {
        Video video1 = new Video("Forrest Gump", 5);
        Assert.assertEquals("Forrest Gump (5:0)", video1.toString());
        Video video2 = new Video("The Godfather");
        Assert.assertEquals("The Godfather (1:0)", video2.toString());
    }
}
