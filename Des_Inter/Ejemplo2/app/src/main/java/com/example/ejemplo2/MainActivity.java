package com.example.ejemplo2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplo2.Pojo.DataManager;
import com.example.ejemplo2.Pojo.Lenguaje;

public class MainActivity extends AppCompatActivity {

    private DataManager dm = new DataManager();

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
        TextView txtLenguaje = findViewById(R.id.txtJava);
        Button btnDesc = findViewById(R.id.btn1);
        Button btnOtro = findViewById(R.id.btn2);

        btnOtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dm.siguienteLenguaje();
                actualizarVista(txtLenguaje);
            }
        });

        btnDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lenguaje lenguaje = dm.getLenguaje();
                txtLenguaje.setText(lenguaje.getDescripcion());
            }
        });

        actualizarVista(txtLenguaje);
    }

    public void actualizarVista(TextView txtLenguaje){
        Lenguaje lenguaje = dm.getLenguaje();

        txtLenguaje.setText(lenguaje.getNombre());
    }
}