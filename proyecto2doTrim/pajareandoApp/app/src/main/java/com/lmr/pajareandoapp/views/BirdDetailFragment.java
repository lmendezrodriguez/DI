package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.databinding.DetailBirdBinding;
import com.lmr.pajareandoapp.viewmodels.BirdDetailViewModel;

/**
 * Fragmento que muestra los detalles de un ave específico.
 */
public class BirdDetailFragment extends Fragment {
    private BirdDetailViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private boolean isDarkMode;
    private String birdId;
    private DetailBirdBinding binding;

    public BirdDetailFragment() {
        // Constructor vacío requerido
    }

    /**
     *
     * @param inflater El objeto LayoutInflater que se puede usar para inflar
     * cualquier vista (View) en el fragmento.
     * @param container Si no es nulo, esta es la vista (View) padre a la que la
     * interfaz de usuario (UI) del fragmento debería adjuntarse. El fragmento no
     * debe añadir la vista (View) por sí mismo, pero este parámetro se puede usar
     * para generar los LayoutParams de la vista (View).
     * @param savedInstanceState Si no es nulo, este fragmento está siendo reconstruido
     * a partir de un estado guardado previamente, tal como se proporciona aquí.
     *
     * @return La vista (View) raíz del fragmento.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_bird, container, false);
        return binding.getRoot();
    }

    /**
     *
     * @param view La Vista (View) retornada por {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState Si no es nulo, este fragmento está siendo reconstruido
     * a partir de un estado guardado previamente, tal como se proporciona aquí.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configura el ViewModel
        viewModel = new ViewModelProvider(this).get(BirdDetailViewModel.class);

        // Configura las preferencias de usuario para el modo oscuro
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", requireActivity().MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        setDarkMode(isDarkMode);

        // Configura botones
        binding.backButton.setOnClickListener(v -> requireActivity().onBackPressed());
        binding.logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
        binding.darkModeButton.setOnClickListener(v -> {
            isDarkMode = !isDarkMode;
            setDarkMode(isDarkMode);
        });

        // Obtiene el ID del ave desde los argumentos del Fragment
        if (getArguments() != null) {
            birdId = getArguments().getString("BIRD_ID");
        }

        if (birdId != null) {
            viewModel.loadBirdById(birdId);
            viewModel.checkIfFavorite(birdId);
        } else {
            Toast.makeText(requireContext(), "Error: ID del ave no encontrado.", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
            return;
        }

        // Observa los datos del ave
        viewModel.getBirdLiveData().observe(getViewLifecycleOwner(), bird -> {
            if (bird != null) {
                binding.setBird(bird);
            } else {
                Toast.makeText(requireContext(), "Error al obtener los datos del ave.", Toast.LENGTH_SHORT).show();
            }
        });

        // Observa si el ave es favorita
        viewModel.getIsFavorite().observe(getViewLifecycleOwner(), isFavorite -> {
            binding.fab.setImageResource(isFavorite ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);
        });

        // Configura el clic del botón de favoritos
        binding.fab.setOnClickListener(v -> {
            viewModel.toggleFavorite(birdId);
            Toast.makeText(requireContext(), "Cambiando estado de favorito...", Toast.LENGTH_SHORT).show();
        });

        binding.darkModeButton.setOnClickListener(v -> {
            isDarkMode = !isDarkMode;
            setDarkMode(isDarkMode);
        });
    }

    /**
     * Configura el modo oscuro de la aplicación.
     * @param isDarkMode Indica si el modo oscuro está activado o no.
     */
    private void setDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            binding.darkModeButton.setIconResource(R.drawable.baseline_light_mode_24);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            binding.darkModeButton.setIconResource(R.drawable.baseline_dark_mode_24);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDarkMode", isDarkMode);
        editor.apply();
    }
}
