module org.example.oopryhmatoo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens pildid to javafx.graphics;

    opens org.example.oopryhmatoo2 to javafx.fxml;
    exports org.example.oopryhmatoo2;
}