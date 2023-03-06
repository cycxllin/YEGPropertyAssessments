package main.java.applications.YegPropertyAssessments;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.DAO.*;
import main.java.classes.*;

import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

public class PropertyAssessmentDataController implements Initializable{
    @FXML
    private TableView<PropertyAssessment> assessmentDataTable;
    @FXML
    private TableColumn<PropertyAssessment, Integer> accountTableColumn;
    @FXML
    private TableColumn<PropertyAssessment, Address> addressTableColumn;
    @FXML
    private TableColumn<PropertyAssessment, Integer> assessedValueTableColumn;
    @FXML
    private TableColumn<PropertyAssessment, AssessmentClasses> assessmentClassesTableColumn;
    @FXML
    private TableColumn<PropertyAssessment, Neighbourhood> neighbourhoodTableColumn;
    @FXML
    private TableColumn<PropertyAssessment, String> locationTableColumn;
    @FXML
    private ComboBox<String> dataSourceComboBox;
    @FXML
    private Button readDataSourceButton;

    ObservableList<String> dataSources;
    PropertyAssessmentDAO dao;
    ObservableList<PropertyAssessment> properties;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataSources = FXCollections.observableArrayList(Arrays.asList("CSV File", "Edmonton's Open Data Portal"));
        dataSourceComboBox.setItems(dataSources);

        //get selected index from combo box, use index to load source data
        readDataSourceButton.setOnAction(event -> loadSourceData(dataSourceComboBox.getSelectionModel().getSelectedIndex()));

        //for number currency format
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
    private void loadSourceData(Integer dataSource){
        if (dataSource == null){
            return;
        }else if (dataSource == 0) { //dataSource is CSV
            dao = new CsvPropertyAssessmentDAO("Property_Short.csv");
            properties = FXCollections.observableArrayList(dao.getAllProperties());
        } else if (dataSource == 1) { //datasource is API
            dao = new ApiPropertyAssessmentDAO();
            properties = FXCollections.observableArrayList(dao.getAllProperties());
        }
        assessmentDataTable.setItems(properties);

        accountTableColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        addressTableColumn.setCellValueFactory(property -> new SimpleObjectProperty<>(property.getValue().getLocation().getAddress()));
        assessedValueTableColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        assessmentClassesTableColumn.setCellValueFactory(property -> new SimpleObjectProperty<>(property.getValue().getAssessmentClasses()));
        neighbourhoodTableColumn.setCellValueFactory(property -> new SimpleObjectProperty<>(property.getValue().getLocation().getNeighbourhood()));
        locationTableColumn.setCellValueFactory(property -> new SimpleStringProperty(property.getValue().getLocation().getLatLon()));
    }
}
