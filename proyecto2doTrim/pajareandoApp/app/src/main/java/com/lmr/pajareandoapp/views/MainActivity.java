package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;  // DataBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Manejamos los eventos del menú lateral
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                openFragment(new DashboardFragment());
            } else if (id == R.id.nav_favourites) {
                openFragment(new FavouritesFragment());
            } else if (id == R.id.nav_logout) {
                logoutUser();
            }
            binding.drawerLayout.closeDrawers();
            return true;
        });

        // Cargar por defecto el DashboardFragment
        if (savedInstanceState == null) {
            openFragment(new DashboardFragment());
        }
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        // Redireccionar a LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Para que no pueda volver con el botón atrás
    }
}