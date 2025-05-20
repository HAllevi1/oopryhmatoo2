package org.example.oopryhmatoo2;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;

public class Peaklass extends Application {

    private void kontrolliJaRakendaUuendus(Button nupp, int hind, int uusKordisti, Label skooriTekst) {
        if (skoor >= hind && !nupp.isDisabled()) {
            skoor -= hind;
            kordisti = uusKordisti;
            nupp.setDisable(true);
            skooriTekst.setText("Delta-BUCKS: " + skoor);
        }
    }

    // Üld andmed
    private static final double ALGNE_LAIUS = 900;
    private static final double ALGNE_KORGUS = 650;
    private int kordisti = 1;
    private int skoor = 0;

    private Stage pealava;

    // Meetodid staatilistele muutujatele ligipääsuks
    public static double getAlgneLaius() {
        return ALGNE_LAIUS;
    }

    public static double getAlgneKorgus() {
        return ALGNE_KORGUS;
    }

    @Override
    public void start(Stage esmaneLava) {
        this.pealava = esmaneLava;
        naiteSisselogimisStseeni();
    }

    public void naiteSisselogimisStseeni() {
        SisselogimisStseen sisselogimine = new SisselogimisStseen(pealava, this);
        Scene sisselogimisStseen = sisselogimine.looStseen();

        pealava.setTitle("Delta Clicker - Sisselogimine");
        pealava.setScene(sisselogimisStseen);
        if (!pealava.isShowing()) {
            pealava.show();
        }
    }


