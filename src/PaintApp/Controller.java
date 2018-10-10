package PaintApp;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller {

    private Stage stage;
    Model model = new Model();
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ChoiceBox brushSize;
    @FXML
    private ChoiceBox shapes;
    @FXML
    private CheckBox eraser;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void init() {
        // Make our bindings here, everything is loaded.
        model.getObservablePoints().addListener((ListChangeListener<Point2D>) c -> draw());
        // Shapes choice box
        model.getObservableShapes().addAll("Circle","Square");
        shapes.setItems(model.getObservableShapes());
        shapes.setValue("Circle"); // default shape

        // default color
        colorPicker.setValue(Color.BLACK);

        // brush size choice box
        String j;
        for (int i = 1; i <= 20 ; i++) {
            j = Integer.toString(i);
            brushSize.getItems().add(j);
        }
        // default size
        brushSize.setValue("12");
    }

    public void canvasClicked(MouseEvent event) {
        Point2D point = new Point2D(event.getX(), event.getY());
        model.getObservablePoints().add(point);
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double size = Double.parseDouble(brushSize.getValue().toString());
        Point2D point = model.getObservablePoints().get(model.getObservablePoints().size()-1);

        if (eraser.isSelected()) {
            gc.clearRect(point.getX() - size / 2, point.getY() - size / 2, size, size);
        }
        else if (shapes.getValue().equals("Circle")) {
            gc.setFill(colorPicker.getValue());
            gc.fillOval(point.getX() - size / 2, point.getY() - size / 2, size, size);
        }
        else if (shapes.getValue().equals("Square")) {
            gc.setFill(colorPicker.getValue());
            gc.fillRect(point.getX() - size / 2, point.getY() - size / 2, size, size);
        }
    }

    public void undo() {
    }

    public void redo() {
    }

    public void onOpen() {

    }

    public void onSave() {

    }

    public void onExit() {

    }
}
