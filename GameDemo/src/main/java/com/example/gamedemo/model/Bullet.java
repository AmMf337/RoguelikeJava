package com.example.gamedemo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Bullet {

    // Elementos graficos
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private int frame;
    private ArrayList<Image> bulletAnimationRight;

    // referencias espaciales
    private Vector position;
    private Vector direction;
    private int size;
    private int speed;

    private int state;
    public Bullet(Canvas canvas, Vector position, Vector direction) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.position = position;
        this.direction =  direction;
        this.size = 10;
        this.speed = 10;
        frame = 0;
        bulletAnimationRight = new ArrayList<>();
        for(int i = 0; i <= 3; i++){
            Image image = new Image(getClass().getResourceAsStream("/animations.bullet/BulletRight"+i+".png"));
            bulletAnimationRight.add(image);
        }
    }

    public void paint(){
        if (state == 0){
            graphicsContext.drawImage(bulletAnimationRight.get(frame%4), position.getX(), position.getY());
            frame++;
        }
        position.setX(position.getX() + direction.getX());
        position.setY(position.getY() + direction.getY());

    }

    public double getPositionX() {
        return position.getX();
    }

    public void setPositionX(double x) {
        this.position.setX(x);
    }

    public double getPositionY() {
        return position.getY();
    }

    public void setPositionY(double y) {
        this.position.setY(y);
    }
}
