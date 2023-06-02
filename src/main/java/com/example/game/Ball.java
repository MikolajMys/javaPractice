package com.example.game;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends GraphicsItem {
    private Point2D moveVector = new Point2D(1, -1).normalize();
    private double velocity = 450;

    private Point2D lastPosition;

    public Ball() {
        x = -100;
        y = -100;
        width = height = canvasHeight * .015;
    }
    public Point2D getLastPosition() {
        return lastPosition;
    }
    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillOval(x, y, width, height);
    }

    public void setPosition(Point2D point) {
        this.x = point.getX() - width/2;
        this.y = point.getY() - height/2;
        lastPosition = point;
    }

    public void updatePosition(double diff) {
        lastPosition = new Point2D(x, y);
        x += moveVector.getX() * velocity * diff;
        y += moveVector.getY() * velocity * diff;
    }

    public void bounceHorizontally(){
        moveVector = new Point2D(-moveVector.getX(), moveVector.getY()).normalize();
    }
    public void bounceVertically(){
        moveVector = new Point2D(moveVector.getX(), -moveVector.getY()).normalize();
    }

//    public void bounceFromPaddle(){
//        moveVector = new Point2D(moveVector.getX(), -moveVector.getY()).normalize();
//    }
    public void bounceFromPaddleCentre(){
        moveVector = new Point2D(moveVector.getX() - 0.6, -moveVector.getY()).normalize();
    }
    public void bounceFromPaddleEdges(){
        moveVector = new Point2D(moveVector.getX() + 0.4, -moveVector.getY()).normalize();
    }
    public void outOfBounce(){
        moveVector = new Point2D(0, 0).normalize();
        System.out.println("GAME OVER!");
    }
}
