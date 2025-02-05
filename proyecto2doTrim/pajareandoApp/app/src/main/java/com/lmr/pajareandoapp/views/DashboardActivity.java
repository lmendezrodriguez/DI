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

public class DashboardActivity extends AppCompatActivity {
    private BirdAdapter birdAdapter;
    private boolean isDarkMode;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configura el ViewModel y el binding
        ActivityDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Configura el adaptador con el listener asociado al ID de la ave
        birdAdapter = new BirdAdapter(new ArrayList<>(), birdId -> {
            Intent intent = new Intent(this, BirdDetailActivity.class);
            intent.putExtra("BIRD_ID", birdId);
            startActivity(intent);
        });

        // Configura el RecyclerView
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

        // Configuración para Dark Mode
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        setDarkMode(isDarkMode);


        MaterialButton favoriteButton = binding.topAppBar.findViewById(R.id.favoriteButton);
        // Aquí puedes añadir la lógica que desees para el botón de favoritos, por ejemplo:
        favoriteButton.setOnClickListener(v -> {
            // Lógica de agregar a favoritos
            Intent intent = new Intent(DashboardActivity.this, FavouritesActivity.class);
            startActivity(intent);
        });

        MaterialButton darkModeButton = binding.topAppBar.findViewById(R.id.darkModeButton);
        darkModeButton.setOnClickListener(v -> {
            isDarkMode = !isDarkMode;
            setDarkMode(isDarkMode);
        });

        MaterialButton logoutButton = binding.topAppBar.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual.
            dashboardViewModel.logoutUser(); // Cierra la sesión del usuario.
        });
    }

    private void setDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Guarda la preferencia del modo
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDarkMode", isDarkMode);
        editor.apply();
    }


}
