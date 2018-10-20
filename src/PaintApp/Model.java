package PaintApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Model {

    private ObservableList<String> shapeChoices = FXCollections.observableList(new ArrayList<>());
    private ObservableList<Shape> shapeList = FXCollections.observableList(new ArrayList<>());

    public ObservableList<Shape> getShapeList() {
        return shapeList;
    }
    public void setShapeList(ObservableList<Shape> shapeList) {
        this.shapeList = shapeList;
    }

    public ObservableList<String> getShapeChoices() {
        return shapeChoices;
    }
    public void setShapeChoices(ObservableList<String> shapeChoices) {
        this.shapeChoices = shapeChoices;
    }
}
