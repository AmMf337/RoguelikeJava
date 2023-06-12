package com.example.gamedemo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Scorpion extends  Thread{
    private Canvas canvas;

    private GraphicsContext graphicsContext;

    private Vector position;

    private int frame;
    private double y;

    private int state;

    private boolean isAlive;

    private ArrayList<Image> walk;

    private ArrayList<Image> attack;

    private ArrayList<Image> idle;

    private boolean avatarInRange;

    public Scorpion(Canvas canvas, Vector position ){
        this.canvas = canvas;
        state =0;
        frame =0;
        walk = new ArrayList<>();
        this.attack = new ArrayList<>();
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.position = position;
        this.isAlive = true;
        avatarInRange = false;
        for(int i = 0; i <= 5; i++){
            Image image = new Image(getClass().getResourceAsStream("/scorpion/move/ScorpionMove"+i+".png"));
            walk.add(image);
        }
        for(int i = 0; i <= 1; i++){
            Image image = new Image(getClass().getResourceAsStream("/scorpion/attack/ScorpionAttack"+i+".png"));
            attack.add(image);
        }
    }
    public void behavior(Vector avatarPosition){
        if(avatarInRange){
            state = 2;
        }else{
            if(avatarPosition.getX()>this.position.getX()){
                position.setX(position.getX()+3);
            }else if(avatarPosition.getX()<this.position.getX()){
                position.setX(position.getX()-3);
            }
            if(avatarPosition.getY()>this.position.getY()){
                position.setY(position.getY()+3);
            }else if(avatarPosition.getY()<this.position.getY()){
                position.setY(position.getY()-3);
            }
        }
        avatarInRange = false;
    }
    public Vector getPosition() {
        return position;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void paint() {
        if (state == 0){
            graphicsContext.drawImage(walk.get(frame%6), position.getX(), position.getY());
            frame++;
        }
        if (state == 2){
            graphicsContext.drawImage(attack.get(frame%2), position.getX(), position.getY());
            frame++;
            avatarInRange = false;
            state =0;
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

    public boolean isAvatarInRange() {
        return avatarInRange;
    }

    public void setAvatarInRange(boolean avatarInRange) {
        this.avatarInRange = avatarInRange;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
}
