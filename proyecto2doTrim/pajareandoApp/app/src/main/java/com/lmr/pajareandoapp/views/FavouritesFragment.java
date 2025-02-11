package com.lmr.pajareandoapp.views;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.adapters.BirdAdapter;
import com.lmr.pajareandoapp.databinding.ActivityFavouritesBinding;
import com.lmr.pajareandoapp.models.Bird;
import com.lmr.pajareandoapp.viewmodels.FavouritesViewModel;

import java.util.ArrayList;

import javax.annotation.Nullable;


public class FavouritesFragment extends Fragment {
    private BirdAdapter birdAdapter; // Adaptador para la lista de aves favoritas
    private boolean isDarkMode; // Estado del modo oscuro
    private ActivityFavouritesBinding binding; // Binding para la vista

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FavouritesViewModel viewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        birdAdapter = new BirdAdapter(new ArrayList<>(), birdId -> {
            BirdDetailFragment detailFragment = new BirdDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("BIRD_ID", birdId);
            detailFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(birdAdapter);

        viewModel.getFavouriteBirdsLiveData().observe(getViewLifecycleOwner(), birds -> {
            if (birds != null) {
                birdAdapter.setBirds(birds);
            } else {
                Toast.makeText(requireContext(), "No se encontraron aves", Toast.LENGTH_SHORT).show();
            }
        });

        binding.darkModeButton.setOnClickListener(v -> {
            isDarkMode = !isDarkMode;
            setDarkMode(isDarkMode);
        });

        binding.logoutButton.setOnClickListener(v -> {
            viewModel.logoutUser();
            requireActivity().finish();
        });


    }

    private void setDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            binding.darkModeButton.setIconResource(R.drawable.baseline_light_mode_24);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            binding.darkModeButton.setIconResource(R.drawable.baseline_dark_mode_24);
        }
    }
}