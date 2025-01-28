package com.lmr.pajareandoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.viewmodels.RegisterViewModel;


/**
 * Actividad de registro de usuario en la aplicación.
 * Permite registrar un nuevo usuario en Firebase Authentication y almacenar sus datos en Firebase Realtime Database.
 */
public class RegisterActivity extends AppCompatActivity {
    RegisterViewModel registerViewModel;

    /**
     * Método que se llama cuando la actividad es creada.
     * Inicializa la instancia de FirebaseAuth y configura el botón de registro.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Observa el resultado de la autenticación
        registerViewModel.getAuthResult().observe(this, authResult -> {
            if (authResult != null) {
                if (authResult.isSuccess()) {
                    Toast.makeText(RegisterActivity.this, authResult.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, authResult.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // Configura el evento de clic en el botón de registro.
        findViewById(R.id.button_register).setOnClickListener(v -> registerUser());

    }

    /**
     * Registra al usuario con los datos proporcionados (nombre, email, contraseña, teléfono y dirección).
     * Valida que los campos no estén vacíos y que las contraseñas coincidan.
     * Si todo es válido, se crea un nuevo usuario en Firebase Authentication y sus datos se guardan en Firebase Realtime Database.
     */
    private void registerUser() {
        // Obtiene los datos ingresados por el usuario.
        String name = ((EditText) findViewById(R.id.edit_text_name)).getText().toString();
        String email = ((EditText) findViewById(R.id.edit_text_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.edit_text_pass)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.edit_text_confirm_pass)).getText().toString();
        String telephone = ((EditText) findViewById(R.id.edit_text_telephone)).getText().toString();
        String address = ((EditText) findViewById(R.id.edit_text_address)).getText().toString();

        // Verifica que los campos no estén vacíos.
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || telephone.isEmpty() || address.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica que las contraseñas coincidan.
        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }
        registerViewModel.registerUser(email, password, name, telephone, address);
    }
}
