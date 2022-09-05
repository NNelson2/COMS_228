package testing;

import edu.iastate.cs228.hw3.MultiList;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class QuickTest {
    public static void main(String[] args) {
        MultiList<Character> theList = new MultiList<Character>();
        theList.add('A');
        theList.add('B');
        theList.add('A');
        theList.add('A');
        theList.add('C');
        theList.add('D');
        theList.add('E');
        theList.remove(3);
        theList.remove(2);
        System.out.println(theList.toStringInternal());
        theList.add('V');
        theList.add('W');
        theList.add(2,'X');

        theList.add(2,'Y');
        theList.add(2,'Z');
        System.out.println(theList.toStringInternal());




    }
}
