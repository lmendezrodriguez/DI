package com.lmr.pajareandoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Actividad de inicio de sesión en la aplicación.
 * Permite a los usuarios autenticarse usando su correo electrónico y contraseña en Firebase Authentication.
 */
public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    /**
     * Método que se llama cuando la actividad es creada.
     * Inicializa la instancia de FirebaseAuth y configura los botones de inicio de sesión y registro.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();

        // Configura el evento de clic para el botón de inicio de sesión.
        findViewById(R.id.button_login).setOnClickListener(v -> loginUser());

        // Configura el evento de clic para el botón de registro.
        findViewById(R.id.button_register).setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );
    }

    /**
     * Intenta autenticar al usuario con el correo electrónico y la contraseña proporcionados.
     * Si la autenticación es exitosa, redirige al usuario al Dashboard. Si falla, muestra un mensaje de error.
     */
    private void loginUser() {
        // Obtiene el correo electrónico y la contraseña ingresados por el usuario.
        String email = ((EditText) findViewById(R.id.edit_text_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.edit_text_pass)).getText().toString();

        // Verifica que los campos no estén vacíos.
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Intenta autenticar al usuario con el correo electrónico y la contraseña proporcionados.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Si la autenticación es exitosa, muestra un mensaje y redirige al Dashboard.
                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            } else {
                // Si la autenticación falla, muestra un mensaje de error.
                Toast.makeText(LoginActivity.this, SpanishExceptionHandler.getSpanishErrorMessage(task.getException()), Toast.LENGTH_LONG).show();
            }
        });
    }
}
