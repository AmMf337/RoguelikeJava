package com.example.gamedemo.screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class BaseScreen {

    @FXML
    protected Canvas canvas;
    protected GraphicsContext graphicsContext;

    public BaseScreen(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
    }

    public abstract void paint();

    public abstract void onKeyPressed(KeyEvent event);

    public abstract void onKeyReleased(KeyEvent event);

    public abstract void onMousePressed(MouseEvent event);


    public abstract void OnMouseReleased(MouseEvent event);

    public abstract boolean isBottonPressed1();


}
