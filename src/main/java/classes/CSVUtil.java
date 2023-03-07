package main.java.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods for parsing info from csv strings
 */
public class CSVUtil {
    /**
     * Reads info from file into property assessments objects
     * @param fileName string
     * @return list of property assessment objects
     */
    public static List<PropertyAssessment> importData(String fileName) {
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
                PropertyAssessment p = loadInfoIntoProperty(info);
                properties.add(p);
            }
        }
        catch (IOException e){
            System.out.println("Error: can't open file " + fileName);
        }
        return properties;
    }

    public static List<PropertyAssessment> parseCSVString(BufferedReader reader) throws IOException {
        ArrayList<PropertyAssessment> properties = new ArrayList<>();
        String line;

        // Skip the header
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] info = line.split(",");
            PropertyAssessment p = CSVUtil.loadInfoIntoProperty(info);
            properties.add(p);
        }

        reader.close();

        return properties;
    }

    /**
     * loads PropertyAssessment information from a string into a PropertyAssessment Object
     * @param info - String of information on property
     * @return PropertyAssessment Object
     */
    public static PropertyAssessment loadInfoIntoProperty(String[] info){
        ArrayList<String> scrubbedInfo = new ArrayList<>();

        //remove all " if present
        for (String str:info) {
            scrubbedInfo.add(str.replaceAll("\"", ""));
        }

        int acc = intFromString(scrubbedInfo.get(0));
        String suite = scrubbedInfo.get(1);
        int hNum = intFromString(scrubbedInfo.get(2));
        String sName = scrubbedInfo.get(3);
        String gar = scrubbedInfo.get(4);
        int nId = intFromString(scrubbedInfo.get(5));
        String nName = scrubbedInfo.get(6);
        String ward = scrubbedInfo.get(7);
        int val = intFromString(scrubbedInfo.get(8));
        double lat = Double.parseDouble(scrubbedInfo.get(9));
        double lon = Double.parseDouble(scrubbedInfo.get(10));
        int per1 = intFromString(scrubbedInfo.get(12));
        int per2 = intFromString(scrubbedInfo.get(13));
        int per3 = intFromString(scrubbedInfo.get(14));

        //load assessment classes
        AssessmentClasses aC = null;

        if(scrubbedInfo.size() == 16){
            String cl1 = scrubbedInfo.get(15);
            aC = new AssessmentClasses(per1, cl1);
        }else if (scrubbedInfo.size() == 17) {
            String cl1 = scrubbedInfo.get(15);
            String cl2 = scrubbedInfo.get(16);
            aC = new AssessmentClasses(per1, per2, cl1, cl2);
        }else if (scrubbedInfo.size() == 18) {
            String cl1 = scrubbedInfo.get(15);
            String cl2 = scrubbedInfo.get(16);
            String cl3 = scrubbedInfo.get(17);
            aC = new AssessmentClasses(per1, per2, per3, cl1, cl2, cl3);
        }

        return new PropertyAssessment(acc, val, gar, new Location(lat, lon, new Address(suite, hNum, sName), new Neighbourhood(nId, nName, ward)), aC);
    }

    /**
     * change string into int or 0 if string is empty
     * @param str - String
     * @return int
     */
    private static int intFromString(String str){
        if (str.equals("")){
            return 0;
        }
        return Integer.parseInt(str);
    }
}
