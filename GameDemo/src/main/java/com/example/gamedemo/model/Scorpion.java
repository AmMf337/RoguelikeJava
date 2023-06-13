package com.example.gamedemo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Scorpion extends  Thread{
    private Canvas canvas;
    private int life;
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
        state =1;
        life = 3;
        frame =0;
        walk = new ArrayList<>();
        this.attack = new ArrayList<>();
        this.idle = new ArrayList<>();
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
        for(int i = 0; i <= 5; i++){
            Image image = new Image(getClass().getResourceAsStream("/scorpion/idle/ScorpionIdle"+i+".png"));
            idle.add(image);
        }
    }
    public void behavior(Vector avatarPosition){

    }
    public Vector getPosition() {
        return position;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void paint() {
        if (avatarInRange){
            graphicsContext.drawImage(attack.get(frame%2), position.getX(), position.getY());
        }else{
            graphicsContext.drawImage(idle.get(frame%6), position.getX(), position.getY());
        }
        frame++;
        /*if(state==6){

            graphicsContext.drawImage(walk.get(frame%6), position.getX(), position.getY());
            frame++;
        }*/

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
