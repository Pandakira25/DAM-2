package com.dam.pn_actividad_1_formulariojavafx;

import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class CtrlFormulario {

    @FXML
    private TextField txfName;
    
    @FXML
    private TextField txfSurname;
    
    @FXML
    private TextField txfEmail;
    
    @FXML
    private Spinner<Integer> spnAge;
    
    @FXML
    public void initialize(){
        //Inicializando el spinner con los valores enteros: 0 de edad mínima, 120 de edad máxima, 10 de edad por defecto
        SpinnerValueFactory<Integer> valores = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,120,10);
        //Añado esos valores al spiner que he creado en el fxml
        spnAge.setValueFactory(valores);
    }
    
    
    //Método para darle funcionalidad al botóon de enviar
    @FXML
    private void send(){
        String nombre = txfName.getText().trim();
        String apellidos = txfSurname.getText().trim();
        String mail = txfEmail.getText().trim();

        // Si falta algún campo, avisamos y salimos sin mostrar la info
        if (nombre.isEmpty() || apellidos.isEmpty() || mail.isEmpty()) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Datos incompletos");
            error.setHeaderText("Faltan campos obligatorios");
            error.setContentText("Debes rellenar nombre, apellidos y correo.");
            error.showAndWait();
            return;
        }

        // Solo llegamos aquí si todo está completo
        int edad = spnAge.getValue();

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Datos del usuario");
        alerta.setHeaderText("Formulario enviado");
        alerta.setContentText("Nombre: " + nombre +
                "\nApellidos: " + apellidos +
                "\nCorreo: " + mail +
                "\nEdad: " + edad);
        //Lanzamos el mensaje de alerta en modo modal, quiere decir que bloquea la ejecucion de la aplicación hasta que el usuario cierre o acepte la ventana
        alerta.showAndWait();
    }
    
    @FXML
    private void clear(){
        txfName.clear();
        txfSurname.clear();
        txfEmail.clear();
        spnAge.getValueFactory().setValue(18);
    }
    
    @FXML
    private void exit(){
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Cerrar aplicación");
       alert.setHeaderText(null);
       alert.setContentText("¿Desea cerrar la aplicación?");
       
       Optional<ButtonType> result = alert.showAndWait();
       
       //Si se ha pulsado el botón "Aceptar"
       if(result.get() == ButtonType.OK) {
           System.exit(0);
       } else {
           txfName.requestFocus();
       }
    }
}
