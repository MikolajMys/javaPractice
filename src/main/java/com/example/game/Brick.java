package com.example.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GraphicsItem{

    static private int gridRows;
    static private int gridCols;
    private static final double margin = 0.1; // Margines między brickami
    private Color color;
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
    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x, y, width, height);
    }
}