    public void naiteManguStseeni() {
        // I ÜLD TAUST
        StackPane juurPaan = new StackPane();
        juurPaan.setStyle("-fx-background-color: #adb16b;");

        // II SISETAUST
        Pane mänguAla = new Pane();

        // Taustapilt
        ImageView taustpilt = new ImageView(new Image(getClass().getResource("/pildid/taust.png").toExternalForm()));
        taustpilt.setFitWidth(ALGNE_LAIUS);
        taustpilt.setFitHeight(ALGNE_KORGUS);

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

        // Klikatav asi
        Rectangle klikatavAsi = new Rectangle(500, 250, Color.SANDYBROWN);
        klikatavAsi.setLayoutX(80);
        klikatavAsi.setLayoutY(170);
        Image delta = new Image(getClass().getResource("/pildid/delta.png").toExternalForm());
        klikatavAsi.setFill(new ImagePattern(delta));

        // Pööramine
        Rotate pööramine = new Rotate(0, klikatavAsi.getWidth() / 2, klikatavAsi.getHeight() / 2);
        klikatavAsi.getTransforms().add(pööramine);

        // Klikkamine annab skoori + pöörab + tausta effekt
        klikatavAsi.setOnMouseClicked(e -> {
            skoor += kordisti;
            skooriLabel.setText("Delta-BUCKS: " + skoor);

            // Arvutab randomiga pööramise
            double pöördeMuutus = Math.random() * 15;
            double pööre = Math.random() < 0.5 ? pöördeMuutus : -pöördeMuutus;
            pööramine.setAngle(pööre);

            // Langev asi
            Image klikatavTaustPilt = new Image(getClass().getResource("/pildid/klikatavtaust.png").toExternalForm());
            ImageView efekt = new ImageView(klikatavTaustPilt);
            efekt.setFitWidth(30);
            efekt.setFitHeight(30);
            // Arvutab välja, et kust lanev asi kukkub
            efekt.setLayoutX(klikatavAsi.getLayoutX() + 250 + Math.random() * 550 - 250);
            efekt.setLayoutY(klikatavAsi.getLayoutY() - 150);

            ((Pane) klikatavAsi.getParent()).getChildren().add(efekt);

            // Langetamise animatsioon
            TranslateTransition langetamine = new TranslateTransition(Duration.seconds(3), efekt);
            langetamine.setByY(550);
            langetamine.setOnFinished(event -> {
                ((Pane) klikatavAsi.getParent()).getChildren().remove(efekt);
            });
            langetamine.play();
        });


        // Uuenduste taust
        Rectangle uuendusTaust = new Rectangle(280, 610, Color.WHEAT);
        uuendusTaust.setArcWidth(30);
        uuendusTaust.setArcHeight(30);
        uuendusTaust.setLayoutX(610);
        uuendusTaust.setLayoutY(20);

        // Uuendused
        // Uuendus 1
        Button uuendus1 = new Button("Osta II korrus\n 10 Delta-BUCKS(+1)");
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
        Button uuendus2 = new Button("Osta III korrus\n 40 Delta-BUCKS(+2)");
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
        Button uuendus3 = new Button("Osta IV korrus\n 80 Delta-BUCKS(+4)");
        uuendus3.setStyle("-fx-background-color: #cac0bd;");
        uuendus3.setLayoutX(620);
        uuendus3.setLayoutY(190);
        uuendus3.setPrefWidth(260);
        ImageView pilt3 = new ImageView(new Image(getClass().getResource("/pildid/uuendus3.png").toExternalForm()));
        pilt3.setFitWidth(120);
        pilt3.setFitHeight(60);
        uuendus3.setGraphic(pilt3);
        uuendus3.setOnAction(e -> kontrolliJaRakendaUuendus(uuendus3, 80, 8, skooriLabel));

        // Uuendus 4
        Button uuendus4 = new Button("Osta õpilastele pulgakomm\n 180 Delta-BUCKS(+6)");
        uuendus4.setStyle("-fx-background-color: #cac0bd;");
        uuendus4.setLayoutX(620);
        uuendus4.setLayoutY(265);
        uuendus4.setPrefWidth(260);
        ImageView pilt4 = new ImageView(new Image(getClass().getResource("/pildid/uuendus4.png").toExternalForm()));
        pilt4.setFitWidth(60);
        pilt4.setFitHeight(60);
        uuendus4.setGraphic(pilt4);
        uuendus4.setOnAction(e -> kontrolliJaRakendaUuendus(uuendus4, 180, 14, skooriLabel));

        // Uuendus 5
        Button uuendus5 = new Button("Saa matemaatilisest \nmaailmapildist läbi \n420 Delta-BUCKS(+10)");
        uuendus5.setStyle("-fx-background-color: #cac0bd;");
        uuendus5.setLayoutX(620);
        uuendus5.setLayoutY(340);
        uuendus5.setPrefWidth(260);
        ImageView pilt5 = new ImageView(new Image(getClass().getResource("/pildid/uuendus5.png").toExternalForm()));
        pilt5.setFitWidth(60);
        pilt5.setFitHeight(60);
        uuendus5.setGraphic(pilt5);
        uuendus5.setOnAction(e -> kontrolliJaRakendaUuendus(uuendus5, 420, 24, skooriLabel));

        // Logi välja nupp
        Button logiValjaNupp = new Button("Logi välja");
        logiValjaNupp.setStyle(
                "-fx-background-color: #f44336;" + // Punane taust
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20;");
        double nupuLaius = 200;
        logiValjaNupp.setPrefWidth(nupuLaius);
        logiValjaNupp.setLayoutX(uuendusTaust.getLayoutX() + (uuendusTaust.getWidth() - nupuLaius) / 2); // Keskele
        logiValjaNupp.setLayoutY(uuendusTaust.getLayoutY() + uuendusTaust.getHeight() - 50); // All äärde, 50px ülespoole

        logiValjaNupp.setOnAction(e -> naiteSisselogimisStseeni());

        // Lisab kõik mänguala sisse
        mänguAla.getChildren().addAll(taustpilt, uuendusTaust, klikatavAsi, skooriLabel, uuendus1, uuendus2, uuendus3, uuendus4, uuendus5, logiValjaNupp);

        // Paneb mänguala Group'i sisse ja seob selle suuruse StackPane suurusega,
        // et kogu sisu skaleeruks proportsionaalselt akna suuruse muutmisel.
        Group mänguGroup = new Group(mänguAla);
        mänguGroup.scaleXProperty().bind(juurPaan.widthProperty().divide(ALGNE_LAIUS));
        mänguGroup.scaleYProperty().bind(juurPaan.heightProperty().divide(ALGNE_KORGUS));

        juurPaan.getChildren().addAll(mänguGroup);

        Scene stseen = new Scene(juurPaan, ALGNE_LAIUS, ALGNE_KORGUS);
        pealava.setTitle("Delta Clicker");
        pealava.setScene(stseen);
        pealava.show();
    }

    public static void main(String[] args) {
        System.out.println("Teretulemast mängu! Tegu on Delta-clicker mänguga.\n" +
                "Et edasi liikuda on teil 2 valikut:\n" +
                "1. sisestada uus kasutajanimi ja parool ning registreerida ennast kasutajaks\n" +
                "2. sisestada enda kasutajanimi ja parool ning logida sisse\n\n" +
                "Edasi avaneb teile mängu aken, vasakus aknas saate vajutada Delta maja ning teenida sellega Delta-BUCKSe.\n" +
                "Delta BUCKSide eest saate paremal aknas osta uuendusi, tänu millele järgmisel korral Delta maja klikkides saate rohkem raha.\n" +
                "Head mänguelamust! :P");
        launch();
    }
}
