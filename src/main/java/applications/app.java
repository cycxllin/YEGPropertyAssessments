package main.java.applications;

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

public class app {
    public static void main(String[] args) throws IOException {
        String endpoint = "https://data.edmonton.ca/resource/q7d6-ambg.csv";
        String assessmentClass = "commercial";

        List<PropertyAssessment> properties = null;
        assessmentClass = URLEncoder.encode(assessmentClass.toUpperCase(), StandardCharsets.UTF_8);

        String url = endpoint + "?$where=mill_class_1='" + assessmentClass
                + URLEncoder.encode("' OR mill_class_2='", StandardCharsets.UTF_8) + assessmentClass
                + URLEncoder.encode("' OR mill_class_3='", StandardCharsets.UTF_8) + assessmentClass + "'";


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            BufferedReader reader = new BufferedReader(new StringReader(response.body()));

            properties = CSVUtil.parseCSVString(reader);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error accessing https://data.edmonton.ca/");
        }

        System.out.println(properties);
    }
}
