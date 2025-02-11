package com.lmr.pajareandoapp.views;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.adapters.BirdAdapter;
import com.lmr.pajareandoapp.databinding.FragmentDashboardBinding;
import com.lmr.pajareandoapp.viewmodels.DashboardViewModel;

import java.util.ArrayList;

/**
 * Fragmento del dashboard donde se muestran las aves disponibles.
 * Permite navegar a los detalles de cada ave y gestionar preferencias de usuario.
 */
public class DashboardFragment extends Fragment {
    private BirdAdapter birdAdapter;
    private boolean isDarkMode;
    private FragmentDashboardBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

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

        dashboardViewModel.getBirdsLiveData().observe(getViewLifecycleOwner(), birds -> {
            if (birds != null) {
                birdAdapter.setBirds(birds);
            } else {
                Toast.makeText(requireContext(), "No se encontraron aves", Toast.LENGTH_SHORT).show();
            }
        });

        binding.favoriteButton.setOnClickListener(v -> {
            BirdDetailFragment detailFragment = new BirdDetailFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });

        binding.darkModeButton.setOnClickListener(v -> {
            isDarkMode = !isDarkMode;
            setDarkMode(isDarkMode);
        });

        binding.logoutButton.setOnClickListener(v -> {
            dashboardViewModel.logoutUser();
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
