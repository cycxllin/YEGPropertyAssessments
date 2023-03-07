package main.java.DAO;

import main.java.classes.CSVUtil;
import main.java.classes.PropertyAssessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiPropertyAssessmentDAO implements PropertyAssessmentDAO{
    String endpoint = "https://data.edmonton.ca/resource/q7d6-ambg.csv";

    @Override
    public PropertyAssessment getByAccountNumber(int accountNumber) {
        PropertyAssessment p = new PropertyAssessment();

        String account = URLEncoder.encode(String.valueOf(accountNumber), StandardCharsets.UTF_8);

        String url = endpoint + "?account_number=" + account;

        List<PropertyAssessment> properties = getProperties(url);
        if (properties.size()>0){
            p = properties.get(0);
        }
        return p;
    }

    @Override
    public List<PropertyAssessment> getByNeighbourhood(String neighbourhood) {
        neighbourhood = URLEncoder.encode(neighbourhood.toUpperCase(), StandardCharsets.UTF_8);

        String url = endpoint + "?neighbourhood=" + neighbourhood;

        return getProperties(url);
    }

    @Override
    public List<PropertyAssessment> getByAssessmentClass(String assessmentClass) {
        assessmentClass = URLEncoder.encode(assessmentClass.toUpperCase(), StandardCharsets.UTF_8);

        String url = endpoint + "?$where=mill_class_1='" + assessmentClass
                + URLEncoder.encode("' OR mill_class_2='", StandardCharsets.UTF_8) + assessmentClass
                + URLEncoder.encode("' OR mill_class_3='", StandardCharsets.UTF_8) + assessmentClass + "'";

        return getProperties(url);
    }

    private List<PropertyAssessment> getProperties(String url) {
        List<PropertyAssessment> properties = null;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            BufferedReader reader = new BufferedReader(new StringReader(response.body()));

            properties = CSVUtil.parseCSVString(reader);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error accessing https://data.edmonton.ca/");
        }

        return properties;
    }

    @Override
    public List<PropertyAssessment> getAllProperties() {
        String url = endpoint + "?$limit=100";
        return getProperties(url);
    }

    @Override
    public List<PropertyAssessment> getByAddress(String address) {
        return null;
    }
}
