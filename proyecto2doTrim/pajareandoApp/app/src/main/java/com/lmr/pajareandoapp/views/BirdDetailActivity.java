package com.lmr.pajareandoapp.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.databinding.DetailBirdBinding;
import com.lmr.pajareandoapp.viewmodels.BirdDetailViewModel;

/**
 * Actividad para mostrar los detalles de un ave.
 */
public class BirdDetailActivity extends AppCompatActivity {
    private BirdDetailViewModel viewModel;
    private FloatingActionButton fab;
    private String birdId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Infla el layout con DataBinding
        DetailBirdBinding binding = DetailBirdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa el ViewModel
        viewModel = new ViewModelProvider(this).get(BirdDetailViewModel.class);

        // Configura el Toolbar
        setSupportActionBar(binding.toolbar); // Usa el toolbar como ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Muestra el ícono de retroceso
        }

        // Recupera el ID del ave desde el Intent
        birdId = getIntent().getStringExtra("BIRD_ID");
        if (birdId != null) {
            viewModel.loadBirdById(birdId);
            viewModel.checkIfFavorite(birdId);
        } else {
            Toast.makeText(this, "Error: ID del ave no encontrado.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Observa los cambios en los datos del ave y actualiza el binding
        viewModel.getBirdLiveData().observe(this, bird -> {
            if (bird != null) {
                binding.setBird(bird);
            } else {
                Toast.makeText(this, "Error al obtener los datos del ave.", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura el FAB (Favoritos)
        fab = findViewById(R.id.fab);
        viewModel.getIsFavorite().observe(this, isFavorite -> {
            // Actualiza el estado del FAB según el valor de isFavorite
            fab.setImageResource(isFavorite ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);
        });

        fab.setOnClickListener(view -> {
            viewModel.toggleFavorite(birdId);
            Toast.makeText(this, "Cambiando estado de favorito...", Toast.LENGTH_SHORT).show();
        });
    }

    // Configura la navegación hacia atrás
    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
