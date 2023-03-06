package main.java.applications;

import main.java.classes.CSVUtil;
import main.java.classes.PropertyAssessment;
import main.java.classes.PropertyAssessments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Lab2Main {
    public static void main(String[] args) throws IOException {
        //get filename
        String filename = null;

        while (filename == null){
             filename = getUserInputString("CSV Filename: ");
        }

        //here when valid input entered
        ArrayList<PropertyAssessment> properties = (ArrayList<PropertyAssessment>) CSVUtil.importData(filename);

        PropertyAssessments edmonton = new PropertyAssessments(properties);
        System.out.println(edmonton);

        //get menu choices from user until quit is requested
        String choice;
        String menu = "1. Display property assessment data by account number\n2. Display statistics by Neighbourhood\n3. Quit\n";
        choice = getUserInputString(menu);

        while (!choice.equals("3")){
            if (choice.equals("1")){
                String account = getUserInputString("Find a property assessment by account number: ");
                PropertyAssessment p = null;
                try{
                    p = edmonton.getPropertyInfo(Integer.parseInt(account));
                }catch(NumberFormatException e){
                    System.out.println("Error: invalid account number...\nSorry, Account number not found");
                }
                if (p != null && p.getAccount() != 0){
                    System.out.println(p);
                }else{
                    System.out.println("Sorry, account number not found");
                }
            }else if (choice.equals("2")){
                String neighName = getUserInputString("Neighbourhood: ");
                List<PropertyAssessment> neighProperties = edmonton.getPropertiesByNeighbourhood(neighName);

                if (neighProperties.size() == 0){
                    System.out.println("Data not found");
                }else{
                    PropertyAssessments neighbourhood = new PropertyAssessments(neighProperties);
                    System.out.println(neighbourhood);
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