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

import com.google.android.material.button.MaterialButton;
import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.adapters.BirdAdapter;
import com.lmr.pajareandoapp.databinding.ActivityDashboardBinding;
import com.lmr.pajareandoapp.viewmodels.DashboardViewModel;

import java.util.ArrayList;

/**
 * Actividad principal del dashboard donde se muestran las aves disponibles.
 * Permite navegar a los detalles de cada ave y gestionar preferencias de usuario.
 */
public class DashboardActivity extends AppCompatActivity {
    private BirdAdapter birdAdapter; // Adaptador para la lista de aves
    private boolean isDarkMode; // Estado del modo oscuro
    private SharedPreferences sharedPreferences; // Preferencias de usuario

    /**
     * Método onCreate que inicializa la vista y configura los elementos de la UI.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configura el ViewModel y el binding con la vista
        ActivityDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Configura el adaptador con un listener para abrir los detalles de cada ave
        birdAdapter = new BirdAdapter(new ArrayList<>(), birdId -> {
            Intent intent = new Intent(this, BirdDetailActivity.class);
            intent.putExtra("BIRD_ID", birdId);
            startActivity(intent);
        });

        // Configura el RecyclerView con un diseño en lista vertical
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(birdAdapter);

        // Observa los cambios en la lista de aves y actualiza el adaptador
        dashboardViewModel.getBirdsLiveData().observe(this, birds -> {
            if (birds != null) {
                birdAdapter.setBirds(birds);
            } else {
                Toast.makeText(this, "No se encontraron aves", Toast.LENGTH_SHORT).show();
            }
        });

        // Configuración del modo oscuro basado en preferencias guardadas
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        setDarkMode(isDarkMode);

        // Configuración de botones en la barra superior
        MaterialButton favoriteButton = binding.topAppBar.findViewById(R.id.favoriteButton);
        favoriteButton.setOnClickListener(v -> {
            // Navega a la actividad de favoritos
            Intent intent = new Intent(DashboardActivity.this, FavouritesActivity.class);
            startActivity(intent);
        });

        MaterialButton darkModeButton = binding.topAppBar.findViewById(R.id.darkModeButton);
        darkModeButton.setOnClickListener(v -> {
            // Alterna el modo oscuro y guarda la preferencia
            isDarkMode = !isDarkMode;
            setDarkMode(isDarkMode);
        });

        MaterialButton logoutButton = binding.topAppBar.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            // Cierra sesión y vuelve a la pantalla de login
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual
            dashboardViewModel.logoutUser(); // Cierra la sesión del usuario
        });
    }

    /**
     * Método para establecer el modo oscuro y guardar la preferencia.
     * @param isDarkMode Indica si el modo oscuro debe estar activado o desactivado.
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
