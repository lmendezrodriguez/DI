package com.lmr.pajareandoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Actividad de registro de usuario en la aplicación.
 * Permite registrar un nuevo usuario en Firebase Authentication y almacenar sus datos en Firebase Realtime Database.
 */
public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

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

        // Crea un nuevo usuario en Firebase Authentication.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show();
                        // Agrega los datos del usuario a Firebase Realtime Database.
                        addUserDataToFireBase(mAuth.getCurrentUser(), name, email, telephone, address);
                        // Inicia la actividad Dashboard después de un registro exitoso.
                        startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                    } else {
                        // Muestra el mensaje de error si el registro falla.
                        Toast.makeText(RegisterActivity.this, SpanishExceptionHandler.getSpanishErrorMessage(task.getException()), Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * Agrega los datos del usuario registrado a Firebase Realtime Database.
     *
     * @param authUser El usuario autenticado en Firebase.
     * @param name Nombre del usuario.
     * @param email Correo electrónico del usuario.
     * @param telephone Número de teléfono del usuario.
     * @param address Dirección del usuario.
     */
    private void addUserDataToFireBase(FirebaseUser authUser, String name, String email, String telephone, String address) {
        // Referencia a la base de datos de Firebase.
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");

        if (authUser != null) {
            String uid = authUser.getUid();
            // Crea un nuevo objeto User con los datos del usuario.
            User newUser = new User(uid, name, email, telephone, address);
            System.out.println(newUser.toString());

            // Almacena los datos del usuario en la base de datos de Firebase.
            databaseRef.child(uid).setValue(newUser)
                    .addOnCompleteListener(dbTask -> {
                        if (dbTask.isSuccessful()) {
                            System.out.println(newUser.toString());
                            Log.d("Firebase", "Usuario añadido a Realtime Database con UID: " + uid);
                        } else {
                            Log.e("Firebase", "Error al añadir usuario a la base de datos", dbTask.getException());
                        }
                    });
        }
    }
}
