import main.java.DAO.*;
import main.java.classes.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvPropertyAssessmentDAOTest {
    public CsvPropertyAssessmentDAO csvDAO;

    @BeforeEach
    void setUp() {
        csvDAO = new CsvPropertyAssessmentDAO(new PropertyAssessments(
                CSVUtil.importData("TestGetProperties.csv")));
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
        String expected = "n = 10\nmin = $1,500\nmax = $1,530,000\nrange = $1,528,500\nmean = $762,650"
                + "\nmedian = $869,250";

        assertEquals(expected, riversedge.toString());

    }

    @Test
    void getByAssessmentClass() {
        PropertyAssessments farmland = new PropertyAssessments(csvDAO.getByAssessmentClass("Farmland"));

        String expected = "n = 4\nmin = $1,500\nmax = $483,000\nrange = $481,500\nmean = $170,625"
                + "\nmedian = $99,000";

        assertEquals(expected, farmland.toString());
    }

}