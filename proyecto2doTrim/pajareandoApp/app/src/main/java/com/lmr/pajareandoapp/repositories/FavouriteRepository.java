package com.lmr.pajareandoapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.lmr.pajareandoapp.models.Bird;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio que gestiona las aves favoritas de usuarios.
 * Estructura en Firebase:
 * - /users/{userId}/favorites/: Lista de IDs de aves favoritas
 * - /birds/{birdId}/: Información completa de cada ave
 */
public class FavouriteRepository {
    // Referencia al nodo "users" para gestionar favoritos
    private final DatabaseReference userRef;
    // Referencia al nodo "birds" para obtener detalles de aves
    private final DatabaseReference birdReference;
    // Instancia de autenticación para obtener usuario actual
    private final FirebaseAuth firebaseAuth;

    /**
     * Constructor que inicializa las referencias necesarias:
     * 1. Referencia a la colección de usuarios
     * 2. Referencia a la colección de aves
     * 3. Instancia de autenticación
     */
    public FavouriteRepository() {
        userRef = FirebaseDatabase.getInstance().getReference("users");
        birdReference = FirebaseDatabase.getInstance().getReference("birds");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Obtiene y observa las aves favoritas del usuario actual.
     *
     * Flujo de datos:
     * 1. Obtiene ID de usuario actual
     * 2. Observa sus favoritos en /users/{userId}/favorites
     * 3. Para cada ID favorito, obtiene detalles del ave en /birds/{birdId}
     * 4. Combina los resultados y actualiza el LiveData
     *
     * @param favoriteBirdsLiveData Contenedor observable para la lista de favoritos
     */
    public void getFavoriteBirds(MutableLiveData<List<Bird>> favoriteBirdsLiveData) {
        // Verificar que hay un usuario autenticado
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) return;

        // Referencia a la lista de favoritos del usuario actual
        DatabaseReference favoritesRef = userRef.child(currentUser.getUid()).child("favorites");

        // Observar cambios en la lista de favoritos
        favoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Lista temporal para almacenar IDs de aves favoritas
                List<String> favoriteBirdIds = new ArrayList<>();

                // Obtener IDs de favoritos si existen
                if (snapshot.exists()) {
                    for (DataSnapshot favSnapshot : snapshot.getChildren()) {
                        favoriteBirdIds.add(favSnapshot.getValue(String.class));
                    }
                }

                if (!favoriteBirdIds.isEmpty()) {
                    // Lista para almacenar los objetos Bird completos
                    List<Bird> favoriteBirds = new ArrayList<>();

                    // Para cada ID, obtener los detalles del ave
                    for (String birdId : favoriteBirdIds) {
                        birdReference.child(birdId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot birdSnapshot) {
                                if (birdSnapshot.exists()) {
                                    Bird bird = birdSnapshot.getValue(Bird.class);
                                    if (bird != null) {
                                        // Guardar ID en el objeto Bird
                                        bird.setBirdId(birdSnapshot.getKey());
                                        favoriteBirds.add(bird);
                                    }
                                }
                                // Actualizar LiveData con una nueva copia de la lista
                                favoriteBirdsLiveData.postValue(new ArrayList<>(favoriteBirds));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                System.out.println("Error al obtener detalles del ave: " + error.getMessage());
                            }
                        });
                    }
                } else {
                    // Si no hay favoritos, actualizar con lista vacía
                    favoriteBirdsLiveData.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error al obtener favoritos: " + error.getMessage());
            }
        });
    }

    /**
     * Cierra la sesión del usuario actual.
     * Esto detendrá automáticamente los listeners de Firebase.
     */
    public void logoutUser() {
        firebaseAuth.signOut();
    }
}