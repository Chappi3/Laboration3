package PaintApp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Eraser extends Shape {

    public Eraser(double sizeX, double sizeY, Color color, Point2D position) {
        super(sizeX, sizeY, color, position);
    }

    @Override
    public void eraseShape(GraphicsContext gc) {
        gc.clearRect(getPosition().getX() - getSizeX() / 2,
                getPosition().getY() - getSizeY() / 2,getSizeX(),getSizeY());
    }

    @Override
    public String toString() {
        return ("<rect x=\""+getPosition().getX()+"\" y=\""+getPosition().getY()+"\" width=\""+getSizeX()+"\" height=\""+getSizeY()+"\" fill=\"clear\"/>");
    }
}
