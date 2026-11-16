module com.dam.pn_actividad_1_formulariojavafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.dam.pn_actividad_1_formulariojavafx to javafx.fxml;
    exports com.dam.pn_actividad_1_formulariojavafx;
}
