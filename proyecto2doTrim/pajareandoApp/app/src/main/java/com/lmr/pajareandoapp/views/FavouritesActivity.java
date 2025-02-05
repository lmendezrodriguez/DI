package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.adapters.BirdAdapter;
import com.lmr.pajareandoapp.databinding.ActivityFavouritesBinding;
import com.lmr.pajareandoapp.viewmodels.FavouritesViewModel;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    private BirdAdapter birdAdapter;
    private boolean isDarkMode;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configurar la actividad Edge-to-Edge para aprovechar toda la pantalla
        EdgeToEdge.enable(this);
        ActivityFavouritesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_favourites);

        // Configura el ViewModel y el binding
        FavouritesViewModel favouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        // Configurar el adaptador con el listener asociado al ID de la ave
        birdAdapter = new BirdAdapter(new ArrayList<>(), birdId -> {
            // Acción al seleccionar una ave
            Intent intent = new Intent(this, BirdDetailActivity.class);
            intent.putExtra("BIRD_ID", birdId);
            startActivity(intent);
        });

        // Configura el RecyclerView
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

        // Configuración para Dark Mode
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        setDarkMode(isDarkMode);

        // Configura el botón de "Volver" (MaterialButton)
        binding.backButton.setOnClickListener(v -> onSupportNavigateUp());

        // Configura el botón de cierre de sesión (Logout) (MaterialButton)
        binding.logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(FavouritesActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Configura el botón de Dark Mode (MaterialButton)
        binding.darkModeButton.setOnClickListener(v -> {
            isDarkMode = !isDarkMode;
            setDarkMode(isDarkMode);
        });
    }

    // Método para manejar la navegación hacia atrás
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

        // Guarda la preferencia del modo
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDarkMode", isDarkMode);
        editor.apply();
    }
}
