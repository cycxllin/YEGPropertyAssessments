package main.java.DAO;

import main.java.classes.PropertyAssessment;

import java.util.List;
import java.util.Map;

public interface PropertyAssessmentDAO {
    PropertyAssessment getByAccountNumber(int accountNumber);
    List<PropertyAssessment> getByAddress(String suite, int housenumber, String streetName);
    List<PropertyAssessment> getByNeighbourhood(String neighbourhood);
    List<PropertyAssessment> getByAssessmentClass(String assessmentClass);
    List<PropertyAssessment> getBetweenValues(Integer min, Integer max);
    List<PropertyAssessment> getAllProperties();

    /**
     * Appropriate keys are: accountNumber, suite, houseNumber, streetName, neighbourhood, assessmentClass, minValue, maxValue
     */
    List<PropertyAssessment> multipleParamaters(Map<String, String> params);

}

