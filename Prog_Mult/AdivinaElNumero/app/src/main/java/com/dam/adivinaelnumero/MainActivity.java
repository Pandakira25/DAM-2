package com.dam.adivinaelnumero;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;

import kotlin.time.Clock;

public class MainActivity extends AppCompatActivity {

    private int nCorrecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                    Math.max(systemBars.bottom, ime.bottom));
            return insets;
        });
        Random rd = new Random();
        nCorrecto = rd.nextInt(101);
        System.out.println(nCorrecto);
    }

    int intentos = 5;
    public void adivinar(View v){
        EditText edtxtNumber = findViewById(R.id.edtxtNumber);
        Button btnComp = findViewById(R.id.btnComp);
        TextView txtClue = findViewById(R.id.txtClue);
        TextView txtIntentos = findViewById(R.id.txtIntentos);

        int n = Integer.parseInt(edtxtNumber.getText().toString());

        if(intentos > 0){
            if(n < nCorrecto){
                //el número es mayor
                txtClue.setText("El número es mayor! Sigue intentando");
                intentos --;
                txtIntentos.setText("Te quedan " + intentos + " intentos");
            }else if(n > nCorrecto){
                //el número es menor
                txtClue.setText("El número es menor! Sigue intentando");
                intentos --;
                txtIntentos.setText("Te quedan " + intentos + " intentos");
            }else{
                txtClue.setText("¡ACERTASTE!");
                txtIntentos.setVisibility(View.INVISIBLE);
                edtxtNumber.setEnabled(false);
                btnComp.setEnabled(false);
            }
        }else{
            txtClue.setText("Te has quedado sin intentos");
            txtIntentos.setVisibility(View.INVISIBLE);
            edtxtNumber.setEnabled(false);
            btnComp.setEnabled(false);
        }
    }
}