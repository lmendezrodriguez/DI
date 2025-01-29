package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
 * Actividad principal del dashboard con RecyclerView para mostrar la lista de aves.
 */
public class DashboardActivity extends AppCompatActivity {
    private BirdAdapter birdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configura el ViewModel y el binding
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        ActivityDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        MaterialButton logoutButton = findViewById(R.id.logoutButton);

        // Configurar el adaptador con el listener asoaciado al ID de la ave
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

        // Configura el botón de cierre de sesión
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            dashboardViewModel.logoutUser(); // Cierra la sesión del usuario.
            finish(); // Finaliza la actividad actual.
        });
    }
}
