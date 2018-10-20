package PaintApp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape implements DrawShape, EraseShape {

    private double sizeX;
    private double sizeY;
    private Color color;
    private Point2D position;

    public Shape(double sizeX, double sizeY, Color color, Point2D position) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.color = color;
        this.position = position;
    }

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

    @Override
    public void drawShape(GraphicsContext gc) {

    }

    @Override
    public void eraseShape(GraphicsContext gc) {

    }

    // convert hex to stringRGB for svg
    public static String colorToString(Color color )
    {
        return String.format("#%02X%02X%02X",(int)(color.getRed() * 255),(int)(color.getGreen() * 255),(int)(color.getBlue() * 255));
    }
}