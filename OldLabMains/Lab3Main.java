package main.java.applications;

import main.java.classes.CSVUtil;
import main.java.classes.PropertyAssessment;
import main.java.classes.PropertyAssessments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Lab3Main {
    public static void main(String[] args) throws IOException {
        //get filename
        String filename = null;

        while (filename == null){
            filename = getUserInputString("CSV Filename: ");
        }

        //here when valid input entered
        ArrayList<PropertyAssessment> properties = (ArrayList<PropertyAssessment>) CSVUtil.importData(filename);

        PropertyAssessments edmonton = new PropertyAssessments(properties);

        //get menu choices from user until quit is requested
        String choice;
        String menu = "1. Display property assessment data by Class\n2. Quit\n";
        choice = getUserInputString(menu);

        while (!choice.equals("2")){
            if (choice.equals("1")){
                String assessmentClass = getUserInputString("Assessment class: ");
                List<PropertyAssessment> asessmentClassList = edmonton.getPropertiesByAssessmentClass(assessmentClass);

                if (asessmentClassList.size() == 0){
                    System.out.println("Data not found");
                }else{
                    PropertyAssessments assessmentClassProperties = new PropertyAssessments(asessmentClassList);
                    System.out.println(assessmentClassProperties);
                }
            }
            choice = getUserInputString(menu);
        }
        System.exit(0);

    }

    /**
     * Displays prompt and reads user input from console
     * @param prompt - a string to be printed to the user
     * @return input - a string of the input entered by the user
     *          null if input not read
     */
    public static String getUserInputString(String prompt) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        System.out.print(prompt);

        try {
            input = reader.readLine();
        } catch (IOException e){
            System.out.println("Failed to read input");
        }
        return input;
    }
}
