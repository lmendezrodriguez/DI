package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

        // Configura el botón de cambio de tema (Modo Oscuro)
        ImageButton darkModeButton = binding.darkModeButton;
        darkModeButton.setOnClickListener(v -> {
            // Aquí va tu lógica para cambiar el tema (si deseas implementarla)
        });

        // Configura el botón de "Volver"
        ImageButton backButton = binding.backButton;
        backButton.setOnClickListener(v -> onBackPressed());

        // Configura el botón de cierre de sesión (Logout)
        ImageButton logoutButton = binding.logoutButton;
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(FavouritesActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
