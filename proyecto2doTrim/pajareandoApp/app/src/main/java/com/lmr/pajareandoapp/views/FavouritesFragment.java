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
import com.lmr.pajareandoapp.databinding.FragmentFavouritesBinding;
import com.lmr.pajareandoapp.viewmodels.FavouritesViewModel;

import java.util.ArrayList;

/**
 * Fragmento que muestra la lista de aves marcadas como favoritas por el usuario.
 * Permite ver detalles de cada ave y gestionar su estado en la lista de favoritos.
 */
public class FavouritesFragment extends Fragment {
    private BirdAdapter birdAdapter; // Adaptador para la lista de aves favoritas
    private FragmentFavouritesBinding binding; // Binding para acceder a los elementos de la UI

    /**
     * Constructor vacío requerido para la correcta instanciación del fragmento.
     */
    public FavouritesFragment() {}

    /**
     * Infla el diseño del fragmento utilizando DataBinding.
     *
     * @param inflater Objeto LayoutInflater que se usa para inflar la vista del fragmento.
     * @param container Contenedor padre donde se adjuntará la vista (puede ser nulo).
     * @param savedInstanceState Estado guardado previamente (puede ser nulo).
     * @return La vista raíz del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false);
        return binding.getRoot();
    }

    /**
     * Se ejecuta después de que la vista ha sido creada.
     * Configura el ViewModel y el RecyclerView para mostrar la lista de aves favoritas.
     *
     * @param view Vista creada por {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState Estado guardado previamente (puede ser nulo).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializa el ViewModel
        FavouritesViewModel favouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        // Configura el adaptador para la lista de aves favoritas
        birdAdapter = new BirdAdapter(new ArrayList<>(), birdId -> {
            // Al hacer clic en un ave, se abre el detalle de la misma
            BirdDetailFragment detailFragment = new BirdDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("BIRD_ID", birdId);
            detailFragment.setArguments(bundle);

            // Reemplaza el fragmento actual con el detalle del ave seleccionado
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, detailFragment)
                    .addToBackStack(null) // Permite volver atrás
                    .commit();
        });

        // Configura el RecyclerView con un LinearLayoutManager y asigna el adaptador
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(birdAdapter);

        // Observa la lista de aves favoritas y la actualiza en el RecyclerView
        favouritesViewModel.getFavouriteBirdsLiveData().observe(getViewLifecycleOwner(), birds -> {
            if (birds != null) {
                birdAdapter.setBirds(birds);
            } else {
                Toast.makeText(requireContext(), "No se encontraron aves favoritas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
