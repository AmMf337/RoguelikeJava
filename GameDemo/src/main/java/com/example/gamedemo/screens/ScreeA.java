package com.example.gamedemo.screens;

import com.example.gamedemo.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import javafx.util.Duration;

import java.util.ArrayList;

import java.util.Timer;


public class ScreeA extends BaseScreen {

    private ArrayList<Image> healtBar;

    private ArrayList<AcidBean> acidBeans;

    private ArrayList<FireBullet> firebullets;
    private ArrayList<Image> bulletsBar;
    private Avatar avatar;

    private Timer timer;
    private ArrayList<BulletPack> bulletPacks;

    private boolean gameOver;
    private ArrayList<Enemy> enemies;
    private ArrayList<Scorpion> scorpions;
    private ArrayList<Bullet> bullets;
    private int lifeLossTimer = 0;


    private final int LIFE_LOSS_INTERVAL = 2000;


    public ScreeA(Canvas canvas) {
        super(canvas);
        firebullets = new ArrayList<>();
        bulletPacks = new ArrayList<>();
        acidBeans = new ArrayList<>();
        avatar = new Avatar(canvas);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        healtBar = new ArrayList<>();
        bulletsBar = new ArrayList<>();
        scorpions = new ArrayList<>();
        gameOver = false;
        timer = new Timer();
        int positionx = (int) (Math.random() * 161) + 320;
        int positionY = (int) (Math.random() * 441) + 110;
        Vector v = new Vector(positionx,positionY);
        Image imageNormal = new Image(getClass().getResourceAsStream("/bulletsType/normalBullets"+".png"));
        BulletPack normalBullets = new BulletPack(this.canvas,imageNormal,v,false);
        bulletPacks.add(normalBullets);
        int positionx2 = (int) (Math.random() * 121) + 1460;
        int positionY2 = (int) (Math.random() * 441) + 110;
        Vector v2 = new Vector(positionx2,positionY2);
        Image imageFire = new Image(getClass().getResourceAsStream("/bulletsType/fireBullets"+".png"));
        BulletPack fireBullets = new BulletPack(this.canvas,imageFire,v2,true);
        bulletPacks.add(fireBullets);
        generateEnemies();
        for (Enemy enemy : enemies) {
            enemy.start();
        }
        for(Scorpion s :scorpions){
            s.start();
        }
        for(int i = 0; i <= 6; i++){
            Image image = new Image(getClass().getResourceAsStream("/healtBar/healtBar"+i+".png"));
            healtBar.add(image);
        }
        for(int i = 0; i <= 5; i++){
            Image image = new Image(getClass().getResourceAsStream("/bulletsBar/bullets"+i+".png"));
            bulletsBar.add(image);
        }

    }

    public void generateEnemies() {
        int numberEnemies = (int) (Math.random() * (3 - 1 + 1) + 1);
        int numberEnemies2 = (int) (Math.random() * (5 -4 + 1) + 4);
        for (int i = 0; i < numberEnemies; i++) {
            int positionx = (int) (Math.random() * 1221) + 350;
            int positionY = (int) (Math.random() * 671) + 110;
            Enemy enemy = new Enemy(canvas, new Vector(positionx, positionY));
            enemies.add(enemy);
        }
        for (int i = 0; i < numberEnemies2; i++) {
            int positionx = (int) (Math.random() * 1221) + 350;
            int positionY = (int) (Math.random() * 671) + 110;
            Scorpion s = new Scorpion(canvas, new Vector(positionx, positionY));
            scorpions.add(s);
        }
    }

