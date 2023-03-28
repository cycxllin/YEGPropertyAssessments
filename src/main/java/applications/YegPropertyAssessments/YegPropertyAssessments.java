package main.java.applications.YegPropertyAssessments;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.MapComponentInitializedListener;
import com.dlsc.gmapsfx.javascript.object.*;
//import com.dlsc.gmapsfx.javascript.object.MapType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class YegPropertyAssessments extends Application implements MapComponentInitializedListener {

    GoogleMapView mapView;
    GoogleMap map;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(YegPropertyAssessments.class.getResource("/PropertyAsessmentsDataView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Edmonton Property Assessments");
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
        LatLong joeSmithLocation = new LatLong(53.5471, -113.5064);

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
        Marker joeMarker = makeMarker(joeSmithLocation);
        map.addMarker( joeMarker);

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2>Joe Smith</h2>"
                + "Current Location: MacEwan<br>"
                + "Popular with students" );

        InfoWindow joeWindow = new InfoWindow(infoWindowOptions);
        joeWindow.open(map, joeMarker);
    }

    /**
     * Creates a marker placed at the specified location
     * @param location a LatLong object containing the latlong of the marker you want to use
     * @return the created marker object
     */
    public Marker makeMarker(LatLong location){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        return new Marker(markerOptions);
    }
}
