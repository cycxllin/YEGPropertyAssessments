import main.java.classes.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CSVUtilTest {

    @Test
    void testImportData() {
        ArrayList<PropertyAssessment> imported = (ArrayList<PropertyAssessment>) CSVUtil.importData("TestImport.csv");
        ArrayList<PropertyAssessment> expected = new ArrayList<>();
        expected.add(new PropertyAssessment(1024009, 0, "N", new Location(53.540685777270600, -113.442892320377,
                new Address("",0,""), new Neighbourhood(6730,"TERRACE HEIGHTS",
                "Métis Ward")), new AssessmentClasses(100, "COMMERCIAL")));

        expected.add(new PropertyAssessment(1210434, 0, "N", new Location(53.4705126081889, -113.48695021694400,
                new Address("",3650,"99 STREET NW"), new Neighbourhood(6700,"STRATHCONA INDUSTRIAL PARK",
                "Karhiio Ward")), new AssessmentClasses(100, 1, "COMMERCIAL", "OTHER")));

        expected.add(new PropertyAssessment(1104066, 0, "N", new Location(53.71222469377190, -113.32624739031500,
                new Address("",25104,"17 STREET NE"), new Neighbourhood(2681,"EDMONTON ENERGY AND TECHNOLOGY PARK",
                "Dene Ward")), new AssessmentClasses(100, 1, 1, "FARMLAND", "OTHER", "OTHER")));

        assertEquals(expected, imported);

        ArrayList<PropertyAssessment> testCatch = (ArrayList<PropertyAssessment>) CSVUtil.importData("invalid");
        ArrayList<PropertyAssessment> empty = new ArrayList<>();
        assertEquals(empty, testCatch);

    }

    @Test
    void parseCSVString() {
    }

    @Test
    void loadInfoIntoProperty() {
    }
}