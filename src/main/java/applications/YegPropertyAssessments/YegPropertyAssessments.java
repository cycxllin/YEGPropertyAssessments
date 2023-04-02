package main.java.applications.YegPropertyAssessments;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.MapComponentInitializedListener;
import com.dlsc.gmapsfx.javascript.object.*;
//import com.dlsc.gmapsfx.javascript.object.MapType;
import com.dlsc.gmapsfx.util.MarkerImageFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class YegPropertyAssessments extends Application implements MapComponentInitializedListener {

    GoogleMapView mapView;
    GoogleMap map;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        mapView = new GoogleMapView();
        mapView.addMapInitializedListener(this);
        //Creates the tab pane that will hold all the
        TabPane tabPane = new TabPane();
        //makes it so you can't close any of the tabs
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Creating the different tabs, additional tabs can be created here
        Tab propertyTab = new Tab("Properties");
        Tab businessTab = new Tab("Businesses");
        Tab mapTab = new Tab("Map");

        FXMLLoader fxmlLoader = new FXMLLoader(YegPropertyAssessments.class.getResource("/PropertyAsessmentsDataView.fxml"));

        propertyTab.setContent(fxmlLoader.load());
        fxmlLoader = new FXMLLoader(YegPropertyAssessments.class.getResource("/BusinessesDataView.fxml"));
        businessTab.setContent(fxmlLoader.load());
        mapTab.setContent(mapView);

        //Add the tabs to the tabpane then set the scene with it
        tabPane.getTabs().addAll(propertyTab, businessTab, mapTab);
        primaryStage.setTitle("Edmonton Property Assessments");
        Scene scene = new Scene(tabPane, 720, 640);
        primaryStage.setScene(scene);
        primaryStage.show();

//        //Create Google Map view object, you then must initialise a listener so it can be manipulated
//        mapView = new GoogleMapView();
//        mapView.addMapInitializedListener(this);
//
//        Scene scene = new Scene(mapView);
//
//        primaryStage.setTitle("JavaFX and Google Maps");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    /**
     * Initialises Google Map Object that calls this
     */
    @Override
    public void mapInitialized() {
        LatLong BPLocation = new LatLong(53.54571, -113.49463);
        LatLong barLocation = new LatLong(53.545468249433824, -113.49771189342023);
        LatLong alcoholLocation = new LatLong(53.54472875342302, -113.49781918178199);
        LatLong weedLocation = new LatLong(53.548090659193115, -113.50333333777968);

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        //co-ords are MacEwan this makes the map centre on MacEwan
        mapOptions.center(new LatLong(53.5471, -113.5064))
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        //Creates the map using the map options
        map = mapView.createMap(mapOptions);

        //Add markers to the map
        Marker BPMarker = makeMarker(BPLocation, "restaurant");
        map.addMarker( BPMarker);

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2>Boston Pizza</h2>"
                + "Suitable for children<br>"
                + "Cheap" );

        InfoWindow bpWindow = new InfoWindow(infoWindowOptions);
        bpWindow.open(map, BPMarker);

        Marker BarMarker = makeMarker(barLocation, "bar");
        map.addMarker( BarMarker);

        infoWindowOptions.content("<h2>A Bar</h2>"
                + "Not suitable for children<br>"
                + "Fancy" );

        InfoWindow barWindow = new InfoWindow(infoWindowOptions);
        barWindow.open(map, BarMarker);
    }

    /**
     * Creates a marker placed at the specified location
     * @param location a LatLong object containing the latlong of the marker you want to use
     * @param business indicates what kind of business it is
     *                 cannabis, bar, alcohol, restaurant
     * @return the created marker object
     */
    public Marker makeMarker(LatLong location, String business){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        File file = new File(business + ".png");
        String path = file.getAbsolutePath();
        path = path.replace("\\", "/");
        path = "file:///" + path.replace(" ", "%20");
        System.out.println(path);
        String image = MarkerImageFactory.createMarkerImage(path, "png"); //or jpg
        image = image.replace("(", "");
        image = image.replace(")", "");
        markerOptions.icon(image);
        return new Marker(markerOptions);
    }
}
