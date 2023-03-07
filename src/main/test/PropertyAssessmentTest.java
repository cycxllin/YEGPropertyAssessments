import main.java.classes.AssessmentClasses;
import main.java.classes.Location;
import main.java.classes.PropertyAssessment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyAssessmentTest {
    public PropertyAssessment property;
    public Location loc;
    public AssessmentClasses aC;

    @BeforeEach
    void setUp() {
        loc = new Location(90.0, -17.90);
        aC = new AssessmentClasses(100, "Residential");
        property = new PropertyAssessment(12, 78_000, "N", loc, aC);
    }

    @Test
    void setAccount() {
        PropertyAssessment expected = new PropertyAssessment(1, 78_000, "N", loc, aC);
        property.setAccount(1);
        assertEquals(expected, property);
    }

    @Test
    void setValue() {
        PropertyAssessment expected = new PropertyAssessment(12, 330_000, "N", loc, aC);
        property.setValue(330_000);
        assertEquals(expected, property);
    }

    @Test
    void setGarage() {
        PropertyAssessment expected = new PropertyAssessment(12, 78_000, "Y", loc, aC);
        property.setGarage("Y");
        assertEquals(expected, property);
    }

    @Test
    void setLocation(){
        Location newLoc = new Location(90, -12);
        PropertyAssessment expected = new PropertyAssessment(12, 78_000, "N", newLoc, aC);
        property.setLocation(newLoc);
        assertEquals(expected, property);
    }

    @Test
    void setAssessmentClasses(){
        AssessmentClasses newAC = new AssessmentClasses(1, "Other");
        PropertyAssessment expected = new PropertyAssessment(12, 78_000, "N", loc, newAC);
        property.setAssessmentClasses(newAC);
        assertEquals(expected, property);
    }

    @Test
    void getAccount() {
        PropertyAssessment result = new PropertyAssessment(property.getAccount(), 78_000, "N", loc, aC);
        assertEquals(property, result);
    }

    @Test
    void getValue() {
        PropertyAssessment result = new PropertyAssessment(12, property.getValue(), "N", loc, aC);
        assertEquals(property, result);
    }

    @Test
    void getGarage() {
        PropertyAssessment result = new PropertyAssessment(12, 78_000, property.getGarage(), loc, aC);
        assertEquals(property, result);
    }

    @Test
    void compare() {
        PropertyAssessment lower = new PropertyAssessment(12, 8000, "N", loc, aC);

        assertTrue(property.compareTo(lower)>0);
        assertTrue(lower.compareTo(property)<0);
        assertEquals(0, property.compareTo(property));
    }

    @Test
    void testEquals() {
        //reflexive
        assertEquals(property, property);

        //symmetric
        PropertyAssessment secondProperty = new PropertyAssessment(12, 78_000, "N", loc, aC);
        assertEquals(property, secondProperty);
        assertEquals(secondProperty, property);

        //transitive
        PropertyAssessment thirdProperty = new PropertyAssessment(12, 78_000, "N", loc, aC);
        assertEquals(secondProperty, thirdProperty);
        assertEquals(property, thirdProperty);

        //false results
        PropertyAssessment newProperty = new PropertyAssessment(1, 7000, "N", loc, aC);
        assertNotEquals(null, newProperty);
        assertNotEquals(property, newProperty);
        int testClass = 9;
        assertNotEquals(property, testClass);
    }

    @Test
    void testHashCode() {
        int expected = property.hashCode();
        PropertyAssessment secondProperty = new PropertyAssessment(12, 78_000, "N", loc, aC);
        int result = secondProperty.hashCode();
        assertEquals(expected, result);

        PropertyAssessment newProperty = new PropertyAssessment(12, 7000, "N", loc, aC);
        assertNotEquals(expected, newProperty.hashCode());
    }

    @Test
    void testToString() {
        String expected = """
                Account = 12
                Address =\s
                Assessed value = $78,000
                Assessment class = [Residential 100%]
                Neighbourhood =\s
                Location = (90.0 -17.9)""";
        String emptyProperty = """
                Account = 0
                Address =\s
                Assessed value = $0
                Assessment class = null
                Neighbourhood =\s
                Location =\s""";

        PropertyAssessment empty = new PropertyAssessment();

        assertEquals(expected, property.toString());
        assertEquals(emptyProperty, empty.toString());
    }
}