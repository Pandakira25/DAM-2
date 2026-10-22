module com.mycompany.primer_proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.primer_proyecto to javafx.fxml;
    exports com.mycompany.primer_proyecto;
}
