package test;

import main.java.DAO.*;
import main.java.classes.*;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvPropertyAssessmentDAOTest {
    public CsvPropertyAssessmentDAO csvDAO;

    @BeforeEach
    void setUp() {
        //csvDAO = new CsvPropertyAssessmentDAO(new PropertyAssessments(
                //PropertiesCSVUtil.importData("TestGetProperties.csv")));
    }

    @Test
    void getByAccountNumber() {
        PropertyAssessment expected = new PropertyAssessment(1,0,"N",new Location(0,0),
                new AssessmentClasses(100, "Other"));
        PropertyAssessment actual = csvDAO.getByAccountNumber(1);

        PropertyAssessment empty = new PropertyAssessment();
        PropertyAssessment actualEmpty = csvDAO.getByAccountNumber(0);

        assertEquals(expected.toString(), actual.toString());
        assertEquals(empty, actualEmpty);
    }

    @Test
    void getByNeighbourhood() {
        PropertyAssessments riversedge = new PropertyAssessments(csvDAO.getByNeighbourhood("river's edge"));
        String expected = """
                n = 10
                min = $1,500
                max = $1,530,000
                range = $1,528,500
                mean = $762,650
                median = $869,250""";

        assertEquals(expected, riversedge.toString());
    }

    @Test
    void getByAssessmentClass() {
        PropertyAssessments farmland = new PropertyAssessments(csvDAO.getByAssessmentClass("Farmland"));

        String expected = """
                n = 4
                min = $1,500
                max = $483,000
                range = $481,500
                mean = $170,625
                median = $99,000""";

        assertEquals(expected, farmland.toString());
    }

    @Test
    void getBetweenValues(){
        List<Integer> expected = new ArrayList<>(Arrays.asList(7000, 22500, 59000, 79500));
        List<PropertyAssessment> props = new ArrayList<>(csvDAO.getBetweenValues(7000, 100000));
        List<Integer> actual = props.stream().map(PropertyAssessment::getValue)
                        .sorted().collect(Collectors.toList());

        List<Integer> expectedMaxNull = new ArrayList<>(List.of(1530000));
        List<PropertyAssessment> props1 = new ArrayList<>(csvDAO.getBetweenValues(1520000, null));
        List<Integer> actualMaxNull = props1.stream().map(PropertyAssessment::getValue)
                .sorted().collect(Collectors.toList());

        List<Integer> expectedMinNull = new ArrayList<>(Arrays.asList(0,0));
        List<PropertyAssessment> props2 = new ArrayList<>(csvDAO.getBetweenValues(null, 1000));
        List<Integer> actualMinNull = props2.stream().map(PropertyAssessment::getValue)
                .sorted().collect(Collectors.toList());

        assertEquals(expected, actual);
        assertEquals(expectedMaxNull, actualMaxNull);
        assertEquals(expectedMinNull, actualMinNull);
    }

    @Test
    void getAddress(){
        String expected = "18407 17 AVENUE NW";
        List<PropertyAssessment> props = new ArrayList<>(csvDAO.getByAddress("",18407, "17 AVENUE NW"));
        String actual = props.get(0).getLocation().getAddress().toString();

        int lengthOnlyStreet = 4;
        List<PropertyAssessment> onlyStreetProps = new ArrayList<>(csvDAO.getByAddress("",0, "17 AVENUE NW"));
        int actualLength = onlyStreetProps.size();

        assertEquals(1, props.size());
        assertEquals(expected, actual);
        assertEquals(lengthOnlyStreet, actualLength);
    }

    @Test
    void multipleParams(){
        Map<String, String> params = new HashMap<>();
        params.put("StreetName", "Jasper Avenue NW");
        //TODO finish unit testing
        csvDAO.multipleParamaters(params);
    }
}