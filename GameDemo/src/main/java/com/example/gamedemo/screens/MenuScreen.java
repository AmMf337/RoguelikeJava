
package com.example.gamedemo.screens;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.Objects;

public class MenuScreen extends BaseScreen {

    private boolean bottonPressed1;
    public MenuScreen(Canvas canvas) {
        super(canvas);

        bottonPressed1 = false;
    }

    @Override
    public void paint() {
        graphicsContext.setFill(Color.BLACK);
        Image buttonImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ImagesUi/button1.png")));

        graphicsContext.drawImage(buttonImage, 928, 260);

    }

    @Override
    public void onKeyPressed(KeyEvent event) {
        // Implementa el manejo de eventos de teclado en la ventana del menú
    }

    @Override
    public void onKeyReleased(KeyEvent event) {
        // Implementa el manejo de eventos de teclado en la ventana del menú
    }

    @Override
    public void onMousePressed(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        if (mouseX >= 940 && mouseX <= 940 + 105 &&
                mouseY >= 440 && mouseY <= 440  + 33 ) {
            bottonPressed1 =true;
            System.out.println("¡Se hizo clic en el botón del menú!");
        }
    }

    @Override
    public void OnMouseReleased(MouseEvent event) {
        // Implementa el manejo de eventos de mouse en la ventana del menú
    }


    public boolean isBottonPressed1() {
        return bottonPressed1;
    }

    public void setBottonPressed1(boolean bottonPressed1) {
        this.bottonPressed1 = bottonPressed1;
    }

    public void onOpenLevel1(ActionEvent actionEvent) {
    }
}
