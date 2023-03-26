package test;

import main.java.classes.Address;
import main.java.classes.Location;
import main.java.classes.Neighbourhood;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    public Location loc;
    public Location locNoNeigh;
    public Location locNoAdd;

    @BeforeEach
    void setUp() {
        loc = new Location (52.9098, -113.2, new Address(2, "Weather Cres"),
                new Neighbourhood("Garner", "Ward"));
        locNoNeigh = new Location(90.2, -78.2736, new Address("1",13,"Beverly Cres"));
        locNoAdd = new Location(23.19029, -123.09887);
    }

    @Test
    void setLat() {
        Location expected = new Location(13.789, -123.09887);
        locNoAdd.setLat(13.789);
        assertEquals(expected, locNoAdd);
    }

    @Test
    void setLon() {
        Location expected = new Location(23.19029, 13.789);
        locNoAdd.setLon(13.789);
        assertEquals(expected, locNoAdd);
    }

    @Test
    void setAddress(){
        Address add = new Address(1,"Beverly Cres");
        Location expected = new Location(90.2, -78.2736, new Address(1,"Beverly Cres"));
        locNoNeigh.setAddress(add);

        assertEquals(expected,locNoNeigh);
    }

    @Test
    void setNeighbourhood(){
        Neighbourhood neigh = new Neighbourhood("Name", "Ward");
        Location expected = new Location (52.9098, -113.2, new Address(2, "Weather Cres"),
                new Neighbourhood("Name", "Ward"));
        loc.setNeighbourhood(neigh);

        assertEquals(expected, loc);
    }

    @Test
    void getLat() {
        Location result = new Location(locNoAdd.getLat(), -123.09887);
        assertEquals(locNoAdd, result);
    }

    @Test
    void getLon() {
        Location result = new Location(23.19029, locNoAdd.getLon());
        assertEquals(locNoAdd, result);
    }

    @Test
    void getAddress(){
        Location result = new Location(90.2, -78.2736, locNoNeigh.getAddress());
        assertEquals(locNoNeigh, result);

    }

    @Test
    void getNeighbourhood(){
        Location result = new Location (52.9098, -113.2, new Address(2, "Weather Cres"),
                loc.getNeighbourhood());
        assertEquals(loc, result);
    }

    @Test
    void testHashCode() {
        int expected = locNoAdd.hashCode();
        Location sameLoc = new Location(23.19029, -123.09887);
        int result = sameLoc.hashCode();
        assertEquals(expected, result);

        Location newLoc = new Location(9, 9);
        assertNotEquals(expected, newLoc.hashCode());
    }

    @Test
    void testToString() {
        String expectedLoc = "2 Weather Cres\nGarner (Ward)\n(52.9098 -113.2)";
        String expectedLocNoNeigh = "1 13 Beverly Cres\n(90.2 -78.2736)";
        String expectedLocNoAdd = "(23.19029 -123.09887)";

        Location emptyLoc = new Location();

        assertEquals(expectedLoc, loc.toString());
        assertEquals(expectedLocNoNeigh, locNoNeigh.toString());
        assertEquals(expectedLocNoAdd, locNoAdd.toString());
        assertEquals("(0.0 0.0)", emptyLoc.toString());
    }

    @Test
    void testGetLatLon(){
        String expectedLocNoAdd = "(23.19029, -123.09887)";
        assertEquals(expectedLocNoAdd, locNoAdd.getLatLon());
    }
}