package main.java.controllers;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import main.java.DAO.*;
import main.java.classes.*;
import main.java.models.*;

import java.net.*;
import java.text.*;
import java.util.*;

public class PropertyAssessmentDataController implements Initializable{
    public TableView<PropertyAssessment> assessmentDataTable;
    public TableColumn<PropertyAssessment, Integer> accountTableColumn;
    public TableColumn<PropertyAssessment, Address> addressTableColumn;
    public TableColumn<PropertyAssessment, Integer> assessedValueTableColumn;
    public TableColumn<PropertyAssessment, AssessmentClasses> assessmentClassesTableColumn;
    public TableColumn<PropertyAssessment, Neighbourhood> neighbourhoodTableColumn;
    public TableColumn<PropertyAssessment, String> locationTableColumn;

    PropertyAssessmentDAO dao;
    ObservableList<PropertyAssessment> properties;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //sidebar view will have csv or api selected, main handles interactions between controllers

        //for csv;
        dao = new CsvPropertyAssessmentDAO("Property_Short.csv");
        properties = FXCollections.observableArrayList(dao.getAllProperties());
        assessmentDataTable.setItems(properties);

        //dao = new ApiPropertyAssessmentDAO();
        //properties = FXCollections.observableArrayList(dao.getProperties("https://data.edmonton.ca/resource/q7d6-ambg.csv?$limit=100"));
        //assessmentDataTable.setItems(properties);


        accountTableColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        addressTableColumn.setCellValueFactory(property -> new SimpleObjectProperty<>(property.getValue().getLocation().getAddress()));
        assessedValueTableColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        assessmentClassesTableColumn.setCellValueFactory(property -> new SimpleObjectProperty<>(property.getValue().getAssessmentClasses()));
        neighbourhoodTableColumn.setCellValueFactory(property -> new SimpleObjectProperty<>(property.getValue().getLocation().getNeighbourhood()));
        locationTableColumn.setCellValueFactory(property -> new SimpleStringProperty(property.getValue().getLocation().getLatLon()));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        currencyFormat.setMaximumFractionDigits(0);
        assessedValueTableColumn.setCellFactory(tc -> new TableCell<>() {

            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(value));
                }
            }
        });

    }
}
