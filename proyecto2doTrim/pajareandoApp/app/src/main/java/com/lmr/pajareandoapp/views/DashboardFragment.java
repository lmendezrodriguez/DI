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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.adapters.BirdAdapter;
import com.lmr.pajareandoapp.databinding.FragmentDashboardBinding;
import com.lmr.pajareandoapp.viewmodels.DashboardViewModel;

import java.util.ArrayList;

/**
 * Fragmento que representa el panel principal (dashboard) donde se listan las aves disponibles.
 * Funcionalidades:
 * - Muestra una lista de aves obtenidas del ViewModel.
 * - Permite la navegación a los detalles de cada ave al hacer clic en una.
 * - Implementa el patrón de diseño MVVM mediante LiveData y DataBinding.
 */
public class DashboardFragment extends Fragment {
    // Adaptador para manejar la lista de aves en el RecyclerView
    private BirdAdapter birdAdapter;
    // ViewBinding para interactuar con los elementos de la UI
    private FragmentDashboardBinding binding;

    /**
     * Constructor vacío requerido para los fragmentos.
     */
    public DashboardFragment() { }

    /**
     * Infla el diseño del fragmento utilizando DataBinding.
     *
     * @param inflater  Objeto LayoutInflater para inflar la vista.
     * @param container Contenedor padre del fragmento.
     * @param savedInstanceState Estado guardado previamente (si lo hay).
     * @return La vista raíz del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inicializa el binding con el layout correspondiente
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        return binding.getRoot(); // Retorna la vista inflada
    }

    /**
     * Configura la vista después de que haya sido creada.
     * Se inicializan el ViewModel, RecyclerView y el Observador de datos.
     *
     * @param view               Vista creada por onCreateView.
     * @param savedInstanceState Estado guardado previamente (si lo hay).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtiene una instancia del ViewModel para manejar los datos de las aves
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Inicializa el adaptador con una lista vacía y define la acción al hacer clic en un ave
        birdAdapter = new BirdAdapter(new ArrayList<>(), birdId -> {
            // Crea una nueva instancia del fragmento de detalles del ave seleccionada
            BirdDetailFragment detailFragment = new BirdDetailFragment();
            // Pasa el ID del ave seleccionada al fragmento de detalles
            Bundle bundle = new Bundle();
            bundle.putString("BIRD_ID", birdId);
            detailFragment.setArguments(bundle);

            // Realiza la transición al fragmento de detalles
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, detailFragment)
                    .addToBackStack(null) // Permite regresar al dashboard
                    .commit();
        });

        // Configura el RecyclerView con un diseño en lista vertical
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(birdAdapter);

        // Observa los cambios en los datos de aves y actualiza la UI en consecuencia
        dashboardViewModel.getBirdsLiveData().observe(getViewLifecycleOwner(), birds -> {
            if (birds != null) {
                // Actualiza la lista de aves en el adaptador
                birdAdapter.setBirds(birds);
            } else {
                // Muestra un mensaje si no se encuentran aves
                Toast.makeText(requireContext(), "No se encontraron aves", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
