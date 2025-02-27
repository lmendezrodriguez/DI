package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.databinding.ActivityMainBinding;

/**
 * MainActivity es la actividad principal de la aplicación.
 * Gestiona la interfaz de usuario principal y la navegación entre fragmentos y el cierre de sesión.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;  // Instancia de DataBinding para acceder a la vista

    /**
     * Método llamado cuando la actividad es creada.
     * Aquí se configura el DataBinding y se manejan los eventos de la navegación lateral.
     *
     * @param savedInstanceState Si la actividad se recrea, contiene el estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configura el DataBinding con el layout 'activity_main'
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Maneja los eventos del menú lateral
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();  // Obtiene el ID del item seleccionado
            if (id == R.id.nav_dashboard) {
                openFragment(new DashboardFragment());  // Abre el DashboardFragment
            } else if (id == R.id.nav_favourites) {
                openFragment(new FavouritesFragment());  // Abre el FavouritesFragment
            }
            else if (id == R.id.nav_random) {
                openFragment(new RandomFragment());  // Abre el FavouritesFragment
            } else if (id == R.id.nav_profile) {
                openFragment(new ProfileFragment());  // Abre el ProfileFragment

            } else if (id == R.id.nav_logout) {
                logoutUser();  // Cierra sesión del usuario
            }
            binding.drawerLayout.closeDrawers();  // Cierra el menú lateral
            return true;  // Indica que el evento ha sido manejado
        });

        // Si la actividad se está creando por primera vez, carga el fragmento de Dashboard
        if (savedInstanceState == null) {
            openFragment(new DashboardFragment());
        }
    }

    /**
     * Método para abrir un fragmento en el contenedor correspondiente.
     *
     * @param fragment El fragmento a mostrar.
     */
    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()  // Inicia la transacción del fragmento
                .replace(R.id.fragmentContainer, fragment)  // Reemplaza el fragmento en el contenedor
                .commit();  // Finaliza la transacción
    }

    /**
     * Método para cerrar la sesión del usuario.
     * Después de cerrar sesión, redirige al usuario a la pantalla de login.
     */
    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();  // Cierra sesión en Firebase
        // Redirige al LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);  // Inicia la actividad de login
        finish();  // Finaliza la actividad actual para evitar que el usuario regrese con el botón atrás
    }
}
