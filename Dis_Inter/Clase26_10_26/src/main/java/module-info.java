module com.dam.clase26_10_26 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.dam.clase26_10_26 to javafx.fxml;
    exports com.dam.clase26_10_26;
}
