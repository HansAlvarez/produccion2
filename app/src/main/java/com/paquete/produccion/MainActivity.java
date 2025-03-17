package com.paquete.produccion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnMaterials, btnProduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los botones
        btnMaterials = findViewById(R.id.btnMaterials);
        btnProduction = findViewById(R.id.btnProduction);

        // Redirigir al login de Materiales
        btnMaterials.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MaterialsLoginActivity.class);
            startActivity(intent);
        });

        // Redirigir al login de Producción
        btnProduction.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductionLoginActivity.class);
            startActivity(intent);
        });
    }
}