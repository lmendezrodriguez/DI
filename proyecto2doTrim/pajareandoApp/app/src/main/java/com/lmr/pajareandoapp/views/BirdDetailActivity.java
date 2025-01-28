package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lmr.pajareandoapp.databinding.DetailBirdBinding;
import com.lmr.pajareandoapp.models.Bird;

public class BirdDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Infla el layout de la actividad
        DetailBirdBinding binding = DetailBirdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recupera el intent que inició esta actividad
        Intent intent = getIntent();

        if (intent != null) {
            // Obtener datos del intent
            String commonName = intent.getStringExtra("Common_name");
            String scientificName = intent.getStringExtra("Scientific_name");
            String description = intent.getStringExtra("Description");
            String urlPhoto = intent.getStringExtra("Url_photo");

            if (commonName != null && scientificName != null && description != null && urlPhoto != null) {
                // Crear objeto Bird con los datos
                Bird bird = new Bird(commonName, scientificName, description, urlPhoto);

                // Configurar el objeto Bird en el binding
                binding.setBird(bird);
            } else {
                Toast.makeText(this, "Error al obtener los datos del ave.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
