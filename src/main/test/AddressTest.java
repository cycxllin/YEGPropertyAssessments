import main.java.classes.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address address;
    private Address empty;
    private Address noSuite;
    private Address noNum;

    @BeforeEach
    void setUp() {
        address = new Address("16", 143, "37 STREET SW");
        empty = new Address();
        noSuite = new Address(143, "37 STREET SW");
        noNum = new Address("37 STREET SW");

    }

    @Test
    void setSuite() {
        Address expResult = new Address("2", 143, "37 STREET SW");
        address.setSuite("2");
        assertEquals(expResult, address);
    }

    @Test
    void setHouseNumber() {
        Address expResult = new Address(10, "37 STREET SW");
        noSuite.setHouseNumber(10);
        assertEquals(expResult, noSuite);
    }

    @Test
    void setStreetName() {
        Address expResult = new Address("Apple Court");
        noNum.setStreetName("Apple Court");
        assertEquals(expResult, noNum);
    }

    @Test
    void getSuite() {
        Address result = new Address(address.getSuite(), 143, "37 STREET SW");
        assertEquals(address, result);
    }

    @Test
    void getHouseNumber() {
        Address result = new Address("16", address.getHouseNumber(), "37 STREET SW");
        assertEquals(address, result);
    }

    @Test
    void getStreetName() {
        Address result = new Address("16", 143, address.getStreetName());
        assertEquals(address, result);
    }

    @Test
    void testHashCode() {
        int expected = address.hashCode();
        Address sameAddress = new Address("16", 143, "37 STREET SW");
        int result = sameAddress.hashCode();
        assertEquals(expected, result);
        assertNotEquals(expected, empty.hashCode());
    }

    @Test
    void testEquals() {
        //reflexive
        assertEquals(address, address);

        //symmetric
        Address secondAddress = new Address("16",143, "37 STREET SW");
        assertEquals(address, secondAddress);
        assertEquals(secondAddress, address);

        //transitive
        Address thirdAddress = new Address("16",143, "37 STREET SW");
        assertEquals(secondAddress, thirdAddress);
        assertEquals(address, thirdAddress);

        //false results
        Address newAddress = new Address(16, "Belleville Ave");
        assertNotEquals(null, address);
        assertNotEquals(address, newAddress);
        int testClass = 9;
        assertNotEquals(address, testClass);
    }

    @Test
    void testToString() {
        String expected1 = "16 143 37 STREET SW";
        String expected2 = "143 37 STREET SW";
        String expected3 = "37 STREET SW";
        String expected4 = "";

        assertEquals(expected1, address.toString());
        assertEquals(expected2, noSuite.toString());
        assertEquals(expected3, noNum.toString());
        assertEquals(expected4, empty.toString());
    }
}