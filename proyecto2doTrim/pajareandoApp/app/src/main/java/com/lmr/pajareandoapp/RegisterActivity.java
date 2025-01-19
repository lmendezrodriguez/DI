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

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.button_register).setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = ((EditText) findViewById(R.id.edit_text_name)).getText().toString();
        String email = ((EditText) findViewById(R.id.edit_text_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.edit_text_pass)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.edit_text_confirm_pass)).getText().toString();
        String telephone = ((EditText) findViewById(R.id.edit_text_telephone)).getText().toString();
        String address = ((EditText) findViewById(R.id.edit_text_address)).getText().toString();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || telephone.isEmpty() || address.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show();
                        addUserDataToFireBase(mAuth.getCurrentUser(), name, email, telephone, address);
                        //finish();
                        startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, SpanishExceptionHandler.getSpanishErrorMessage(task.getException()), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void addUserDataToFireBase(FirebaseUser authUser, String name, String email, String telephone, String address) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        if (authUser != null) {
            String uid = authUser.getUid();
            User newUser = new User(uid, name, email, telephone, address);
            System.out.println(newUser.toString());
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