package com.example.mycatalog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Actividad principal de la aplicación, que gestiona la navegación entre fragmentos utilizando
 * una barra de navegación inferior.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llamada al método onCreate de la superclase para inicializar la actividad
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura la navegación con la barra inferior
        setupNavegacion();
    }

    /**
     * Configura la navegación utilizando un NavHostFragment y un BottomNavigationView.
     */
    private void setupNavegacion() {
        // Referencia a la barra de navegación inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Obtiene el fragmento de navegación (NavHostFragment)
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_hostfragment);

        // Vincula el controlador de navegación con la barra de navegación inferior
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
    }
}
