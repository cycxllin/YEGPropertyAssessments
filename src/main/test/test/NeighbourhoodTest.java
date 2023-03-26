package test;

import main.java.classes.Neighbourhood;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeighbourhoodTest {
    public Neighbourhood n1;
    @BeforeEach
    void setUp() {
    n1 = new Neighbourhood("Barley", "Dog Ward");
    }

    @Test
    void setName() {
        Neighbourhood expected = new Neighbourhood("Esther", "Dog Ward");
        n1.setName("Esther");
        assertEquals(expected, n1);
    }

    @Test
    void setWard() {
        Neighbourhood expected = new Neighbourhood("Barley", "Puppy Ward");
        n1.setWard("Puppy Ward");
        assertEquals(expected, n1);
    }

    @Test
    void getId() {
        Neighbourhood result = new Neighbourhood( "Barley", "Dog Ward");
        assertEquals(n1, result);
    }

    @Test
    void getName() {
        Neighbourhood result = new Neighbourhood(n1.getName(), "Dog Ward");
        assertEquals(n1, result);
    }

    @Test
    void getWard() {
        Neighbourhood result = new Neighbourhood("Barley", n1.getWard());
        assertEquals(n1, result);
    }

    @Test
    void testToString() {
        String expected = "Barley (Dog Ward)";
        assertEquals(expected, n1.toString());
    }

    @Test
    void testEquals() {
        //reflexive
        assertEquals(n1, n1);

        //symmetric
        Neighbourhood secondNeigh = new Neighbourhood("Barley", "Dog Ward");
        assertEquals(n1, secondNeigh);
        assertEquals(secondNeigh, n1);

        //transitive
        Neighbourhood thirdNeigh = new Neighbourhood("Barley", "Dog Ward");
        assertEquals(secondNeigh, thirdNeigh);
        assertEquals(n1, thirdNeigh);

        //false results
        Neighbourhood newNeigh = new Neighbourhood("Esther", "Puppy Ward");
        assertNotEquals(null, n1);
        assertNotEquals(n1, newNeigh);
        int testClass = 9;
        assertNotEquals(n1, testClass);
    }

    @Test
    void testHashCode() {
        int expected = n1.hashCode();
        Neighbourhood sameNeigh = new Neighbourhood("Barley", "Dog Ward");
        int result = sameNeigh.hashCode();
        assertEquals(expected, result);

        Neighbourhood newNeigh = new Neighbourhood("Esther", "Puppy Ward");
        assertNotEquals(expected, newNeigh.hashCode());
    }
}