package com.example.gamedemo.model;

import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import com.example.gamedemo.model.Bullet;
import java.util.ArrayList;
import java.util.Stack;

public class Avatar {

    // Elementos graficos
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private ArrayList<Image> idleImages;
    private ArrayList<Image> runImages;

    private ArrayList<Image> runRight;

    private ArrayList<Image> runBack;

    private ArrayList<Image> runLeft;
    private ArrayList<Image> attackImages;

    // referencias espaciales
    private double posX;
    private double posY;

    private int life;

    private Vector position;
    private Vector direction;

    // estado actual del personaje

    private int state;
    private int frame;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private Stack<Integer> bullets;

    private boolean FireTypeEquiped;

    private  boolean hasNormalBullets;
    private boolean hasFireBullets;
    public Avatar(Canvas canvas){
        this.state = 0;
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.life = 7;
        this.hasNormalBullets = false;
        this.hasFireBullets = false;
        this.position = new Vector(970, 440);
        bullets = new Stack<>();
        this.posX = 100;
        this.posY = 100;
        runLeft = new ArrayList<>();
        idleImages = new ArrayList<>();
        runImages = new ArrayList<>();
        runRight = new ArrayList<>();
        attackImages = new ArrayList<>();
        runBack = new ArrayList<>();

        for(int i = 0; i <= 1; i++){
            Image image = new Image(getClass().getResourceAsStream("/animations/hero/idle/idleFront"+i+".png"));
            idleImages.add(image);
        }

        for(int i = 0; i <= 3; i++){
            Image image = new Image(getClass().getResourceAsStream("/animations/hero/run/WalkBackward"+i+".png"));
            runImages.add(image);
        }
        for(int i = 0; i <= 3; i++){
            Image image = new Image(getClass().getResourceAsStream("/animations/hero/runLeft/WalkLeft"+i+".png"));
            runRight.add(image);
        }
        for(int i = 0; i <= 3; i++){
            Image image = new Image(getClass().getResourceAsStream("/animations/hero/runRight/WalkRight"+i+".png"));
            runLeft.add(image);
        }
        for(int i = 0; i <= 3; i++){
            Image image = new Image(getClass().getResourceAsStream("/animations/hero/runBack/WalkFoward"+i+".png"));
            runBack.add(image);
        }
        for(int i = 0; i <= 3; i++){
            Image image = new Image(getClass().getResourceAsStream("/animations/hero/attack/ShootFront"+i+".png"));
            attackImages.add(image);
        }
    }

    public void paint(){
        onMove();
        if (state == 0){
            graphicsContext.drawImage(idleImages.get(frame%2), position.getX(), position.getY());
            frame++;
        }
        else if(state == 1) {
            graphicsContext.drawImage(runImages.get(frame%4), position.getX(), position.getY());
            frame++;
        }
        else if (state == 2) {
            graphicsContext.drawImage(runLeft.get(frame%4), position.getX(), position.getY());
            frame++;
        }else if (state == 3) {
            graphicsContext.drawImage(runRight.get(frame%4), position.getX(), position.getY());
            frame++;
        }else if (state == 4) {
            graphicsContext.drawImage(runBack.get(frame % 4), position.getX(), position.getY());
            frame++;
        }else if (state == 5) {
            graphicsContext.drawImage(attackImages.get(frame % 4), position.getX(), position.getY());
            frame++;
        }
    }

    public boolean isFireTypeEquiped() {
        return FireTypeEquiped;
    }

    public void setFireTypeEquiped(boolean fireTypeEquiped) {
        FireTypeEquiped = fireTypeEquiped;
    }

    public void onKeyPressed(KeyEvent event){
        switch (event.getCode()){
            case W:
                state = 4;
                upPressed = true;
                break;
            case S:
                state = 1;
                downPressed = true;
                break;
            case D:
                state = 2;
                rightPressed = true;
                break;
            case A:
                state = 3;
                leftPressed = true;
                break;
            case R:
                recharge();
                break;
            case G:
                FireTypeEquiped=false;
                break;
            case F:
                FireTypeEquiped=true;
                break;

        }
    }

    public void onKeyReleased(KeyEvent event){
        state = 0;
        switch (event.getCode()){
            case W:
                upPressed = false;
                break;
            case S:
                downPressed = false;
                break;
            case D:
                rightPressed = false;
                break;
            case A:
                leftPressed = false;
                break;
        }
    }
    public void recharge(){
        for(int i =0;i<5;i++){
            if(bullets.size()<=4 && hasNormalBullets){
                bullets.add(i);
            }
            if( bullets.size()<=4 && hasFireBullets){
                bullets.add(i);
            }
        }
    }

    public void onMove(){
        if (upPressed){
            if(position.getY() - 10>110 ){
                position.setY(position.getY() - 10);
            }

        }
        if (downPressed){
            if( position.getY() + 10<790){
                position.setY(position.getY() + 10);
            }

        }
        if (leftPressed){
            if(position.getX() - 10>320){
                position.setX(position.getX() - 10);
            }

        }
        if (rightPressed){
            if( position.getX() + 10<1580){
                position.setX(position.getX() + 10);
            }

        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Vector getPosition() {
        return position;
    }

    public Stack<Integer> getBullets() {
        return bullets;
    }

    public void setBullets(Stack<Integer> bullets) {
        this.bullets = bullets;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public boolean isHasNormalBullets() {
        return hasNormalBullets;
    }

    public void setHasNormalBullets(boolean hasNormalBullets) {
        this.hasNormalBullets = hasNormalBullets;
    }

    public boolean isHasFireBullets() {
        return hasFireBullets;
    }

    public void setHasFireBullets(boolean hasFireBullets) {
        this.hasFireBullets = hasFireBullets;
    }
}
