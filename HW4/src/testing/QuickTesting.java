package testing;

import edu.iastate.cs228.hw4.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class QuickTesting {
    public static void main(String[] args) throws IOException, FilmNotInInventoryException, AllCopiesRentedOutException {
        SplayTree<Integer> tree = new SplayTree<Integer>();
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

        System.out.println(tree.toString());
        tree.unlinkBST(tree.findEntry(12));
        System.out.println(tree.toString());
        tree.unlinkBST(tree.findEntry(14));
        System.out.println(tree.toString());
        String test = "test (1)";
        VideoStore movie = new VideoStore("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/moreFilms");
//        movie.addVideo("Slumdog Millionaire", 4);
//        movie.addVideo("Thug Hunters vol 2", 4000);
//        movie.addVideo("mermaids");
//        movie.addVideo("mermaids");
//        movie.bulkImport("/Users/noahnelson/Library/Mobile Documents/com~apple~CloudDocs/Iowa State/Spring 2022/COMS_228/HW4/src/testing/moreFilms");
//        movie.videoRent("Inception" , 1);
//        System.out.println(movie.inventoryList());




    }
}
