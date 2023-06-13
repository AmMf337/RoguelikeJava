package com.example.gamedemo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletPack {
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private boolean typeFire;
    private Image image;

    private int state;
    private Vector position;

    public BulletPack(Canvas canvas,Image a,Vector position,boolean typeFire){
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.position = position;
        this.image = a;
        this.state = 1;
        this.typeFire = typeFire;
    }
    public void paint(){
        if(state==1){
            graphicsContext.drawImage(this.image, position.getX(), position.getY());
        }

    }

    public boolean isTypeFire() {
        return typeFire;
    }

    public void setTypeFire(boolean typeFire) {
        this.typeFire = typeFire;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
