package com.example.gamedemo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Enemy extends Thread{
    private Canvas canvas;

    private GraphicsContext graphicsContext;

    private Vector position;

    private int life;
    private int frame;
    private double y;

    private int state;

    private boolean isAlive;

    private ArrayList<Image> walkFront;

    private ArrayList<Image> attackFront;

    private boolean contactAvatar;

    public Enemy(Canvas canvas, Vector position ){
        this.canvas = canvas;
        state =0;
        frame =0;
        life = 2;
        walkFront = new ArrayList<>();
        this.attackFront = new ArrayList<>();
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.position = position;
        this.isAlive = true;
        this.contactAvatar = false;
        for(int i = 0; i <= 9; i++){
            Image image = new Image(getClass().getResourceAsStream("/animations.enemy/WalkFront/WalkFront"+i+".png"));
            walkFront.add(image);
        }
        for(int i = 0; i <= 7; i++){
            Image image = new Image(getClass().getResourceAsStream("/animations.enemy/attack/FrontAttack"+i+".png"));
            attackFront.add(image);
        }
    }
    public void behavior(Vector avatarPosition,double distance){

        if(contactAvatar){
           state = 2;

        }else if(distance<300){
            if(avatarPosition.getX()>this.position.getX()){
                position.setX(position.getX()+5);
            }else if(avatarPosition.getX()<this.position.getX()){
                position.setX(position.getX()-5);
            }
            if(avatarPosition.getY()>this.position.getY()){
                position.setY(position.getY()+5);
            }else if(avatarPosition.getY()<this.position.getY()){
                position.setY(position.getY()-5);
            }
        }

    }

    public Vector getPosition() {
        return position;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void paint() {
        if (state == 0){

            graphicsContext.drawImage(walkFront.get(frame%9), position.getX(), position.getY());
            frame++;
        }
        if (state == 2){
            graphicsContext.drawImage(attackFront.get(frame%7), position.getX(), position.getY());
            frame++;
            state = 0;
        }
    }

    @Override
    public void run(){
        while (isAlive){
            try {
                Thread.sleep(25);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public boolean isContactAvatar() {
        return contactAvatar;
    }

    public void setContactAvatar(boolean contactAvatar) {
        this.contactAvatar = contactAvatar;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }


    public int getStateInt() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
