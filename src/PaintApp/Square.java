package PaintApp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square extends Shape implements DrawShape {

    public Square(double sizeX, double sizeY, Color color, Point2D position) {
        super(sizeX, sizeY, color, position);
    }

    @Override
    public String toSVG() {
        String color = colorToString(getColor());
        return ("<rect x=\""+getPosition().getX()+"\" y=\""+getPosition().getY()+"\" width=\""+getSizeX()+"\" height=\""+getSizeY()+"\" fill=\""+color+"\"/>");
    }

    @Override
    public void drawShape(GraphicsContext gc) {
        gc.setFill(getColor());
        gc.fillRect(getPosition().getX() - getSizeX() / 2,
                getPosition().getY() - getSizeY() / 2,getSizeX(),getSizeY());
    }

    @Override
    public String toString() {
        String type = "Square";
        return (type);
    }
}
