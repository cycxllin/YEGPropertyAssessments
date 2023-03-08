package main.java.DAO;

import main.java.classes.PropertyAssessment;

import java.util.List;

public interface PropertyAssessmentDAO {
    PropertyAssessment getByAccountNumber(int accountNumber);
    List<PropertyAssessment> getByNeighbourhood(String neighbourhood);
    List<PropertyAssessment> getByAssessmentClass(String assessmentClass);
    List<PropertyAssessment> getAllProperties();
    List<PropertyAssessment> getByAddress(String suite, int housenumber, String streetName);
    List<PropertyAssessment> getBetweenValues(Integer min, Integer max);
}
