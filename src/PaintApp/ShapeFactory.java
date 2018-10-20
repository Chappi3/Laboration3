package PaintApp;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class ShapeFactory {

    public static void createShape(Model model, String type, double sizeX, double sizeY, Color color, Point2D position) {
        switch (type) {
            case "eraser":
                createEraser(model,sizeX,sizeY,color,position);
                break;
            case "circle":
                createCircle(model,sizeX,sizeY,color,position);
                break;
            case "square":
                createSquare(model,sizeX,sizeY,color,position);
                break;
        }
    }

    private static void createEraser(Model model, double sizeX, double sizeY, Color color, Point2D position) {
        Shape eraser = new Eraser(sizeX,sizeY,color,position);
        model.getShapeList().add(eraser);
    }

    private static void createCircle(Model model, double sizeX, double sizeY, Color color, Point2D position) {
        Shape circle = new Circle(sizeX,sizeY,color, position);
        model.getShapeList().add(circle);
    }

    private static void createSquare(Model model, double sizeX, double sizeY, Color color, Point2D position) {
        Shape square = new Square(sizeX,sizeY,color, position);
        model.getShapeList().add(square);
    }

}