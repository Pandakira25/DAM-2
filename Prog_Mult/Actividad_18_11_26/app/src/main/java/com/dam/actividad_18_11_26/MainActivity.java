package com.dam.actividad_18_11_26;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText txtNombre, txtCorreo, txtEdad;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Enlazamos los distintos componentes
        txtNombre = findViewById(R.id.txtNombre);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtEdad = findViewById(R.id.txtEdad);

        //Evento del botón
        btnRegistrar.setOnClickListener(v -> {
            String nombre = txtNombre.getText().toString().trim();
            String correo = txtCorreo.getText().toString().trim();
            String edad = txtEdad.getText().toString().trim();

            if(nombre.isEmpty() || correo.isEmpty() || edad.isEmpty()){
                Toast.makeText(this, "Debes completar los campos", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Usuario registrado: " + nombre, Toast.LENGTH_SHORT).show();
            }
        });
    }
}