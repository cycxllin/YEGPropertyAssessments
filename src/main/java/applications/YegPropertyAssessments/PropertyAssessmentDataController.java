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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyAssessmentDataController implements Initializable{
    //region FXMLvariables
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
    private ComboBox<String> assessmentClassComboBox;
    @FXML
    private Button readDataSourceButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button resetButton;

    @FXML
    private TextField accountNumberInput;
    @FXML
    private TextField suiteInput;
    @FXML
    private TextField houseNumberInput;
    @FXML
    private TextField streetInput;
    @FXML
    private TextField neighbourhoodInput;
    @FXML
    private TextField minValueInput;
    @FXML
    private TextField maxValueInput;
    //endregion
    PropertyAssessmentDAO dao;
    ObservableList<PropertyAssessment> properties;
    ObservableList<String> assessmentClasses;
    String filename = "Property_Assessment_Data_2022.csv";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> dataSources = FXCollections.observableArrayList(
                Arrays.asList("CSV File", "Edmonton's Open Data Portal"));
        dataSourceComboBox.setItems(dataSources);

        //When read data clicked, get selected index from combobox, use index to load source data
        readDataSourceButton.setOnAction(event ->
                loadSourceData(dataSourceComboBox.getSelectionModel().getSelectedIndex()));

        //When search button clicked, use dao to get account number
        searchButton.setOnAction(event -> search());

        //Reset search fields
        resetButton.setOnAction(event -> resetSearchFilters());

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

    /**
     * Given 0 or 1, creates DAO and loads info appropriately
     * @param dataSource Integer
     */
    private void loadSourceData(Integer dataSource){
        if (dataSource == 0) { //dataSource is CSV
            dao = new CsvPropertyAssessmentDAO(filename);
        } else if (dataSource == 1) { //datasource is API
            dao = new ApiPropertyAssessmentDAO();
        }
        properties = FXCollections.observableArrayList(dao.getAllProperties());

        loadDataTable();
        enableSearchresetButtons();
        setAssessmentClasses();
    }

    /**
     * creates set of assessment classes based on current properties observable list and sets respective combobox to list
     */
    private void setAssessmentClasses() {
        Set<String> assessmentClassNames = dao.getAllProperties().stream()
                .map(PropertyAssessment::getAssessmentClasses)
                .map(AssessmentClasses::getClassNames)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        assessmentClasses = FXCollections.observableArrayList(assessmentClassNames);
        assessmentClassComboBox.setItems(assessmentClasses);
    }

    private void enableSearchresetButtons(){
        searchButton.setDisable(false);
        resetButton.setDisable(false);
    }

    /**
     * Populates data table with contents of properties
     */
    private void loadDataTable(){
        assessmentDataTable.setItems(properties);

        accountTableColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        addressTableColumn.setCellValueFactory(property ->
                new SimpleObjectProperty<>(property.getValue().getLocation().getAddress()));
        assessedValueTableColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        assessmentClassesTableColumn.setCellValueFactory(property ->
                new SimpleObjectProperty<>(property.getValue().getAssessmentClasses()));
        neighbourhoodTableColumn.setCellValueFactory(property ->
                new SimpleObjectProperty<>(property.getValue().getLocation().getNeighbourhood()));
        locationTableColumn.setCellValueFactory(property ->
                new SimpleStringProperty(property.getValue().getLocation().getLatLon()));
    }

    private void search(){
        //TODO cascade search results for advanced filtering
        List<PropertyAssessment> searchProperties = List.of(new PropertyAssessment());

        List<PropertyAssessment> account,address,neighbourhood,assessmentClass,betweenValues;

        List<List<PropertyAssessment>> allProps = new ArrayList<>();

        //which fields am I considering?
        //which field makes smallest list?
        //on smallest list, apply other lists until done or smallest list is empty

        if (!accountNumberInput.getText().isEmpty()) {
            account = List.of(dao.getByAccountNumber(convertIputStringToInteger(accountNumberInput.getText())));
            allProps.add(account);
        }
        if(!streetInput.getText().isEmpty()){
            address = searchByAddress();
            allProps.add(address);
        }
        if (!neighbourhoodInput.getText().isEmpty()) {
            neighbourhood = dao.getByNeighbourhood(neighbourhoodInput.getText().toUpperCase());
            allProps.add(neighbourhood);
        }
        if (assessmentClassComboBox.getValue() != null){
            assessmentClass = dao.getByAssessmentClass(assessmentClassComboBox.getSelectionModel().getSelectedItem());
            allProps.add(assessmentClass);
        }
        if (!minValueInput.getText().isEmpty() || !maxValueInput.getText().isEmpty()) {
            betweenValues = dao.getBetweenValues(convertIputStringToInteger(minValueInput.getText()),
                    convertIputStringToInteger(maxValueInput.getText()));
            allProps.add(betweenValues);
        }

        //using for loop in order to terminate when list is empty
        if (allProps.size() > 0) { //there are search terms
            searchProperties = allProps.get(0);

            if (allProps.size() > 1) {
                for (int i = 1; i < allProps.size(); i++) {
                    searchProperties = filterByList(searchProperties, allProps.get(i));
                    if (searchProperties.isEmpty()) {
                        break;
                    }
                }
            }
            loadSearchResults(searchProperties);
        }
    }

    private List<PropertyAssessment> filterByList(List<PropertyAssessment> first, List<PropertyAssessment> second){
        return first.stream()
                .filter(second::contains)
                .collect(Collectors.toList());
    }


    private void loadSearchResults(List<PropertyAssessment> searchResults){
        properties = FXCollections.observableArrayList(searchResults);

        if (properties.stream().allMatch(PropertyAssessment::emptyProperty)){
            throwAlert("Search Results", "No properties found");
        }
        else {
            loadDataTable();
        }
    }

    private List<PropertyAssessment> searchByAddress(){
        Integer houseNumber = convertIputStringToInteger(houseNumberInput.getText());
        
        //this is how no value housenumbers are stored in PropertyAssessment objects TODO test 0 with streetName
        if (houseNumber == null)
            houseNumber = 0;
        
        try {
            return dao.getByAddress(suiteInput.getText().toUpperCase(), houseNumber, streetInput.getText().toUpperCase());
        } catch (NullPointerException e){
            return List.of(new PropertyAssessment());
        }
    }

    private Integer convertIputStringToInteger(String valueString){
        Integer value = null;

        if (valueString.isEmpty()){
            return value;
        } else {
            try {
                value = Integer.parseInt(valueString);
            } catch (NumberFormatException e) {
                throwAlert("Number Format Error", """
                        The following fields must consist only of digits 0-9:
                        Account Number
                        House Number
                        Assessed Values""");
            }
        }
        return value;
    }

    private void throwAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void resetSearchFilters(){
        accountNumberInput.clear();
        suiteInput.clear();
        houseNumberInput.clear();
        streetInput.clear();
        neighbourhoodInput.clear();
        minValueInput.clear();
        maxValueInput.clear();

        assessmentClassComboBox.setValue(null);
        loadSourceData(dataSourceComboBox.getSelectionModel().getSelectedIndex());
    }
}
