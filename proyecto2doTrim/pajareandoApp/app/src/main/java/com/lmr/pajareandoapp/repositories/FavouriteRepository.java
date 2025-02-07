package com.lmr.pajareandoapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lmr.pajareandoapp.models.Bird;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar las aves favoritas de un usuario en Firebase.
 */
public class FavouriteRepository {
    private final DatabaseReference userRef;
    private final DatabaseReference birdReference;
    private final FirebaseAuth firebaseAuth;

    /**
     * Constructor que inicializa las referencias a Firebase.
     */
    public FavouriteRepository() {
        userRef = FirebaseDatabase.getInstance().getReference("users");
        birdReference = FirebaseDatabase.getInstance().getReference("birds");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Obtiene la lista de aves favoritas del usuario actual y actualiza LiveData.
     *
     * @param favoriteBirdsLiveData LiveData que se actualizará con la lista de aves favoritas.
     */
    public void getFavoriteBirds(MutableLiveData<List<Bird>> favoriteBirdsLiveData) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) return;

        DatabaseReference favoritesRef = userRef.child(currentUser.getUid()).child("favorites");

        favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> favoriteBirdIds = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot favSnapshot : snapshot.getChildren()) {
                        favoriteBirdIds.add(favSnapshot.getValue(String.class));
                    }
                }

                if (!favoriteBirdIds.isEmpty()) {
                    List<Bird> favoriteBirds = new ArrayList<>();
                    for (String birdId : favoriteBirdIds) {
                        birdReference.child(birdId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot birdSnapshot) {
                                if (birdSnapshot.exists()) {
                                    Bird bird = birdSnapshot.getValue(Bird.class);
                                    if (bird != null) {
                                        bird.setBirdId(birdSnapshot.getKey());
                                        favoriteBirds.add(bird);
                                    }
                                }
                                favoriteBirdsLiveData.postValue(new ArrayList<>(favoriteBirds));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                System.out.println("Error al obtener detalles del ave: " + error.getMessage());
                            }
                        });
                    }
                } else {
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
     * Cierra la sesión del usuario actual en Firebase.
     */
    public void logoutUser() {
        firebaseAuth.signOut();
    }
}