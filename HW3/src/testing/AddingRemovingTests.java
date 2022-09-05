package testing;
import edu.iastate.cs228.hw3.MultiList;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class AddingRemovingTests {

    @Test
    public void Test1() {
        MultiList<Integer> list = new MultiList<Integer>(4);
        list.add(3);
        list.add(2);
        list.add(55);
        list.add(22);
        assertEquals(4, list.size());list.toStringInternal();

    }

}
