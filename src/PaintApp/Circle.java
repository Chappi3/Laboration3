package PaintApp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Shape {

    public Circle(double sizeX, double sizeY, Color color, Point2D position) {
        super(sizeX, sizeY, color, position);
    }

    @Override
    public void drawShape(GraphicsContext gc) {
        gc.setFill(getColor());
        gc.fillOval(getPosition().getX() - getSizeX() / 2,
                getPosition().getY() - getSizeY() / 2,getSizeX(),getSizeY());
    }

    @Override
    public String toString() {
        String color = colorToString(getColor());
        // <circle cx="positionX" cy="positionY" r="size" fill="color" />
        return ("<circle cx=\""+getPosition().getX()+"\" cy=\""+getPosition().getY()+"\" r=\""+getSizeX()+"\" fill=\""+color+"\" />");
    }
}