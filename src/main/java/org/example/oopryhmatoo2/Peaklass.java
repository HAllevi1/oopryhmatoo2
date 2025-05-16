package org.example.oopryhmatoo2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Peaklass extends Application {

    private void kontrolliJaRakendaUuendus(Button nupp, int hind, int uusKordisti, Label skooriLabel) {
        if (skoor >= hind && !nupp.isDisabled()) {
            skoor -= hind;
            kordisti = uusKordisti;
            nupp.setDisable(true);
            skooriLabel.setText("Delta-BUCKS: " + skoor);
        }
    }


    // Üld andmed
    private static final double ALGNE_LAIUS = 900;
    private static final double ALGNE_KÕRGUS = 650;
    private int kordisti = 1;
    private int skoor = 0;

    @Override
    public void start(Stage pealava) {

        // I ÜLD TAUST
        StackPane juurPaan = new StackPane();
        juurPaan.setStyle("-fx-background-color: #adb16b;");

        // II SISETAUST (vaja selleks, et elementide suurus muutuks ühtlaselt ekraani suurust muutes)
        Pane mänguAla = new Pane();
        mänguAla.setPrefSize(ALGNE_LAIUS, ALGNE_KÕRGUS);

        // Taustapilt
        ImageView taustpilt = new ImageView(new Image(getClass().getResource("/pildid/taust.png").toExternalForm()));
        taustpilt.setFitWidth(ALGNE_LAIUS);
        taustpilt.setFitHeight(ALGNE_KÕRGUS);

        // Klikatav asi
        Rectangle klikatavAsi = new Rectangle(500, 250, Color.SANDYBROWN);
        klikatavAsi.setLayoutX(80);
        klikatavAsi.setLayoutY(170);
        Image delta = new Image(getClass().getResource("/pildid/delta.png").toExternalForm());
        klikatavAsi.setFill(new ImagePattern(delta));
        // Pööramine
        Rotate pööramine = new Rotate(0, klikatavAsi.getWidth() / 2, klikatavAsi.getHeight() / 2);
        klikatavAsi.getTransforms().add(pööramine);

        // Skoor
        Label skooriLabel = new Label("Delta-BUCKS: 0");
        skooriLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #ecc550;" +
                        "-fx-background-color: #333333;" +
                        "-fx-padding: 10;");
        skooriLabel.setLayoutX(200);
        skooriLabel.setLayoutY(600);

        // Klikkamine annab skoori + pööramine
        klikatavAsi.setOnMouseClicked(e -> {
            skoor += kordisti;
            skooriLabel.setText("Delta-BUCKS: " + skoor);
            // Arvutab randomiga klikatavaAsja pööramist
            double pöördeMuutus = Math.random() * 15;
            double pööre = Math.random() < 0.5 ? pöördeMuutus : -pöördeMuutus;
            pööramine.setAngle(pööre);
        });


        // Uuenduste taust
        Rectangle uuendusTaust = new Rectangle(280, 610, Color.WHEAT);
        uuendusTaust.setArcWidth(30);
        uuendusTaust.setArcHeight(30);
        uuendusTaust.setLayoutX(610);
        uuendusTaust.setLayoutY(20);

        // Uuendused
        // Uuendus 1
        Button uuendus1 = new Button("Delta II korrus\n 10 Delta-BUCKS(+1)");
        uuendus1.setStyle("-fx-background-color: #cac0bd;");
        uuendus1.setLayoutX(620);
        uuendus1.setLayoutY(30);
        uuendus1.setPrefWidth(260);
        // Pilt tehakse sobivaks
        ImageView pilt1 = new ImageView(new Image(getClass().getResource("/pildid/uuendus1.png").toExternalForm()));
        pilt1.setFitWidth(120);   // pildi laius
        pilt1.setFitHeight(70);  // pildi kõrgus
        uuendus1.setGraphic(pilt1);
        // Kui nuppu vajutatakse siis käivitatakse kontrolliJaRakendaUuendus
        uuendus1.setOnAction(e -> kontrolliJaRakendaUuendus(uuendus1, 10, 2, skooriLabel));

        // Uuendus 2
        Button uuendus2 = new Button("Delta II korrus\n 40 Delta-BUCKS(+2)");
        uuendus2.setStyle("-fx-background-color: #cac0bd;");
        uuendus2.setLayoutX(620);
        uuendus2.setLayoutY(115);
        uuendus2.setPrefWidth(260);
        ImageView pilt2 = new ImageView(new Image(getClass().getResource("/pildid/uuendus2.png").toExternalForm()));
        pilt2.setFitWidth(120);
        pilt2.setFitHeight(60);
        uuendus2.setGraphic(pilt2);
        uuendus2.setOnAction(e -> kontrolliJaRakendaUuendus(uuendus2, 40, 4, skooriLabel));

        // Uuendus 3
        Button uuendus3 = new Button("Delta II korrus\n 80 Delta-BUCKS(+4)");
        uuendus3.setStyle("-fx-background-color: #cac0bd;");
        uuendus3.setLayoutX(620);
        uuendus3.setLayoutY(190);
        uuendus3.setPrefWidth(260);
        ImageView pilt3 = new ImageView(new Image(getClass().getResource("/pildid/uuendus3.png").toExternalForm()));
        pilt3.setFitWidth(120);
        pilt3.setFitHeight(60);
        uuendus3.setGraphic(pilt3);
        uuendus3.setOnAction(e -> kontrolliJaRakendaUuendus(uuendus3, 80, 8, skooriLabel));

        // Lisab kõik mänguala sisse
        mänguAla.getChildren().addAll(taustpilt, uuendusTaust, klikatavAsi, skooriLabel, uuendus1, uuendus2, uuendus3);

        // Paneb mänguala Group'i sisse ja seob selle suuruse StackPane suurusega,
        // et kogu sisu skaleeruks proportsionaalselt akna suuruse muutmisel.
        Group mänguGroup = new Group(mänguAla);
        mänguGroup.scaleXProperty().bind(juurPaan.widthProperty().divide(ALGNE_LAIUS));
        mänguGroup.scaleYProperty().bind(juurPaan.heightProperty().divide(ALGNE_KÕRGUS));

        juurPaan.getChildren().addAll(mänguGroup);

        Scene stseen = new Scene(juurPaan, ALGNE_LAIUS, ALGNE_KÕRGUS);
        pealava.setTitle("Delta Clicker");
        pealava.setScene(stseen);
        pealava.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
