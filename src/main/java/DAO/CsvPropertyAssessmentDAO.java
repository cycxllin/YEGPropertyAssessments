package main.java.DAO;

import main.java.classes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvPropertyAssessmentDAO implements PropertyAssessmentDAO{

    private final PropertyAssessments csvProperties;

    public CsvPropertyAssessmentDAO(PropertyAssessments csvProperties){
        this.csvProperties = csvProperties;
    }
    public CsvPropertyAssessmentDAO(String fileName){
        this.csvProperties = new PropertyAssessments(importCSVData(fileName));
    }

    public List<PropertyAssessment> getAllProperties() {
        return csvProperties.getAllProperties();
    }

    @Override
    public List<PropertyAssessment> getByAddress(String suite, int housenumber, String streetName) {
        Address theAddress = new Address(suite, housenumber, streetName);
        if (suite.isEmpty() && housenumber == 0){
            return csvProperties.getAllProperties().stream()
                    .filter(property -> property.getLocation().getAddress().getStreetName().contains(theAddress.getStreetName()))
                    .collect(Collectors.toList());
        } else if (suite.isEmpty()) { // and there is a houseNumber
            return csvProperties.getAllProperties().stream()
                    .filter(property -> property.getLocation().getAddress().getStreetName().equalsIgnoreCase(theAddress.getStreetName()))
                    .filter(property -> property.getLocation().getAddress().getHouseNumber() == theAddress.getHouseNumber())
                    .collect(Collectors.toList());
        }

        return csvProperties.getAllProperties().stream()
                .filter(property -> property.getLocation().getAddress().equals(theAddress))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyAssessment> getBetweenValues(Integer min, Integer max) {
        if (min == null && max == null) //no search params
            return new ArrayList<>();

        else if (min == null) { //max != null
            return csvProperties.getAllProperties().stream()
                    .filter(property -> property.getValue() <= max)
                    .collect(Collectors.toList());

        } else if (max == null) { //min != null
            return csvProperties.getAllProperties().stream()
                    .filter(property -> property.getValue() >= min)
                    .collect(Collectors.toList());
        }
        //here when both min & max != null
        return csvProperties.getAllProperties().stream()
                .filter(property -> property.getValue() >= min)
                .filter(property -> property.getValue() <= max)
                .collect(Collectors.toList());
    }

    public static List<PropertyAssessment> importCSVData(String fileName) {
        ArrayList<PropertyAssessment> properties = new ArrayList<>();
        String line;

        Path path = Paths.get(fileName);

        // Create a stream to read the CSV file
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            // Skip the header
            reader.readLine();

            //read each line into an array of strings, load info into property and add to list
            while((line = reader.readLine()) != null){
                String[] info = line.split(",");
                PropertyAssessment p = CSVUtil.loadInfoIntoProperty(info);
                properties.add(p);
            }
        }
        catch (IOException e){
            System.out.println("Error: can't open file " + fileName);
        }
        return properties;
    }

    @Override
    public PropertyAssessment getByAccountNumber(int accountNumber) {
        return csvProperties.getPropertyInfo(accountNumber);
    }

    @Override
    public List<PropertyAssessment> getByNeighbourhood(String neighbourhood) {
        return csvProperties.getAllProperties().stream().
                filter(property -> property.getLocation().getNeighbourhood().getName().contains(neighbourhood))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyAssessment> getByAssessmentClass(String assessmentClass) {
        return csvProperties.getAllProperties().stream().
                filter(property -> property.getAssessmentClasses().hasClass(assessmentClass))
                .collect(Collectors.toList());
    }
}
