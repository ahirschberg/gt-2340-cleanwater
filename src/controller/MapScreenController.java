package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import fxapp.MainFXApplication;
import fxapp.ReportManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Report;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * Handles the water availability screen.
 */
public class MapScreenController implements Initializable,
        MapComponentInitializedListener {
    private MainFXApplication main;

    @FXML
    private final GoogleMapView mapView;

    private GoogleMap map;

    /**
     * Called automatically on view initialization
     * sets up map view
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }

    /**
     * initializes map
     */
    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        //set up the center location for the map
        LatLong center = new LatLong(33.7756, -84.3963);

        options.center(center)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(true)
                .rotateControl(true)
                .scaleControl(false)
                .streetViewControl(true)
                .zoomControl(true)
                .mapType(MapTypeIdEnum.ROADMAP);

        map = mapView.createMap(options);
    }

    /**
     * Updates the map with the location of each water report
     */
    public void refreshMarkers() {
        LatLong center = new LatLong(33.7756, -84.3963);
        ReportManager rm = main.getReportManager();
        Stream<Report> reportStream = rm.getAllReports();
        reportStream.forEach(r -> {
            MarkerOptions mo = new MarkerOptions();
            mo.position(r.getLocation().toLatLong());
            mo.title(r.toString());
            Marker marker = new Marker(mo);
            map.addMarker(marker);
            map.addUIEventHandler(marker, UIEventType.click,
                (JSObject obj) -> main.setReportDetailsScene(r));
        });
    }

    /**
     * Gives the controller a reference to the main app
     *
     * @param main the main app
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Called when user presses back button.
     */
    @FXML
    public void onBackPressed() {
        main.setMainScene();
    }
}
