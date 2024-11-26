package com.example.mycatalog;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Fragmento del catálogo.
 */
public class CatalogFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        // Configurar el botón
        Button button = view.findViewById(R.id.button_detail_fragment);
        button.setOnClickListener(v -> {
            // Iniciar la DetailActivity
            Intent intent = new Intent(requireContext(), DetailActivity.class);
            startActivity(intent);

            // Finalizar la MainActivity
            requireActivity().finish();
        });

        return view;
    }
}
