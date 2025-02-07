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

/**
 * Esta clase representa la actividad que muestra los detalles de un ave específica.
 * Incluye funcionalidad para gestionar favoritos y modo oscuro.
 */
public class BirdDetailActivity extends AppCompatActivity {

    private BirdDetailViewModel viewModel; // ViewModel que maneja la lógica de negocio para esta actividad
    private SharedPreferences sharedPreferences; // Preferencias para guardar configuraciones como el modo oscuro
    private boolean isDarkMode; // Indica si el modo oscuro está habilitado
    private String birdId; // ID del ave seleccionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Activa el diseño de pantalla completa
        DetailBirdBinding binding = DataBindingUtil.setContentView(this, R.layout.detail_bird); // Enlaza la vista con la actividad

        // Configura el ViewModel
        viewModel = new ViewModelProvider(this).get(BirdDetailViewModel.class);

        // Configura las preferencias de usuario para el modo oscuro
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        setDarkMode(isDarkMode); // Aplica el modo oscuro según la preferencia guardada

        // Configura los botones personalizados en la barra de herramientas
        binding.backButton.setOnClickListener(v -> onSupportNavigateUp()); // Botón para volver atrás
        binding.logoutButton.setOnClickListener(v -> { // Botón para cerrar sesión
            Intent intent = new Intent(BirdDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        binding.darkModeButton.setOnClickListener(v -> { // Botón para alternar el modo oscuro
            isDarkMode = !isDarkMode;
            setDarkMode(isDarkMode);
        });

        // Obtiene el ID del ave desde el intent que inició la actividad
        birdId = getIntent().getStringExtra("BIRD_ID");
        if (birdId != null) {
            viewModel.loadBirdById(birdId); // Carga los detalles del ave
            viewModel.checkIfFavorite(birdId); // Verifica si el ave es favorita
        } else {
            // Muestra un mensaje de error y finaliza la actividad si no hay ID de ave
            Toast.makeText(this, "Error: ID del ave no encontrado.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Observa los datos del ave y actualiza la interfaz cuando cambien
        viewModel.getBirdLiveData().observe(this, bird -> {
            if (bird != null) {
                binding.setBird(bird); // Actualiza los datos en la vista
            } else {
                Toast.makeText(this, "Error al obtener los datos del ave.", Toast.LENGTH_SHORT).show();
            }
        });

        // Observa si el ave es favorita y actualiza el botón de favoritos
        viewModel.getIsFavorite().observe(this, isFavorite -> {
            binding.fab.setImageResource(isFavorite ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);
        });

        // Configura el clic del botón de favoritos
        binding.fab.setOnClickListener(view -> {
            viewModel.toggleFavorite(birdId); // Cambia el estado de favorito del ave
            Toast.makeText(this, "Cambiando estado de favorito...", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Maneja el evento de volver atrás cuando el usuario pulsa el botón correspondiente.
     */
    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    /**
     * Configura el modo oscuro y guarda la preferencia en almacenamiento.
     *
     * @param isDarkMode Indica si el modo oscuro debe estar habilitado.
     */
    private void setDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Guarda la preferencia del modo oscuro en almacenamiento compartido
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDarkMode", isDarkMode);
        editor.apply();
    }
}
