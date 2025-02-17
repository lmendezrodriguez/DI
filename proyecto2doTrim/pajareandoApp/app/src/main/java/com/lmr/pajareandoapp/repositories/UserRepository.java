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
     */
    public UserRepository() {
        userRef = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Obtiene la lista de usuarios desde la base de datos.
     * Se utiliza un MutableLiveData para notificar a los observadores cuando los datos cambian.
     *
     * @param userLiveData MutableLiveData para notificar al observador con la lista de usuarios.
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
     *
     * @param email     El correo electrónico del usuario.
     * @param password  Contraseña del usuario.
     * @param name      Nombre del usuario.
     * @param telephone Número de teléfono del usuario.
     * @param address   Dirección del usuario.
     * @param result    El resultado de la operación de registro que notifica a los observadores.
     */
    public void registerUser(String email, String password, String name, String telephone, String address, MutableLiveData<AuthenticationResult> result) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userUid = firebaseUser.getUid();
                            // Crear el objeto User
                            User user = new User(userUid, name, email, telephone, address, new ArrayList<>(), new ArrayList<>());
                            // Guardar los datos del usuario en la base de datos
                            saveUserToDatabase(user, result);
                        }
                    } else {
                        result.postValue(new AuthenticationResult(false, "Error: " + getSpanishErrorMessage(task.getException())));
                    }
                });
    }

    /**
     * Guarda los datos del usuario en la base de datos.
     *
     * @param user   El objeto User que contiene los datos del usuario.
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
     *
     * @param email    El correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param result   El resultado de la operación de inicio de sesión que notifica a los observadores.
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

    /**
     * Obtiene la información del usuario actual.
     *
     * @param birdId id del ave
     * @param isFavoriteLiveData MutableLiveData para notificar al observador con el ave recuperada.
     */
    public void toggleFavorite(String birdId, MutableLiveData<Boolean> isFavoriteLiveData) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) return;

        // Obtener la referencia del usuario actual
        DatabaseReference favoritesRef = userRef.child(currentUser.getUid()).child("favorites");

        // Obtener la lista de favoritos del usuario
        favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> favorites = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot favSnapshot : snapshot.getChildren()) {
                        favorites.add(favSnapshot.getValue(String.class));
                    }
                }
                // Verificar si el ave ya está en la lista de favoritos
                boolean isCurrentlyFavorite = favorites.contains(birdId);
                if (isCurrentlyFavorite) {
                    favorites.remove(birdId);
                } else {
                    favorites.add(birdId);
                }
                // Actualizar la lista de favoritos en la base de datos
                favoritesRef.setValue(favorites).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        isFavoriteLiveData.postValue(!isCurrentlyFavorite);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error al obtener favoritos: " + error.getMessage());
            }
        });
    }

    /**
     * Comprueba si un ave está en la lista de favoritos del usuario actual.
     *
     * @param birdId id del ave
     * @param isFavoriteLiveData MutableLiveData para notificar al observador con el ave recuperada.
     */
    public void checkIfFavorite(String birdId, MutableLiveData<Boolean> isFavoriteLiveData) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) return;

        DatabaseReference favoritesRef = userRef.child(currentUser.getUid()).child("favorites");

        // Añade un listener para obtener la lista de favoritos
        favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> favorites = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot favSnapshot : snapshot.getChildren()) {
                        favorites.add(favSnapshot.getValue(String.class));
                    }
                }
                isFavoriteLiveData.postValue(favorites.contains(birdId));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error al obtener favoritos: " + error.getMessage());
            }
        });
    }
    /**
     * Recupera la información del usuario autenticado desde la base de datos.
     *
     * @param userLiveData MutableLiveData para notificar al observador con el usuario recuperado.
     */
    public void getCurrentUser(MutableLiveData<User> userLiveData) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            userLiveData.postValue(null);
            return;
        }

        String userUid = currentUser.getUid();
        userRef.child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    userLiveData.postValue(user);
                } else {
                    userLiveData.postValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error al obtener la información del usuario: " + error.getMessage());
                userLiveData.postValue(null);
            }
        });
    }

}