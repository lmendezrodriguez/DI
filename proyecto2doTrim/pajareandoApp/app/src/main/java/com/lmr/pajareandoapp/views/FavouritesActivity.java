package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.adapters.BirdAdapter;
import com.lmr.pajareandoapp.databinding.ActivityFavouritesBinding;
import com.lmr.pajareandoapp.viewmodels.FavouritesViewModel;

import java.util.ArrayList;

/**
 * Actividad que muestra la lista de aves favoritas del usuario.
 * Permite visualizar detalles de cada ave y gestionar preferencias.
 */
public class FavouritesActivity extends AppCompatActivity {
    private BirdAdapter birdAdapter; // Adaptador para la lista de aves favoritas
    private boolean isDarkMode; // Estado del modo oscuro
    private SharedPreferences sharedPreferences; // Preferencias de usuario
    private ActivityFavouritesBinding binding; // Binding para la vista
    /**
     * Método onCreate que inicializa la actividad y configura los elementos de la UI.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourites);

        // Configura el ViewModel para gestionar los datos de las aves favoritas
        FavouritesViewModel favouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        // Configura el adaptador con un listener para abrir detalles de cada ave favorita
        birdAdapter = new BirdAdapter(new ArrayList<>(), birdId -> {
            Intent intent = new Intent(this, BirdDetailActivity.class);
            intent.putExtra("BIRD_ID", birdId);
            startActivity(intent);
        });

        // Configura el RecyclerView con un diseño en lista vertical
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(birdAdapter);

        // Observa los cambios en la lista de aves favoritas y actualiza el adaptador
        favouritesViewModel.getFavouriteBirdsLiveData().observe(this, birds -> {
            if (birds != null) {
                birdAdapter.setBirds(birds);
            } else {
                Toast.makeText(this, "No tienes aves favoritas", Toast.LENGTH_SHORT).show();
            }
        });

        // Configuración del modo oscuro basado en preferencias guardadas
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        setDarkMode(isDarkMode);

        // Configuración de botones en la barra superior
        binding.backButton.setOnClickListener(v -> onSupportNavigateUp());
        binding.logoutButton.setOnClickListener(v -> {
            // Cierra sesión y vuelve a la pantalla de login
            Intent intent = new Intent(FavouritesActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        binding.darkModeButton.setOnClickListener(v -> {
            // Alterna el modo oscuro y guarda la preferencia
            isDarkMode = !isDarkMode;
            setDarkMode(isDarkMode);
        });
    }

    /**
     * Método para manejar la navegación hacia atrás.
     * @return true si la navegación es exitosa.
     */
    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    /**
     * Método para establecer el modo oscuro y guardar la preferencia.
     * @param isDarkMode Indica si el modo oscuro debe estar activado o desactivado.
     */
    private void setDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            binding.darkModeButton.setIconResource(R.drawable.baseline_light_mode_24);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            binding.darkModeButton.setIconResource(R.drawable.baseline_dark_mode_24);
        }

        // Guarda la preferencia del modo oscuro en almacenamiento compartido
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDarkMode", isDarkMode);
        editor.apply();
    }
}
