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
 * Fragmento que representa el catálogo.
 * Contiene un botón que permite navegar a la actividad de detalle (DetailActivity).
 */
public class CatalogFragment extends Fragment {

    /**
     * Método para inflar y configurar el layout del fragmento.
     *
     * @param inflater  Objeto utilizado para inflar vistas en el fragmento.
     * @param container Contenedor padre al que se añadirá la vista del fragmento (puede ser nulo).
     * @param savedInstanceState Estado guardado previamente (si existe).
     * @return La vista inflada del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout para este fragmento desde XML
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        // Referencia al botón definido en el layout
        Button button = view.findViewById(R.id.button_detail_fragment);
        // Configurar la acción del botón
        button.setOnClickListener(v -> {
            // Crear un Intent para abrir la actividad de detalle
            Intent intent = new Intent(requireContext(), DetailActivity.class);
            startActivity(intent);

            // Finalizar la actividad principal (MainActivity)
            requireActivity().finish();
        });

        // Retornar la vista configurada del fragmento
        return view;
    }
}
