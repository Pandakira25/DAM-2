module com.dam.entradascine_evaluacion_t1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.dam.entradascine_evaluacion_t1 to javafx.fxml;
    exports com.dam.entradascine_evaluacion_t1;
}
