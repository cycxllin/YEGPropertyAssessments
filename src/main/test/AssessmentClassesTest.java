import main.java.classes.AssessmentClasses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssessmentClassesTest {
    public AssessmentClasses aC3;
    public AssessmentClasses aC2;
    public AssessmentClasses aC1;
    public AssessmentClasses aC0;
    @BeforeEach
    void setUp() {
        aC3 = new AssessmentClasses(1,9,90, "residential", "commercial", "other");
        aC2 = new AssessmentClasses(99, 1, "Residential", "Other");
        aC1 = new AssessmentClasses(100, "Farmland");
        aC0 = new AssessmentClasses();
    }

    @Test
    void getClassPer1() {
        AssessmentClasses result = new AssessmentClasses(aC1.getClassPer1(),"Farmland");
        assertEquals(aC1, result);
    }

    @Test
    void getClassPer2() {
        AssessmentClasses result = new AssessmentClasses(99, aC2.getClassPer2(),"Residential", "Other");
        assertEquals(aC2, result);
    }

    @Test
    void getClassPer3() {
        AssessmentClasses result = new AssessmentClasses(1,9, aC3.getClassPer3(), "residential",
                "commercial", "other");
        assertEquals(aC3, result);
    }

    @Test
    void getClass1() {
        AssessmentClasses result = new AssessmentClasses(100,aC1.getClass1());
        assertEquals(aC1, result);
    }

    @Test
    void getClass2() {
        AssessmentClasses result = new AssessmentClasses(99,1,"Residential", aC2.getClass2());
        assertEquals(aC2, result);
    }

    @Test
    void getClass3() {
        AssessmentClasses result = new AssessmentClasses(1,9, 90, "residential",
                "commercial", aC3.getClass3());
        assertEquals(aC3, result);
    }

    @Test
    void setClassPer1() {
        AssessmentClasses expected = new AssessmentClasses(90,"Farmland");
        aC1.setClassPer1(90);
        assertEquals(expected, aC1);
    }

    @Test
    void setClassPer2() {
        AssessmentClasses expected = new AssessmentClasses(99, 30, "Residential", "Other");
        aC2.setClassPer2(30);
        assertEquals(expected, aC2);
    }

    @Test
    void setClassPer3() {
        AssessmentClasses expected = new AssessmentClasses(1,9,100, "residential",
                "commercial", "other");
        aC3.setClassPer3(100);
        assertEquals(expected, aC3);
    }

    @Test
    void setClass1() {
        AssessmentClasses expected = new AssessmentClasses(100,"Other");
        aC1.setClass1("Other");
        assertEquals(expected, aC1);
    }

    @Test
    void setClass2() {
        AssessmentClasses expected = new AssessmentClasses(99, 1, "Residential", "Farmland");
        aC2.setClass2("Farmland");
        assertEquals(expected, aC2);
    }

    @Test
    void setClass3() {
        AssessmentClasses expected = new AssessmentClasses(1,9,90, "residential",
                "commercial", "Misc");
        aC3.setClass3("Misc");
        assertEquals(expected, aC3);
    }

    @Test
    void testEquals() {
        //reflexive
        assertEquals(aC0, aC0);

        //symmetric
        AssessmentClasses secondAC1 = new AssessmentClasses(100,"Farmland");
        assertEquals(aC1, secondAC1);
        assertEquals(secondAC1, aC1);

        //transitive
        AssessmentClasses thirdAC1 = new AssessmentClasses(100,"Farmland");
        assertEquals(secondAC1, thirdAC1);
        assertEquals(aC1, thirdAC1);

        //false results
        AssessmentClasses newAC = new AssessmentClasses(0,"Nothing");
        assertNotEquals(null, aC0);
        assertNotEquals(aC1, newAC);
        int testClass = 9;
        assertNotEquals(aC1, testClass);
    }

    @Test
    void testHashCode() {
        int expected = aC2.hashCode();
        AssessmentClasses secondAC2 = new AssessmentClasses(99, 1, "Residential", "Other");
        int result = secondAC2.hashCode();
        assertEquals(expected, result);
        assertNotEquals(expected, aC3.hashCode());
    }

    @Test
    void testToString() {
        String expected3 = "[residential 1%, commercial 9%, other 90%]";
        String expected2 = "[Residential 99%, Other 1%]";
        String expected1 = "[Farmland 100%]";
        String expected0 = "[]";

        assertEquals(expected3, aC3.toString());
        assertEquals(expected2, aC2.toString());
        assertEquals(expected1, aC1.toString());
        assertEquals(expected0, aC0.toString());
    }

    @Test
    void testHasClass(){
        assertTrue(aC3.hasClass("residential"));
        assertTrue(aC2.hasClass("residential"));
        assertTrue(aC1.hasClass("FARMLAND"));
        assertFalse(aC0.hasClass("other"));
    }
}