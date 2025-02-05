package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.databinding.DetailBirdBinding;
import com.lmr.pajareandoapp.viewmodels.BirdDetailViewModel;

public class BirdDetailActivity extends AppCompatActivity {
    private BirdDetailViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private boolean isDarkMode;
    private String birdId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        DetailBirdBinding binding = DataBindingUtil.setContentView(this, R.layout.detail_bird);

        // Configura el ViewModel
        viewModel = new ViewModelProvider(this).get(BirdDetailViewModel.class);

        // Configuración de preferencias para modo oscuro
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        setDarkMode(isDarkMode);

        // Configuración de la barra de herramientas con botones personalizados
        binding.backButton.setOnClickListener(v -> onSupportNavigateUp());
        binding.logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(BirdDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        binding.darkModeButton.setOnClickListener(v -> {
            isDarkMode = !isDarkMode;
            setDarkMode(isDarkMode);
        });

        // Obtener el ID del ave desde el intent
        birdId = getIntent().getStringExtra("BIRD_ID");
        if (birdId != null) {
            viewModel.loadBirdById(birdId);
            viewModel.checkIfFavorite(birdId);
        } else {
            Toast.makeText(this, "Error: ID del ave no encontrado.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Observa los cambios en la información del ave y actualiza la UI
        viewModel.getBirdLiveData().observe(this, bird -> {
            if (bird != null) {
                binding.setBird(bird);
            } else {
                Toast.makeText(this, "Error al obtener los datos del ave.", Toast.LENGTH_SHORT).show();
            }
        });

        // Manejo del botón de favoritos
        viewModel.getIsFavorite().observe(this, isFavorite -> {
            binding.fab.setImageResource(isFavorite ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);
        });

        binding.fab.setOnClickListener(view -> {
            viewModel.toggleFavorite(birdId);
            Toast.makeText(this, "Cambiando estado de favorito...", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    private void setDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Guarda la preferencia del modo oscuro
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDarkMode", isDarkMode);
        editor.apply();
    }
}