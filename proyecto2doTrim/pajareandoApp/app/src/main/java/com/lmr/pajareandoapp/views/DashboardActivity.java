package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.adapters.BirdAdapter;
import com.lmr.pajareandoapp.databinding.ActivityDashboardBinding;
import com.lmr.pajareandoapp.models.Bird;
import com.lmr.pajareandoapp.viewmodels.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Actividad principal del dashboard donde se muestra la lista de aves.
 */
public class DashboardActivity extends AppCompatActivity {
    private BirdAdapter birdAdapter;
    private MutableLiveData<List<Bird>> birdsLiveData;
    private MutableLiveData<Bird> selectedBird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        ActivityDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        MaterialButton logoutButton = findViewById(R.id.logoutButton);

        // Inicializa el adaptador de aves
        birdAdapter = new BirdAdapter(new ArrayList<>(), bird -> {
            Intent intent = new Intent(this, BirdDetailActivity.class);
            intent.putExtra("Common_name", bird.getCommonName());
            intent.putExtra("Scientific_name", bird.getScientificName());
            intent.putExtra("Description", bird.getDescription());
            intent.putExtra("Url_photo", bird.getUrlPhoto());
            startActivity(intent);
        });

        // Obtiene la lista de aves desde el ViewModel y actualiza el adaptador
        dashboardViewModel.getBirdsLiveData().observe(this, birds -> {
            if (birds != null) {
                birdAdapter.setBirds(birds);
            } else {
                Toast.makeText(this, "No se encontraron aves", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura el RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(birdAdapter);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        dashboardViewModel.getBirdsLiveData().observe(this, birds -> birdAdapter.setBirds(birds));

        // Configura el botón de cierre de sesión
        logoutButton.setOnClickListener(v -> {
            // Redirige a la actividad de Login.
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual.

        });

    }

    /**
     * Obtiene el ave seleccionado.
     * Necesario para el funcionamiento del adaptador.
     *
     * @return LiveData que notifica el ave seleccionado.
     */
    public LiveData<Bird> getSelectedBird() {
        return selectedBird;
    }

    /**
     * Selecciona un ave.
     * Necesario para el funcionamineto del adaptador.
     *
     * @param bird El ave seleccionado.
     */
    public void selectBird(Bird bird) {
        selectedBird.setValue(bird);
    }
}
