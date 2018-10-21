package PaintApp;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {

    private Stage stage;
    private Model model = new Model();

    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ChoiceBox brushSize;
    @FXML
    private ChoiceBox shapes;
    @FXML
    private CheckBox selection;
    @FXML
    private CheckBox eraser;
    @FXML
    private Button undoButton;
    @FXML
    private Button redoButton;
    @FXML
    private MenuItem menuUndo;
    @FXML
    private MenuItem menuRedo;
    @FXML
    private ListView listView;
    @FXML
    private Label mouseCoordinates;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void init() {
        // Make our bindings here, everything is loaded.
        model.getShapeList().addListener((ListChangeListener<Shape>) c -> draw());
        listView.setItems(model.getShapeList());

        // Shapes choice box
        model.getShapeChoices().addAll("Circle","Square");
        shapes.setItems(model.getShapeChoices());
        shapes.setValue("Circle"); // default shape

        // default color
        colorPicker.setValue(Color.BLACK);

        // brush size choice box
        String j;
        for (int i = 2; i <= 20 ; i = i + 2) {
            j = Integer.toString(i);
            brushSize.getItems().add(j);
        }

        // default size
        brushSize.setValue("12");

        // redo button disabled as default
        redoButton.setDisable(true);
        menuRedo.setDisable(true);

        // undo button disabled as default
        undoButton.setDisable(true);
        menuUndo.setDisable(true);
    }

    // when moving mouse on canvas
    public void canvasMouseCoordinates(MouseEvent event) {
        // mouse coordinates label
        mouseCoordinates.setText(String.format("Mouse Coordinates X: %g Y: %g",event.getX(),event.getY()));
    }

    // when clicking on canvas
    public void canvasEvents(MouseEvent event) {
        // mouseOne event
        if (event.isPrimaryButtonDown()) {
            double size = Double.parseDouble(brushSize.getValue().toString());
            Color color = colorPicker.getValue();
            Point2D position = new Point2D(event.getX(), event.getY());

            if (eraser.isSelected() || selection.isSelected()) {

                if (eraser.isSelected()) {
                    String type = "eraser";
                    ShapeFactory.createShape(model,type,size,size,color,position);
                    System.out.println("New eraser!");
                }

                if (selection.isSelected()) {
                    for (Shape shape : model.getShapeList()) {
                        // TODO: add the size of the object
                        if (shape.shapeArea(position.getX(),position.getY())) {//check if mouse is clicked within shape
                            // TODO: add selected shape data to be edited in th left side view. color,size etc.
                            System.out.println("Clicked a "+shape.getClass().getName());
                        }
                    }
                }
            }

            else if (shapes.getValue().equals("Circle")) {
                String type = "circle";
                ShapeFactory.createShape(model,type,size,size,color,position);
                System.out.println("New circle!");
            }

            else if (shapes.getValue().equals("Square")) {
                String type = "square";
                ShapeFactory.createShape(model,type,size,size,color,position);
                System.out.println("New square!");
            }
        }
    }

    // when something on the list view is clicked
    public void listViewPressed(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            Shape shape = (Shape)listView.getSelectionModel().getSelectedItem();
            shape.setColor(Color.GRAY);
            // TODO: implement selection and edit selected object
        }
    }

    // When a choice box is changed
    public void changedCheckBox() {
        if (!selection.isSelected() && !eraser.isSelected()) {
            eraser.setDisable(false);
            selection.setDisable(false);
        }
        if (selection.isSelected()) {
            eraser.setDisable(true);
        }
        if (eraser.isSelected()) {
            selection.setDisable(true);
        }
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Shape shape = model.getShapeList().get(model.getShapeList().size()-1);

        if (eraser.isSelected()) {
            shape.eraseShape(gc);
        }
        else {
            shape.drawShape(gc);
        }
    }

    public void onSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("png", "*.png"), new FileChooser.ExtensionFilter("svg", "*.svg"));
        fileChooser.setInitialFileName("Nameless");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showSaveDialog(stage);
        Platform.runLater(() -> {
            // If user choose png format
            if (fileChooser.getSelectedExtensionFilter().getDescription().equals("png")) {
                try {
                    Image snapshot = canvas.snapshot(null, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null),"png",file);
                }
                catch (Exception e) {
                    System.out.println("Failed to save image: "+e);
                }
            }
            // If user choose svg format
            if (fileChooser.getSelectedExtensionFilter().getDescription().equals("svg")) {
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(file.getAbsoluteFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                try {
                    bufferedWriter.write("<svg height=\""+canvas.getHeight()+"\" width=\""+canvas.getWidth()+"\" xmlns=\"http://www.w3.org/2000/svg\">");
                    bufferedWriter.newLine();
                    for (Shape shape : model.getShapeList()) {
                        bufferedWriter.write(shape.toSVG());
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.write("</svg>");
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onExit() {
        // TODO: exit confirmation
        Platform.exit();
    }

    public void onAbout() {
        // TODO: add some info about the paint app here in a new window.
    }

    public void undo() {
        undoButton.setDisable(true);
        menuUndo.setDisable(true);
        // TODO: implement undo function

        //undoLastShape();

    }

    public void redo() {
        redoButton.setDisable(true);
        menuRedo.setDisable(true);
        // TODO: implement redo function

        //redoLastShape();

    }
}