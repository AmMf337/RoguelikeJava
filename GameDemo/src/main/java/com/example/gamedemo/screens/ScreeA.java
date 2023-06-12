package com.example.gamedemo.screens;

import com.example.gamedemo.model.Avatar;
import com.example.gamedemo.model.Enemy;
import com.example.gamedemo.model.Bullet;
import com.example.gamedemo.model.Vector;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class ScreeA extends BaseScreen {

    private ArrayList<Image> healtBar;

    private ArrayList<Image> bulletsBar;
    private Avatar avatar;

    private ArrayList<Enemy> enemies;

    private ArrayList<Bullet> bullets;
    private int lifeLossTimer = 0;
    private final int LIFE_LOSS_INTERVAL = 2000;


    public ScreeA(Canvas canvas) {
        super(canvas);
        avatar = new Avatar(canvas);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        healtBar = new ArrayList<>();
        bulletsBar = new ArrayList<>();
        generateEnemies();
        for (Enemy enemy : enemies) {
            enemy.start();
        }
        for(int i = 0; i <= 6; i++){
            Image image = new Image(getClass().getResourceAsStream("/healtBar/healtBar"+i+".png"));
            healtBar.add(image);
        }
        for(int i = 0; i <= 4; i++){
            Image image = new Image(getClass().getResourceAsStream("/bulletsBar/bullets"+i+".png"));
            bulletsBar.add(image);
        }

    }

    public void generateEnemies() {
        int numberEnemies = (int) (Math.random() * (12 - 8 + 1) + 8);
        Random random = new Random();

        for (int i = 0; i < numberEnemies; i++) {
            int positionx = (int) (Math.random() * 1221) + 350;
            int positionY = (int) (Math.random() * 671) + 110;
            Enemy enemy = new Enemy(canvas, new Vector(positionx, positionY));
            enemies.add(enemy);
        }
    }

    @Override
    public void paint() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.drawImage(healtBar.get(avatar.getLife()-1),390,130);
        if(avatar.getBullets().size()>0){
            graphicsContext.drawImage(bulletsBar.get(avatar.getBullets().size()-1),510,130);
        }else{
            graphicsContext.drawImage(bulletsBar.get(avatar.getBullets().size()),510,130);
        }

        avatar.paint();

        for (Enemy b : enemies) {
            b.behavior(avatar.getPosition());
            b.paint();
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint();

            if (bullets.get(i).getPositionX() > canvas.getWidth()) {
                bullets.remove(i);
                i--;
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < bullets.size(); j++) {

                Enemy actualEnemy = enemies.get(i);
                Bullet actualBullet = bullets.get(j);

                double distance = Math.sqrt(
                        Math.pow(actualEnemy.getPosition().getX() - actualBullet.getPositionX(), 2) +
                                Math.pow(actualEnemy.getPosition().getY() - actualBullet.getPositionY(), 2)
                );
                double distance2 = Math.sqrt(
                        Math.pow(actualEnemy.getPosition().getX() - avatar.getPosition().getX(), 2) +
                                Math.pow(actualEnemy.getPosition().getY() - avatar.getPosition().getY(), 2)
                );

                if (distance2 < 15) {
                    enemies.get(i).setContactAvatar(true);
                    if (lifeLossTimer >= LIFE_LOSS_INTERVAL) {
                        avatar.setLife(avatar.getLife() - 1);
                        lifeLossTimer = 0;
                    }
                }
                if(distance <= 10 && actualEnemy.getLife()>0){
                    actualEnemy.setLife(enemies.get(i).getLife()-1);
                    bullets.remove(j);
                    return;
                }
                if (distance <= 10 && enemies.get(i).getLife()==0) {
                    Enemy deletedEnemy = enemies.remove(i);

                    deletedEnemy.setAlive(false);
                    bullets.remove(j);
                    return;
                }

            }
        }
        lifeLossTimer += 50;

        System.out.println(avatar.getPosition().getX() + "" + avatar.getPosition().getY());
    }


    @Override
    public void onKeyPressed(KeyEvent event) {
        avatar.onKeyPressed(event);
    }

    @Override
    public void onKeyReleased(KeyEvent event) {
        avatar.onKeyReleased(event);
    }

    @Override
    public void onMousePressed(MouseEvent event) {
        avatar.setState(5);
        double diffX = event.getX() - avatar.getPosition().getX();
        double diffY = event.getY() - avatar.getPosition().getY();

        Vector diff = new Vector(diffX, diffY);

        diff.normalize();
        diff.setSpeed(4);
        if(avatar.getBullets().size()>=1){
            bullets.add(
                    new Bullet(canvas, new Vector(avatar.getPosition().getX(), avatar.getPosition().getY()), diff)
            );
            avatar.getBullets().pop();
        }

    }

    @Override
    public void OnMouseReleased(MouseEvent event) {

    }

    @Override
    public boolean isBottonPressed1() {
        return false;
    }


}
