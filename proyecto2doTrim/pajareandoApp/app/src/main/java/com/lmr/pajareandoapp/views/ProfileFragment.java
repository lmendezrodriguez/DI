package com.lmr.pajareandoapp.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lmr.pajareandoapp.databinding.FragmentProfileBinding;
import com.lmr.pajareandoapp.models.User;
import com.lmr.pajareandoapp.repositories.UserRepository;

import java.util.Objects;

/**
 * ProfileFragment es un fragmento que muestra y permite editar el perfil del usuario,
 * cambiar la contraseña y gestionar el modo oscuro de la aplicación.
 */
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding; // Binding para la vista
    private SharedPreferences prefs;  // Preferencias compartidas para guardar configuraciones

    public ProfileFragment() { }

    /**
     * Método llamado para crear la vista del fragmento.
     * Inicializa el binding, obtiene la información del usuario, configura preferencias y maneja eventos.
     *
     * @param inflater Inflador de vista para crear el layout del fragmento.
     * @param container Contenedor del fragmento.
     * @param savedInstanceState Estado guardado en caso de recreación del fragmento.
     * @return La vista del fragmento.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Obtiene los datos del usuario desde el repositorio y los observa
        UserRepository userRepository = new UserRepository();
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        userRepository.getCurrentUser(userLiveData);

        // Observa los cambios del usuario y actualiza la vista
        userLiveData.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.setUser(user);
            } else {
                Toast.makeText(getContext(), "No se pudo recuperar la información del usuario", Toast.LENGTH_SHORT).show();
            }
        });

        // Obtener las preferencias de la app, en particular el modo oscuro
        prefs = requireActivity().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("darkMode", false);
        binding.switchDarkMode.setChecked(isDarkMode);

        // Configura el listener para cambiar la contraseña
        binding.btnChangePassword.setOnClickListener(v -> changePassword());

        // Configura el listener para cambiar el modo oscuro
        binding.switchDarkMode.setOnCheckedChangeListener((compoundButton, checked) -> toggleDarkMode(checked));

        return view;
    }

    /**
     * Método que permite cambiar la contraseña del usuario.
     * Requiere la contraseña actual y las nuevas contraseñas ingresadas por el usuario.
     */
    private void changePassword() {
        String currentPass = Objects.requireNonNull(binding.editTextActualPass.getText()).toString();
        String newPass = Objects.requireNonNull(binding.editTextNewPass.getText()).toString();
        String confirmPass = Objects.requireNonNull(binding.editTextConfirmPass.getText()).toString();

        // Reautenticar al usuario antes de cambiar la contraseña
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && !newPass.isEmpty() && !confirmPass.isEmpty()) {
            if (!newPass.equals(confirmPass)) {
                Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            AuthCredential credential = EmailAuthProvider
                    .getCredential(Objects.requireNonNull(user.getEmail()), currentPass);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(newPass).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(getContext(), "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "La contraseña actual no es correcta", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Cambia entre el modo oscuro y claro de la aplicación.
     * Guarda la preferencia en SharedPreferences y aplica el tema correspondiente.
     *
     * @param enableDarkMode Booleano que indica si se debe activar el modo oscuro.
     */
    private void toggleDarkMode(boolean enableDarkMode) {
        // Guardar la preferencia de modo oscuro
        prefs.edit().putBoolean("darkMode", enableDarkMode).apply();

        // Aplicar el tema según la preferencia
        if (enableDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Recrear la actividad para que el cambio de tema se aplique
        requireActivity().recreate();
    }
}
