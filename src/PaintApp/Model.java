package PaintApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Model {

    private ObservableList<Point2D> observablePoints = FXCollections.observableList(new ArrayList<>());
    private ObservableList<String> observableShapes = FXCollections.observableList(new ArrayList<>());

    public Model() {

    }

    public ObservableList<Point2D> getObservablePoints() {
        return observablePoints;
    }

    public void setObservablePoints(ObservableList<Point2D> observablePoints) {
        this.observablePoints = observablePoints;
    }

    public ObservableList<String> getObservableShapes() {
        return observableShapes;
    }

    public void setObservableShapes(ObservableList<String> observableShapes) {
        this.observableShapes = observableShapes;
    }
}
