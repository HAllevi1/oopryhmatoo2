package org.example.oopryhmatoo2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SisselogimisStseen {

    private AndmeHaldur andmeHaldur;
    private Stage pealava;
    private Peaklass peaklass;

    public SisselogimisStseen(Stage pealava, Peaklass peaklass) {
        this.andmeHaldur = new AndmeHaldur();
        this.pealava = pealava;
        this.peaklass = peaklass;
    }

    public Scene looStseen() {
        // Main container for background and centering login box
        StackPane juurPaaneel = new StackPane();

        // Background Pane
        GridPane taustaPaneel = new GridPane(); // Using GridPane to easily apply effect
        taustaPaneel.setAlignment(Pos.CENTER);

        try {
            Image taustapilt = new Image(getClass().getResource("/pildid/TU_Delta_MBA5215_c_MarisTomba_72dpi-1920x1280.jpg").toExternalForm());
            BackgroundImage backgroundImage = new BackgroundImage(
                    taustapilt,
                    BackgroundRepeat.NO_REPEAT, 
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    // cover=true ensures image covers area, zooming if needed
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
            );
            taustaPaneel.setBackground(new Background(backgroundImage));
        } catch (Exception e) { // Catch broader exception for safety
            System.err.println("Sisselogimise taustapilti ei leitud või ei saanud laadida! Kasutatakse vaiketausta. Viga: " + e.getMessage());
            taustaPaneel.setStyle("-fx-background-color: #e0e0e0;");
        }

        // Apply blur to the background panel
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(10); // Adjust blurriness, e.g., 5-15
        taustaPaneel.setEffect(blur);

        // VBox for the login form elements to make them stand out
        VBox sisselogimisAla = new VBox(15); // Spacing between elements
        sisselogimisAla.setAlignment(Pos.CENTER);
        sisselogimisAla.setPadding(new Insets(30)); // Padding inside the box
        sisselogimisAla.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.8);" + // White, 80% transparent
                "-fx-background-radius: 10;" + // Rounded corners
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);" // Subtle drop shadow
        );
        sisselogimisAla.setMaxWidth(350); // Max width for the login box

        Label pealkiri = new Label("Delta Clicker - Sisselogimine");
        pealkiri.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // GridPane for form fields for better alignment inside VBox
        GridPane vormiRuudustik = new GridPane();
        vormiRuudustik.setAlignment(Pos.CENTER);
        vormiRuudustik.setHgap(10);
        vormiRuudustik.setVgap(10);

        Label kasutajanimiSilt = new Label("Kasutajanimi:");
        kasutajanimiSilt.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
        vormiRuudustik.add(kasutajanimiSilt, 0, 0);

        TextField kasutajanimiTekst = new TextField();
        kasutajanimiTekst.setPromptText("Sisesta kasutajanimi");
        kasutajanimiTekst.setPrefWidth(200);
        vormiRuudustik.add(kasutajanimiTekst, 1, 0);

        Label paroolSilt = new Label("Parool:");
        paroolSilt.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
        vormiRuudustik.add(paroolSilt, 0, 1);

        PasswordField paroolTekst = new PasswordField();
        paroolTekst.setPromptText("Sisesta parool");
        paroolTekst.setPrefWidth(200);
        vormiRuudustik.add(paroolTekst, 1, 1);

        Button logiSisseNupp = new Button("Logi sisse");
        logiSisseNupp.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 15;");
        logiSisseNupp.setPrefWidth(150);

        Label teadeSilt = new Label();
        teadeSilt.setTextFill(Color.RED);
        teadeSilt.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        // Add elements to the login area VBox
        sisselogimisAla.getChildren().addAll(pealkiri, vormiRuudustik, logiSisseNupp, teadeSilt);

        // Add background and login area to the root StackPane
        juurPaaneel.getChildren().addAll(taustaPaneel, sisselogimisAla);
        StackPane.setAlignment(sisselogimisAla, Pos.CENTER); // Center login box in StackPane

        logiSisseNupp.setOnAction(e -> {
            String kasutajanimi = kasutajanimiTekst.getText();
            String parool = paroolTekst.getText();
            try {
                if (andmeHaldur.kontrolliSisselogimist(kasutajanimi, parool)) {
                    peaklass.naiteManguStseeni();
                }
            } catch (ValedKasutajaAndmed ex) {
                teadeSilt.setText("Vale kasutajanimi või parool!");
            }
        });

        return new Scene(juurPaaneel, Peaklass.getAlgneLaius(), Peaklass.getAlgneKorgus());
    }
}
