package org.example.oopryhmatoo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.util.Random;

public class Peaklass extends Application {
    // Estonian variable names with descriptive meanings
    private static final double ALGNE_LAIUS = 800;
    private static final double ALGNE_KÕRGUS = 600;

    int score;
    Random rand = new Random();

    @Override
    public void start(Stage pealava) {
        // Root pane that will scale with the window
        StackPane juurPaan = new StackPane();

        // Background rectangle that fills the entire window
        Rectangle taust = new Rectangle();
        taust.widthProperty().bind(juurPaan.widthProperty());
        taust.heightProperty().bind(juurPaan.heightProperty());
        taust.setFill(Color.LIGHTBLUE);

        // Game area pane
        Pane mänguAla = new Pane();
        mänguAla.setPrefSize(ALGNE_LAIUS, ALGNE_KÕRGUS);

        // Example game object that scales with the window
        Rectangle mänguObjekt = new Rectangle(100, 100, Color.RED);
        mänguObjekt.widthProperty().bind(mänguAla.widthProperty().multiply(0.2));
        mänguObjekt.heightProperty().bind(mänguAla.heightProperty().multiply(0.2));
        mänguObjekt.xProperty().bind(mänguAla.widthProperty().multiply(0.4));
        mänguObjekt.yProperty().bind(mänguAla.heightProperty().multiply(0.4));

        juurPaan.setOnMouseClicked(event -> {
            int lambir = rand.nextInt(3);
        });

        int lambir = 0;

        mänguObjekt.setOnMouseClicked(event -> {
            score += 40;
            switch (lambir) {
                case 0: mänguObjekt.setFill(Color.YELLOW);
                case 1: mänguObjekt.setFill(Color.GREEN);
                case 2: mänguObjekt.setFill(Color.BLUE);
            }
            System.out.println(score);
        });

        // Add game object to the game area
        mänguAla.getChildren().add(mänguObjekt);


        // Add background and game area to the root
        juurPaan.getChildren().addAll(taust, mänguAla);

        // Create scene with responsive scaling
        Scene stseen = new Scene(juurPaan, ALGNE_LAIUS, ALGNE_KÕRGUS);

        // Set up the primary stage
        pealava.setTitle("Skaleeritav Mänguaken");
        pealava.setScene(stseen);
        pealava.show();

        // Optional: Maintain aspect ratio or set minimum size
        pealava.setMinWidth(400);
        pealava.setMinHeight(300);
    }

    public static void main(String[] args) {
        launch(args);
    }
}