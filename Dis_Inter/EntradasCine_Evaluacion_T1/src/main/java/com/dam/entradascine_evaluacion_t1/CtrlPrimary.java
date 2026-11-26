package com.dam.entradascine_evaluacion_t1;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CtrlPrimary {

    private static final String[] t = {"Normal", "Infantil", "Día del espectador"};
    private static final int[] preciosEntradas = {8, 6, 5};
    //preciosAdicionales[0] = pochoclos, preciosAdicionales[1] = refresco
    private static final double[] preciosAdicionales = {4, 2.5};

    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> cmbTipoEntrada;
    @FXML
    private Spinner<Integer> spnCantEntrada;
    @FXML
    private CheckBox chkPochoclos;
    @FXML
    private CheckBox chkRefresco;
    @FXML
    private Button btnCalcular;
    @FXML
    private Button btnLimpiar;
    @FXML
    private TextArea txtaResumen;
    @FXML
    private Label lblPrecioTotal;

    @FXML
    public void initialize() {

        SpinnerValueFactory<Integer> valores = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 1);
        spnCantEntrada.setValueFactory(valores);

        cmbTipoEntrada.getItems().add(t[0]);
        cmbTipoEntrada.getItems().add(t[1]);
        cmbTipoEntrada.getItems().add(t[2]);
        
        lblPrecioTotal.setText("Precio Total = 0€");
    }

    @FXML
    private void CalcularPrecio() {
        //System.out.println("Dentro");
        double precioTotal = 0;
        int cantidadEntradas = spnCantEntrada.getValue();
        String mensaje = "";
        
        //Comprobar si el nombre y el tipo de entrada estan vacíos
        //System.out.println(comprobar());

        if (!comprobar()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Datos incompletos");
            error.setHeaderText("Faltan campos obligatorios");
            error.setContentText("Debes rellenar tu nombre y el tipo de entrada");
            error.showAndWait();
            return;
        }
        
        System.out.println(chkPochoclos.isSelected());
        
        if (cmbTipoEntrada.getValue().equals(t[0])) {
            precioTotal += cantidadEntradas * preciosEntradas[0];
            mensaje = "Entradas (" + cantidadEntradas + " x " + preciosEntradas[0] + "€: " + cantidadEntradas * preciosEntradas[0] + ")";
        } else if (cmbTipoEntrada.getValue().equals(t[1])) {
           precioTotal += cantidadEntradas * preciosEntradas[1];
           mensaje = "Entradas (" + cantidadEntradas + " x " + preciosEntradas[1] + "€: " + cantidadEntradas * preciosEntradas[1] + ")";
        } else if (cmbTipoEntrada.getValue().equals(t[2])) {
            precioTotal += cantidadEntradas * preciosEntradas[2];
            mensaje = "Entradas (" + cantidadEntradas + " x " + preciosEntradas[2] + "€: " + cantidadEntradas * preciosEntradas[2] + ")";
        } else if(chkRefresco.isSelected()){
            
        }
        
        if(chkPochoclos.isSelected()){
            //System.out.println("add1");
            precioTotal += preciosAdicionales[0];
            mensaje += "\n- Pchoclos agregados: " + preciosAdicionales[0] + "€";
        }
        if(chkRefresco.isSelected()){
            //System.out.println("add2");
            precioTotal += preciosAdicionales[1];
            mensaje += "\n- Refresco agregado: " + preciosAdicionales[1] + "€";
        }
        
        lblPrecioTotal.setText("Precio total= " + precioTotal + "€");
        
        txtaResumen.setText(mensaje);
    }

    private boolean comprobar() {
        //System.out.println(txtNombre.getText() + cmbTipoEntrada.getValue());
        if (txtNombre.getText().isBlank() || cmbTipoEntrada.getValue() == null) {
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void Limpiar() {
        txtNombre.clear();
        txtaResumen.clear();
        cmbTipoEntrada.setValue("");
        spnCantEntrada.getValueFactory().setValue(1);
        chkPochoclos.setSelected(false);
        chkRefresco.setSelected(false);
    }
}
