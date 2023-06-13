package com.example.gamedemo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

public class FireBullet {
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private int frame;
    private ArrayList<Image> bulletAnimation;

    // referencias espaciales
    private Vector position;
    private Vector direction;
    private int size;
    private int speed;

    private int state;
    public FireBullet(Canvas canvas, Vector position, Vector direction) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.position = position;
        this.direction =  direction;
        this.size = 1;
        this.speed = 10;
        frame = 0;
        bulletAnimation = new ArrayList<>();
        for(int i = 0; i <= 5; i++){
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/FireBullet/Fireball" + i + ".png")));
            bulletAnimation.add(image);
        }
    }

    public void paint(){
        if (state == 0){
            graphicsContext.drawImage(bulletAnimation.get(frame%6), position.getX(), position.getY());
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
