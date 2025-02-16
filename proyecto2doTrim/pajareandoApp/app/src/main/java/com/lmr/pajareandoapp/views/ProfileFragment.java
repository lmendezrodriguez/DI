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

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding; // Binding para la vista
    private SharedPreferences prefs;

    public ProfileFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        UserRepository userRepository = new UserRepository();
        MutableLiveData<User> userLiveData = new MutableLiveData<>();

        userRepository.getCurrentUser(userLiveData);

        userLiveData.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.setUser(user);
            } else {
                Toast.makeText(getContext(), "No se pudo recuperar la información del usuario", Toast.LENGTH_SHORT).show();
            }
        });

        // Obtener preferencias para modo oscuro
        prefs = requireActivity().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("darkMode", false);
        binding.switchDarkMode.setChecked(isDarkMode);

        // Listener para el cambio de contraseña
        binding.btnChangePassword.setOnClickListener(v -> changePassword());

        // Listener para el cambio de modo oscuro
        binding.switchDarkMode.setOnCheckedChangeListener((compoundButton, checked) -> toggleDarkMode(checked));

        return view;
    }

    private void changePassword() {
        String currentPass = Objects.requireNonNull(binding.editTextActualPass.getText()).toString();
        String newPass = Objects.requireNonNull(binding.editTextNewPass.getText()).toString();
        String confirmPass = Objects.requireNonNull(binding.editTextConfirmPass.getText()).toString();

        // Reautenticación del usuario y actualización de la contraseña
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

    private void toggleDarkMode(boolean enableDarkMode) {
        // Guardamos la preferencia
        prefs.edit().putBoolean("darkMode", enableDarkMode).apply();

        // Aplicamos el tema
        if (enableDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Recreamos la actividad para que se aplique el cambio
        requireActivity().recreate();
    }
}
