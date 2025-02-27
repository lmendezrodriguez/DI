package com.lmr.pajareandoapp.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.databinding.FragmentRandomBinding;
import com.lmr.pajareandoapp.viewmodels.BirdDetailViewModel;

/**
 * Fragmento que muestra los detalles de un ave específico.
 * Se encarga de cargar la información del ave seleccionada y gestionar su estado de favorito.
 */
public class RandomFragment extends Fragment {
    private BirdDetailViewModel viewModel; // ViewModel para gestionar los datos del ave
    private String birdId; // ID del ave seleccionada
    private FragmentRandomBinding binding; // Enlace de datos con la vista (View Binding)

    /**
     * Constructor vacío requerido para la correcta instanciación del fragmento.
     */
    public RandomFragment() {}

    /**
     * Infla el diseño del fragmento usando DataBinding.
     *
     * @param inflater Objeto LayoutInflater para inflar la vista del fragmento.
     * @param container Contenedor padre donde se adjuntará la vista (puede ser nulo).
     * @param savedInstanceState Estado guardado previamente (puede ser nulo).
     * @return La vista raíz del fragmento.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_random, container, false);
        return binding.getRoot();
    }

    /**
     * Se ejecuta después de que la vista ha sido creada.
     * Configura el ViewModel y carga los datos del ave.
     *
     * @param view Vista creada por {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState Estado guardado previamente (puede ser nulo).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurar el botón de retroceso en la barra de herramientas
        binding.topAppBar.setNavigationOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed(); // Regresar al fragmento anterior
        });

        // Inicializa el ViewModel
        viewModel = new ViewModelProvider(this).get(BirdDetailViewModel.class);

        // Carga un ave aleatoria desde el repositorio
        viewModel.loadRandomBird();

        // Observa los datos del ave y los asigna a la vista si existen
        viewModel.getBirdLiveData().observe(getViewLifecycleOwner(), bird -> {
            if (bird != null) {
                birdId = bird.getBirdId();
                viewModel.checkIfFavorite(birdId);
                binding.setBird(bird); // Asigna los datos del ave a la vista a través de Data Binding
            } else {
                Toast.makeText(requireContext(), "Error al obtener los datos del ave.", Toast.LENGTH_SHORT).show();
            }
        });

        // Observa el estado de favorito del ave y cambia el icono del botón flotante (FAB)
        viewModel.getIsFavorite().observe(getViewLifecycleOwner(), isFavorite -> {
            binding.fab.setImageResource(isFavorite ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);
        });

        // Configura la acción del botón flotante para alternar el estado de favorito
        binding.fab.setOnClickListener(v -> {
            viewModel.toggleFavorite(birdId);
            Toast.makeText(requireContext(), "Cambiando estado de favorito...", Toast.LENGTH_SHORT).show();
        });
        binding.buttonRandom.setOnClickListener(v -> {
            requireActivity().recreate();
        });
    }
}
