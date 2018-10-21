package PaintApp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape implements DrawShape, EraseShape {

    // fields
    private double sizeX;
    private double sizeY;
    private Color color;
    private Point2D position;

    // constructor
    public Shape(double sizeX, double sizeY, Color color, Point2D position) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.color = color;
        this.position = position;
    }

    // getters and setters
    public double getSizeX() {
        return sizeX;
    }
    public void setSizeX(double sizeX) {
        this.sizeX = sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }
    public void setSizeY(double sizeY) {
        this.sizeY = sizeY;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public Point2D getPosition() {
        return position;
    }
    public void setPosition(Point2D position) {
        this.position = position;
    }

    // for shapes to be drawn
    @Override
    public void drawShape(GraphicsContext gc) {

    }

    // for the eraser to be drawn
    @Override
    public void eraseShape(GraphicsContext gc) {

    }

    // for shapes to be in correct string format
    public String toSVG() {
        return null;
    }

    // checks if mouse coordinates is in the are of a shape
    public boolean shapeArea(double mouseX,double mouseY) {

        if (mouseX > getPosition().getX() - getSizeX() && mouseX < getPosition().getX() + getSizeX()
        && mouseY > getPosition().getY() - getSizeY() && mouseY < getPosition().getY() + getSizeY()) {
            return true;
        }
        return false;
    }

    // convert hex to stringRGB for svg
    public static String colorToString(Color color )
    {
        return String.format("#%02X%02X%02X",(int)(color.getRed() * 255),(int)(color.getGreen() * 255),(int)(color.getBlue() * 255));
    }
}