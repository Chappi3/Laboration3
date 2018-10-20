package PaintApp;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
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
        model.getShapeList().addListener((ListChangeListener<Shape>) c -> draw());

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

    }

    public void canvasClicked(MouseEvent event) {

    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double size = Double.parseDouble(brushSize.getValue().toString());
        Shape shape = model.getShapeList().get(model.getShapeList().size()-1);

        if (eraser.isSelected()) {
            gc.clearRect(shape.getPosition().getX() - size / 2, shape.getPosition().getY() - size / 2, size, size);
        }
        else if (shapes.getValue().equals("Circle")) {
            gc.setFill(colorPicker.getValue());
            gc.fillOval(shape.getPosition().getX() - size / 2, shape.getPosition().getY() - size / 2, size, size);
        }
        else if (shapes.getValue().equals("Square")) {
            gc.setFill(colorPicker.getValue());
            gc.fillRect(shape.getPosition().getX() - size / 2, shape.getPosition().getY() - size / 2, size, size);
        }
    }

    public void undo() {

    }

    public void redo() {

    }

    public void onOpen() {

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
                        bufferedWriter.write(shape.toString());
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
        Platform.exit();
    }
}
