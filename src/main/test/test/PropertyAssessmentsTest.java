package test;

import main.java.classes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PropertyAssessmentsTest {
    public PropertyAssessments properties;
    public PropertyAssessment p1, p2, p3, p4, p5;
    @BeforeEach
    void setUp() {
        p1 = new PropertyAssessment(1,21, "N", new Location(56,98),
                new AssessmentClasses(100, "Other"));
        p2 = new PropertyAssessment(1,45, "Y", new Location(21,-113.2),
                new AssessmentClasses(100, "Residential"));
        p3 = new PropertyAssessment(3,10, "N", new Location(98,6),
                new AssessmentClasses(100, "Residential"));
        p4 = new PropertyAssessment(4,90, "Y", new Location(-89,-89.08),
                new AssessmentClasses(100, "Other"));
        p5 = new PropertyAssessment(5,48, "N", new Location(3,100.36),
                new AssessmentClasses(100, "Other"));
        properties = new PropertyAssessments(new ArrayList<>(List.of(p1, p2, p3, p4, p5)));
    }

    @Test
    void testEquals() {
        //reflexive
        assertEquals(properties, properties);

        //symmetric
        PropertyAssessments secondProperties = new PropertyAssessments(new ArrayList<>(List.of(p1, p2, p3, p4, p5)));

        assertEquals(properties, secondProperties);
        assertEquals(secondProperties, properties);

        //transitive
        PropertyAssessments thirdProperties = new PropertyAssessments(new ArrayList<>(List.of(p1, p2, p3, p4, p5)));

        assertEquals(secondProperties, thirdProperties);
        assertEquals(properties, thirdProperties);

        //false results
        PropertyAssessments diffPropertyList = new PropertyAssessments(new ArrayList<>(List.of(p2, p3, p4)));
        assertNotEquals(null, diffPropertyList);
        assertNotEquals(properties, diffPropertyList);
        int testClass = 9;
        assertNotEquals(properties, testClass);
    }

    @Test
    void testHashCode() {
        int expected = properties.hashCode();
        PropertyAssessments secondProperties = new PropertyAssessments(new ArrayList<>(List.of(p1, p2, p3, p4, p5)));
        int result = secondProperties.hashCode();
        assertEquals(expected, result);

        PropertyAssessments diffPropertyList = new PropertyAssessments(new ArrayList<>(List.of(p2, p3)));
        assertNotEquals(expected, diffPropertyList.hashCode());
    }

    @Test
    void testToString() {
        PropertyAssessments evenProperties = new PropertyAssessments(new ArrayList<>(List.of(p1, p2, p4, p5)));
        String expectedOddProperties = """
                n = 5
                min = $10
                max = $90
                range = $80
                mean = $43
                median = $45""";

        String expectedEvenProperties = "n = 4\nmin = $21\nmax = $90\nrange = $69\nmean = $" + (21+45+90+48)/4
                + "\nmedian = $" + (45+48)/2;

        PropertyAssessments evenP = new PropertyAssessments(new ArrayList<>(List.of(p1, p2, p4, p2)));
        String expectedEven2 = "n = 4\nmin = $21\nmax = $90\nrange = $69\nmean = $" + (21+45+90+45)/4
                + "\nmedian = $" + 45;

        assertEquals(expectedOddProperties, properties.toString());
        assertEquals(expectedEvenProperties, evenProperties.toString());
        assertEquals(expectedEven2, evenP.toString());

    }

    @Test
    void setProperties() {
        PropertyAssessments newProperties = new PropertyAssessments(new ArrayList<>(List.of(p1, p2, p4, p5)));
        List<PropertyAssessment> propertyList = new ArrayList<>(List.of(p1, p2, p4, p5));
        properties.setProperties(propertyList);

        assertEquals(properties, newProperties);

    }
}