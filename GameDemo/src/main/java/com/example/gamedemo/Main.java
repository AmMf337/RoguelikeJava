package com.example.gamedemo;

import com.example.gamedemo.control.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private Image customCursorImage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        customCursorImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cursorImage/cursor.png")));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        scene.setCursor(Cursor.NONE);
        scene.setCursor(new ImageCursor(customCursorImage,
                customCursorImage.getWidth() / 2,
                customCursorImage.getHeight() /2));
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            MainController controller = fxmlLoader.getController();
            controller.setRunning(false);

        });

        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}