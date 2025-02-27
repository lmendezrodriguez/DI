package com.lmr.pajareandoapp.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.viewmodels.LoginViewModel;

/**
 * Actividad de inicio de sesión en la aplicación.
 * Permite a los usuarios autenticarse usando su correo electrónico y contraseña en Firebase Authentication.
 */
public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel; // ViewModel para manejar la autenticación

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargar la preferencia del modo oscuro antes de establecer la vista
        SharedPreferences prefs = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("darkMode", false);

        // Aplicar el modo oscuro o claro según la preferencia del usuario
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Establecer el diseño de la actividad
        setContentView(R.layout.activity_login);

        // Inicializar el ViewModel utilizando ViewModelProvider
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        /**
         * Observar el LiveData `authResult` del ViewModel.
         * Cuando cambia el estado de autenticación, se actualiza la UI automáticamente.
         */
        loginViewModel.getAuthResult().observe(this, authResult -> {
            if (authResult != null) {
                if (authResult.isSuccess()) {
                    // Autenticación exitosa: mostrar mensaje y navegar a la pantalla principal
                    Toast.makeText(LoginActivity.this, authResult.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish(); // Cierra la pantalla de login para evitar volver atrás con el botón de retroceso
                } else {
                    // Autenticación fallida: mostrar mensaje de error
                    Toast.makeText(LoginActivity.this, authResult.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // Configurar eventos de clic para los botones
        findViewById(R.id.button_login).setOnClickListener(v -> loginUser());
        findViewById(R.id.button_register).setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );
    }

    /**
     * Intenta autenticar al usuario con el correo electrónico y la contraseña proporcionados.
     * Si los campos están vacíos, muestra un mensaje de error.
     */
    private void loginUser() {
        // Obtener los datos ingresados por el usuario
        String email = ((EditText) findViewById(R.id.edit_text_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.edit_text_pass)).getText().toString();

        // Validar que ambos campos estén completos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llamar al ViewModel para iniciar sesión con los datos ingresados
        loginViewModel.loginUser(email, password);
    }
}
