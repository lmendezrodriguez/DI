package com.lmr.pajareandoapp.repositories;

import static com.lmr.pajareandoapp.utils.SpanishExceptionHandler.getSpanishErrorMessage;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lmr.pajareandoapp.models.User;
import com.lmr.pajareandoapp.utils.AuthenticationResult;


import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio de usuarios en la aplicación Pajareando.
 */
public class UserRepository {

    private final DatabaseReference userRef;
    private final FirebaseAuth firebaseAuth;

    /**
     * Crea una instancia del repositorio de usuarios.
     * @param userRef Referencia a la base de datos de usuarios en Firebase Realtime Database.
     * @param firebaseAuth Instancia de FirebaseAuth para autenticación en Firebase.
     */
    public UserRepository() {
        userRef = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Obtiene la lista de usuarios desde la base de datos.
     * Se utiliza un MutableLiveData para notificar a los observadores cuando los datos cambian.
     * @param userLiveData
     */
    public void getUsers(MutableLiveData<List<User>> userLiveData) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    users.add(user);
                }
                userLiveData.postValue(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                userLiveData.postValue(null);
                System.out.println("Error al obtener los usuarios: " + error.getMessage());
            }
        });
    }

    /**
     * Registra un nuevo usuario en la aplicación.
     * @param email
     * @param password
     * @param name
     * @param telephone
     * @param address
     * @param result El resultado de la operación de registro que notifica a los observadores.
     */
    public void registerUser(String email, String password, String name, String telephone, String address, MutableLiveData<AuthenticationResult> result) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userUid = firebaseUser.getUid();
                            // Crear el objeto User
                            User user = new User(userUid,name, email,telephone, address, new ArrayList<>());;
                            // Guardar los datos del usuario en la base de datos
                            saveUserToDatabase(user, result);
                        }
                    } else {
                        result.postValue(new AuthenticationResult(false,"Error: " + getSpanishErrorMessage(task.getException())));
                    }
                });
    }

    /**
     * Guarda los datos del usuario en la base de datos.
     * @param user
     * @param result El resultado de la operación de registro que notifica a los observadores.
     */
    private void saveUserToDatabase(User user, MutableLiveData<AuthenticationResult> result) {
        userRef.child(user.getUid()).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        result.postValue(new AuthenticationResult(true, "Registro existoso"));
                    } else {
                        result.postValue(new AuthenticationResult(false, "Error: " + getSpanishErrorMessage(task.getException())));
                    }
                });
    }

    /**
     * Inicia sesión de un usuario.
     * @param email
     * @param password
     * @param result El resultado de la operación de inicio de sesión que notifica a los observadores.
     */
    public void loginUser(String email, String password, MutableLiveData<AuthenticationResult> result) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        result.postValue(new AuthenticationResult(true, "Inicio de sesión exitoso"));
                    } else {
                        result.postValue(new AuthenticationResult(false, "Error: " + getSpanishErrorMessage(task.getException())));
                    }
                });
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void logoutUser() {
        firebaseAuth.signOut();
    }
}
