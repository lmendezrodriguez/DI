package com.example.mycatalog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragmento que muestra información sobre la aplicación.
 */
public class AboutFragment extends Fragment {

    // Constructor vacío requerido para instanciar el fragmento.
    public AboutFragment() {
    }

    /**
     * Método estático para crear una nueva instancia del fragmento.
     *
     * @param param1 Primer parámetro (no utilizado en este ejemplo).
     * @param param2 Segundo parámetro (no utilizado en este ejemplo).
     * @return Nueva instancia de AboutFragment.
     */
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        // Aquí se pueden añadir argumentos al fragmento si es necesario.
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Comprueba si el fragmento tiene argumentos y los gestiona (si aplica).
        if (getArguments() != null) {
            // Código para manejar argumentos.
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el diseño XML asociado al fragmento.
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}