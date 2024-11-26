package com.example.mycatalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Actividad que muestra un catálogo y permite la navegación a los detalles de un elemento.
 */
public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llamada al método onCreate de la superclase para inicializar la actividad
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Vincular el botón definido en el layout
        Button navigateButton = findViewById(R.id.button_detail);

        // Configurar el evento onClick para el botón
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear y lanzar un Intent para abrir la actividad DetailActivity
                Intent intent = new Intent(CatalogActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }
}