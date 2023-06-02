package com.example.game;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameCanvas extends Canvas {
    private GraphicsContext graphicsContext;
    private Paddle paddle;
    private Ball ball;
    private boolean gameRunning = false;
    private List<Brick> bricks = new ArrayList<>();

    public void loadLevel(){
        int cols = 10;
        int rows = 20;

        Brick.setGridCols(cols);
        Brick.setGridRows(rows);

        Color[] colors = new Color[]{Color.RED, Color.GREY, Color.AQUAMARINE, Color.SANDYBROWN, Color.YELLOWGREEN, Color.GOLD, Color.BLUE};
        Random random = new Random();

        for (int i = 2; i < 7; ++i) {
            int index = random.nextInt(colors.length);
            for (int j = 0; j < cols; ++j) {
                //System.out.println(colors[index]);
                Brick brick = new Brick(j, i, colors[index]);
                bricks.add(brick);
            }
        }
    }

    private AnimationTimer animationTimer = new AnimationTimer() {
        private long lastUpdate;
        @Override
        public void handle(long now) {
            double diff = (now - lastUpdate)/1_000_000_000.;
            lastUpdate = now;
            ball.updatePosition(diff);
            if(shouldBallBounceHorizontally()){ ball.bounceHorizontally();}
            if(shouldBallBounceVertically()){ ball.bounceVertically();}
            //if(shouldBallBounceFromPaddle()){ ball.bounceFromPaddle();}
            if (shouldBallBounceFromPaddleCentre()) { ball.bounceFromPaddleCentre();}
            if (shouldBallBounceFromPaddleEdges()) { ball.bounceFromPaddleEdges();}
            if(shouldBallOutOfBounce()){ ball.outOfBounce(); stop();}
            draw();
        }

        @Override
        public void start() {
            super.start();
            lastUpdate = System.nanoTime();
        }
    };

    public GameCanvas() {
        super(640, 700);

        this.setOnMouseMoved(mouseEvent -> {
            paddle.setPosition(mouseEvent.getX());
            if(!gameRunning)
                ball.setPosition(new Point2D(mouseEvent.getX(), paddle.getY() - ball.getWidth() / 2));
//            else
//                ball.updatePosition();
            draw();
        });

        this.setOnMouseClicked(mouseEvent -> {
            gameRunning = true;
            animationTimer.start();
        });
    }

    public void initialize() {
        graphicsContext = this.getGraphicsContext2D();
        GraphicsItem.setCanvasSize(getWidth(), getHeight());
        paddle = new Paddle();
        ball = new Ball();
        this.loadLevel();
    }

    public void draw() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, getWidth(), getHeight());

        paddle.draw(graphicsContext);
        ball.draw(graphicsContext);

        for(int i = 0; i < bricks.size(); ++i){
            bricks.get(i).draw(graphicsContext);
        }
    }
    public boolean shouldBallBounceHorizontally(){
        return ((ball.getLastPosition().getX() > 0 && ball.getX() < 0)
                || (ball.getLastPosition().getX() + ball.getWidth() < this.getWidth()-1 && ball.getX()+ball.getWidth() > this.getWidth()-1));
    }

    public boolean shouldBallBounceVertically(){
        return (ball.getLastPosition().getY() > 0 && ball.getY() < 0);
    }

//    public boolean shouldBallBounceFromPaddle(){
//        return (ball.getLastPosition().getY() < paddle.getY() - paddle.getHeight() && ball.getY() >= paddle.getY() - paddle.getHeight()
//                && ball.getX() >= paddle.getX() && ball.getX() <= paddle.getX() + paddle.getWidth());
//    }

    /*Odbicia piłki od paletki względem tego od której części paletki się ona odbije.
    Wymyśliłem, że podziele paletke na 3 części: BOK|CENTRUM|BOK.
    Jeżeli piłka dotknie środka, kąt odbicia będzie bardziej ostry,
    a jeśli dotknie któregoś z boków kąt odbicia będzie bardziej rozwarty*/
    public boolean shouldBallBounceFromPaddleCentre() {
        double paddleCentreStart = paddle.getX() + paddle.getWidth() / 3.0;
        double paddleCentreEnd = paddle.getX() + 2.0 * paddle.getWidth() / 3.0;
        return (ball.getLastPosition().getY() < paddle.getY() - paddle.getHeight()
                && ball.getY() >= paddle.getY() - paddle.getHeight()
                && ball.getX() >= paddleCentreStart && ball.getX() <= paddleCentreEnd);
    }

    public boolean shouldBallBounceFromPaddleEdges() {
        double paddleCentreStart = paddle.getX() + paddle.getWidth() / 3.0;
        double paddleCentreEnd = paddle.getX() + 2.0 * paddle.getWidth() / 3.0;
        return (ball.getLastPosition().getY() < paddle.getY() - paddle.getHeight()
                && ball.getY() >= paddle.getY() - paddle.getHeight()
                && (ball.getX() < paddleCentreStart && ball.getX() >= paddle.getX()
                || ball.getX() > paddleCentreEnd && ball.getX() <= paddle.getX() + paddle.getWidth()));
    }

    public boolean shouldBallOutOfBounce(){
        return (ball.getY() > this.getHeight()-10);
    }
}