    @Override
    public void paint() {
        if(gameOver){

            Image gameOverImage =  new Image(getClass().getResourceAsStream("/GameOverScreen/gameover"+".jpg"));
            ImagePattern image = new ImagePattern(gameOverImage);
            graphicsContext.setFill(image);
            graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            Image gameOverText =  new Image(getClass().getResourceAsStream("/GameOverScreen/overText"+".png"));
            graphicsContext.drawImage(gameOverText,500,100);

        }else{
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphicsContext.drawImage(healtBar.get(avatar.getLife()-1),390,130);
            if(avatar.getBullets().size()>0 ){
                graphicsContext.drawImage(bulletsBar.get(avatar.getBullets().size()),660,130);
            }else {
                graphicsContext.drawImage(bulletsBar.get(avatar.getBullets().size()),660,130);
            } /*else if (avatar.getBullets().size()>0 && avatar.isFireTypeEquiped()) {
                graphicsContext.drawImage(bulletsBar.get(avatar.getBullets().size()),660,130);
            } else if (avatar.getBullets().size()==0 && avatar.isFireTypeEquiped()) {
                graphicsContext.drawImage(bulletsBar.get(avatar.getBullets().size()),660,130);
            }*/
            Image fireType = new Image(getClass().getResourceAsStream("/FireBullet/Fireball0"+".png"));
            if(avatar.isFireTypeEquiped()){
                graphicsContext.drawImage(fireType,900,130);
            }
            avatar.paint();
            for(BulletPack bp:bulletPacks){
                bp.paint();
            }
            for (FireBullet fb:firebullets) {
                fb.paint();
            }
            for (Enemy b : enemies) {
                double distance2 = Math.sqrt(
                        Math.pow(b.getPosition().getX() - avatar.getPosition().getX(), 2) +
                                Math.pow(b.getPosition().getY() - avatar.getPosition().getY(), 2)
                );
                b.behavior(avatar.getPosition(),distance2);
                b.paint();
            }
            for (Scorpion s:scorpions) {
                s.behavior(avatar.getPosition());
                s.paint();
            }
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).paint();

                if (bullets.get(i).getPositionX() > canvas.getWidth()) {
                    bullets.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < firebullets.size(); i++) {
                firebullets.get(i).paint();

                if (firebullets.get(i).getPositionX() > canvas.getWidth()) {
                    firebullets.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < acidBeans.size(); i++) {
                acidBeans.get(i).paint();

                if (acidBeans.get(i).getPositionX() > canvas.getWidth()) {
                    acidBeans.remove(i);
                    i--;
                }
            }

            for (int i = 0; i < enemies.size(); i++) {
                for (int j = 0; j < firebullets.size(); j++) {
                    Enemy actualEnemy = enemies.get(i);
                    FireBullet fb = firebullets.get(j);
                    double distance = Math.sqrt(
                            Math.pow(actualEnemy.getPosition().getX() - fb.getPositionX(), 2) +
                                    Math.pow(actualEnemy.getPosition().getY() - fb.getPositionY(), 2)
                    );
                    if(distance <= 20 && actualEnemy.getLife()>0){
                        actualEnemy.setLife(enemies.get(i).getLife()-1);
                        firebullets.remove(j);
                        return;
                    }
                    if (distance <= 20 && enemies.get(i).getLife()==0) {
                        Enemy deletedEnemy = enemies.remove(i);

                        deletedEnemy.setAlive(false);
                        firebullets.remove(j);
                        return;
                    }
                }
            }
            for (int i = 0; i < scorpions.size(); i++) {
                for (int j = 0; j < firebullets.size(); j++) {
                    Scorpion actualEnemy = scorpions.get(i);
                    FireBullet fb = firebullets.get(j);
                    double distance = Math.sqrt(
                            Math.pow(actualEnemy.getPosition().getX() - fb.getPositionX(), 2) +
                                    Math.pow(actualEnemy.getPosition().getY() - fb.getPositionY(), 2)
                    );
                    if(distance <= 20 && actualEnemy.getLife()>0){
                        actualEnemy.setLife(scorpions.get(i).getLife()-1);
                        firebullets.remove(j);
                        return;
                    }
                    if (distance <= 20 && scorpions.get(i).getLife()==0) {
                        Scorpion deletedEnemy = scorpions.remove(i);

                        deletedEnemy.setAlive(false);
                        firebullets.remove(j);
                        return;
                    }
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

                    if (distance2 <= 15) {
                        actualEnemy.setContactAvatar(true);
                        if (lifeLossTimer >= LIFE_LOSS_INTERVAL) {
                            avatar.setLife(avatar.getLife() - 1);
                            lifeLossTimer = 0;
                            if (avatar.getLife() <= 0) {
                                gameOver = true;
                            }
                        }
                    }else{
                        actualEnemy.setContactAvatar(false);
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
            for (BulletPack b:bulletPacks) {
                double distance = Math.sqrt(
                        Math.pow(avatar.getPosition().getX() - b.getPosition().getX(), 2) +
                                Math.pow(avatar.getPosition().getY() - b.getPosition().getY(), 2)
                );
                if(distance<=10 && !b.isTypeFire()){
                    avatar.setHasNormalBullets(true);
                    b.setState(0);
                }
                if(distance<=10 && b.isTypeFire()){
                    avatar.setHasFireBullets(true);
                    b.setState(0);
                }
            }

            for (int i = 0;i<scorpions.size();i++){
                for (int j = 0; j < bullets.size(); j++) {
                    final boolean[] enemyCanShoot = {true};
                    Scorpion sActual = scorpions.get(i);
                    Bullet actualBullet = bullets.get(j);
                    double distance2 = Math.sqrt(
                            Math.pow(scorpions.get(i).getPosition().getX() - avatar.getPosition().getX(), 2) +
                                    Math.pow(scorpions.get(i).getPosition().getY() - avatar.getPosition().getY(), 2)
                    );
                    double distance = Math.sqrt(
                            Math.pow(sActual.getPosition().getX() - actualBullet.getPositionX(), 2) +
                                    Math.pow(sActual.getPosition().getY() - actualBullet.getPositionY(), 2)
                    );

                    if(distance2 < 150 ){


                        scorpions.get(i).setAvatarInRange(true);


                        double diffX = avatar.getPosition().getX() - scorpions.get(i).getPosition().getX();
                        double diffY = avatar.getPosition().getY() - scorpions.get(i).getPosition().getY();

                        Vector diff = new Vector(diffX, diffY);

                        diff.normalize();
                        diff.setSpeed(4);
                        if (enemyCanShoot[0]) {
                            acidBeans.add(
                                    new AcidBean(canvas, new Vector(sActual.getPosition().getX(), sActual.getPosition().getY()), diff)
                            );
                            enemyCanShoot[0] = false;
                        }
                        Duration delay = Duration.seconds(2);
                        Timeline timer = new Timeline(
                                new KeyFrame(delay, event -> {
                                    enemyCanShoot[0] = true;
                                })
                        );

                    }else{
                        scorpions.get(i).setAvatarInRange(false);
                    }
                    if(distance <= 15 && sActual.getLife()>0){
                        sActual.setLife(scorpions.get(i).getLife()-1);
                        bullets.remove(j);
                        return;
                    }
                    if (distance <= 15 && scorpions.get(i).getLife()==0) {
                        Scorpion deletedEnemy = scorpions.remove(i);

                        deletedEnemy.setAlive(false);
                        bullets.remove(j);
                        return;
                    }
                }

                for(int j = 0;j<acidBeans.size();j++){

                    AcidBean ac = acidBeans.get(j);
                    double distance3 = Math.sqrt(
                            Math.pow(ac.getPositionX() - avatar.getPosition().getX(), 2) +
                                    Math.pow(ac.getPositionY() - avatar.getPosition().getY(), 2)
                    );


                    if(distance3<10){
                        avatar.setLife(avatar.getLife()-1);
                        if (avatar.getLife() <= 0) {
                            gameOver = true;
                        }
                        acidBeans.remove(j);
                        return;
                    }

                }

            }
            lifeLossTimer += 100;


            //System.out.println(avatar.getPosition().getX() + "" + avatar.getPosition().getY());
        }


    }


    @Override
    public void onKeyPressed(KeyEvent event) {
        avatar.onKeyPressed(event);
        if(gameOver && event.getCode() == KeyCode.Y){
            restar();
        }
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
        if(avatar.getBullets().size()>=1 && avatar.isHasNormalBullets() && !avatar.isFireTypeEquiped()){
            bullets.add(
                    new Bullet(canvas, new Vector(avatar.getPosition().getX(), avatar.getPosition().getY()), diff)
            );
            avatar.getBullets().pop();
        }
        if (avatar.getBullets().size()>=1 && avatar.isHasFireBullets() && avatar.isFireTypeEquiped()) {
            firebullets.add(
                 new FireBullet(canvas,new Vector(avatar.getPosition().getX(), avatar.getPosition().getY()), diff)
            );
            System.out.println("here");
            avatar.getBullets().pop();
        }
        System.out.println(avatar.isFireTypeEquiped());

    }
    public void restar(){
        avatar.setLife(7);
        avatar.setHasFireBullets(false);
        avatar.setHasNormalBullets(false);
        avatar.setPosition(new Vector(970, 440));
        avatar.recharge();
        gameOver = false;
        for (Enemy e:enemies) {
            e.setAlive(true);
            e.setLife(2);
            int positionx = (int) (Math.random() * 1221) + 350;
            int positionY = (int) (Math.random() * 671) + 110;
            Vector v = new Vector(positionx,positionY);
            e.setPosition(v);
        }
        for(Scorpion s:scorpions){
            s.setAlive(true);
            s.setLife(3);
            int positionx = (int) (Math.random() * 1221) + 350;
            int positionY = (int) (Math.random() * 671) + 110;
            Vector v = new Vector(positionx,positionY);
            s.setPosition(v);
        }
        for (BulletPack bp: bulletPacks) {
            bp.setState(1);
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
