package main.java.DAO;

import main.java.classes.CSVUtil;
import main.java.classes.PropertyAssessment;
import main.java.classes.PropertyAssessments;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvPropertyAssessmentDAO implements PropertyAssessmentDAO{

    private PropertyAssessments csvProperties;

    public CsvPropertyAssessmentDAO(PropertyAssessments csvProperties){
        this.csvProperties = csvProperties;
    }
    public CsvPropertyAssessmentDAO(String fileName){
        this.csvProperties = new PropertyAssessments(importCSVData(fileName));
    }

    public List<PropertyAssessment> getAllProperties() {
        return csvProperties.getAllProperties();
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
                filter(property -> property.getLocation().getNeighbourhood().getName().equalsIgnoreCase(neighbourhood))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyAssessment> getByAssessmentClass(String assessmentClass) {
        return csvProperties.getAllProperties().stream().
                filter(property -> property.getAssessmentClasses().hasClass(assessmentClass))
                .collect(Collectors.toList());
    }
}
