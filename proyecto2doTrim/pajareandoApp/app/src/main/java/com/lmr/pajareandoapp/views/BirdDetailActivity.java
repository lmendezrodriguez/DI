package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lmr.pajareandoapp.databinding.DetailBirdBinding;
import com.squareup.picasso.Picasso;

/**
 * Actividad para mostrar los detalles de un ave.
 */
public class BirdDetailActivity extends AppCompatActivity {

    /**
     * Es llamado cuando la actividad se crea
     *
     * @param savedInstanceState representa el estado de la actividad antes de que se destruya     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Infla el layout de la actividad
        DetailBirdBinding binding = DetailBirdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recupera el intent que inició esta actividad
        Intent intent = getIntent();

        // Inicializa las vistas con los datos del intent
        TextView commonNameTextView = binding.birdCommonName;
        TextView scientificNameTextView = binding.birdScientificName;
        TextView descriptionTextView = binding.birdDescription;
        ImageView photoView = binding.birdImage;

        // Verifica si el intent contiene datos, si contiene datos los muestra en las vistas
        if (intent != null) {
            // Populate views with intent data
            String commonName = intent.getStringExtra("Common_name");
            commonNameTextView.setText(commonName);

            String scientificName = intent.getStringExtra("Scientific_name");
            scientificNameTextView.setText(scientificName);

            String description = intent.getStringExtra("Description");
            descriptionTextView.setText(description);

            String urlPhoto = intent.getStringExtra("Url_photo");
            Picasso.get().load(urlPhoto).into(photoView);

            if (commonName == null || scientificName == null || description == null || urlPhoto == null) {
                Toast.makeText(this, "Error al obtener los datos del ave.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
