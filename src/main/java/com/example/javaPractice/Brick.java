package com.example.javaPractice;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GraphicsItem{

    static private int gridRows;
    static private int gridCols;
    private static final double margin = 0.1; // Margines między brickami
    private Color color;
    public enum CrushType {
        NoCrush,
        HorizontalCrush,
        VerticalCrush
    }
    public static void setGridRows(int gridRows) {
        Brick.gridRows = gridRows;
    }

    public static void setGridCols(int gridCols) {
        Brick.gridCols = gridCols;
    }
    public Brick(int x, int y, Color color){
        this.width = canvasWidth/gridCols - margin;
        this.height = canvasHeight/gridRows - margin;
        this.x = (width + margin) * x;
        this.y = (height + margin) * y;
        this.color = color;
    }
    public CrushType crush(Ball.ExtremePoints points) {
        Point2D topLeft = new Point2D(x+margin, y+margin);
        Point2D topRight = new Point2D(x+margin + width, y+margin);
        Point2D bottomLeft = new Point2D(x+margin, y+margin + height);
        Point2D bottomRight = new Point2D(x+margin + width, y+margin + height);

        if (points.top().getX() >= bottomLeft.getX()
                && points.top().getX() <= bottomRight.getX()
                && points.top().getY() <= bottomLeft.getY()
                && points.bottom().getY() >= bottomLeft.getY()) {
                return CrushType.VerticalCrush;
        } else
        if (points.bottom().getX() >= topLeft.getX()
                && points.bottom().getX() <= topRight.getX()
                && points.bottom().getY() >= topLeft.getY()
                && points.top().getY() <= topLeft.getY()) {
            return CrushType.VerticalCrush;
        } else
        if (points.left().getY() >= topRight.getY()
                && points.left().getY() <= bottomRight.getY()
                && points.left().getX() <= topRight.getX()
                && points.right().getX() >= topRight.getX()) {
            return CrushType.HorizontalCrush;
        } else
        if (points.right().getY() >= topLeft.getY()
                && points.right().getY() <= bottomLeft.getY()
                && points.right().getX() >= topLeft.getX()
                && points.left().getX() <= topLeft.getX()) {
            return CrushType.HorizontalCrush;
        } else {
            return CrushType.NoCrush;
        }
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x, y, width, height);
    }
}
