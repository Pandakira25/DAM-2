package com.dam.ejercicio28_10_26;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CicloDeVida";

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

        //Hay varias acciones que se desencadenan como el onStart, onPause, etc que podemos usar
        Log.d(TAG, "onCreate llamado");
        Toast.makeText(this, "onCreate llamado", Toast.LENGTH_SHORT).show();
    }

    EditText etNombre = findViewById(R.id.etNombre);
    Button btnEnviar = findViewById(R.id.btnEnviar);

    public void enviarDatos(){

    }
}