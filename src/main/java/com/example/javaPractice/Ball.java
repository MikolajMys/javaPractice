package com.example.javaPractice;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

public class Ball extends GraphicsItem {
    private Point2D moveVector = new Point2D(1, -1).normalize();
    private double velocity = 450;

    private Point2D lastPosition;
    public record ExtremePoints(Point2D top, Point2D bottom, Point2D right, Point2D left){};
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

        /*Sprawdzenie punktów skrajnych piłki*/
//        ExtremePoints extremePoints = extremePoints();
//        graphicsContext.setFill(Color.RED);
//        graphicsContext.fillOval(extremePoints.top.getX(), extremePoints.top.getY(), 3, 3);
//        graphicsContext.setFill(Color.BLUE);
//        graphicsContext.fillOval(extremePoints.bottom.getX(), extremePoints.bottom.getY(), 3, 3);
//        graphicsContext.setFill(Color.GREEN);
//        graphicsContext.fillOval(extremePoints.right.getX(), extremePoints.right.getY(), 3, 3);
//        graphicsContext.setFill(Color.YELLOW);
//        graphicsContext.fillOval(extremePoints.left.getX(), extremePoints.left.getY(), 3, 3);
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

    /*Pierwsza wersja odbijania piłeczki pod różnym kątem*/
//    public void bounceFromPaddleCentre(){
//        moveVector = new Point2D(moveVector.getX() - 0.6, -moveVector.getY()).normalize();
//    }
//    public void bounceFromPaddleEdges(){
//        moveVector = new Point2D(moveVector.getX() + 0.4, -moveVector.getY()).normalize();
//    }
    public void bounceFromPaddle(double centerOffset){
        double bounceAngle = 1.1 - centerOffset * 0.01;
        moveVector = new Point2D(moveVector.getX(), -bounceAngle).normalize();
        //System.out.printf("Kąt odbicia:%f\n", -bounceAngle);
    }
    public void outOfBounce(){
        moveVector = new Point2D(0, 0).normalize();
        System.out.println("GAME OVER!");
    }
    public void epicWin(){
        moveVector = new Point2D(0, 0).normalize();
        System.out.println("YOU WIN!");
    }
    public ExtremePoints extremePoints() {
        Point2D top = new Point2D(x + width/2,y);
        Point2D bottom = new Point2D(x + width/2,y + height);
        Point2D right = new Point2D(x + width,y + width/2);
        Point2D left = new Point2D(x,y + height/2);
        return new ExtremePoints(top, bottom, right, left);
    }
}