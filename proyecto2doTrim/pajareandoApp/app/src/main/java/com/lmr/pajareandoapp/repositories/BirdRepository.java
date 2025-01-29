package com.lmr.pajareandoapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lmr.pajareandoapp.models.Bird;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio de aves en la aplicación Pajareando.
 */
public class BirdRepository {
    private final DatabaseReference birdReference;

    public BirdRepository() {
        this.birdReference = FirebaseDatabase.getInstance().getReference("birds");
    }

    /**
     * Obtiene la lista de aves desde la base de datos.
     * Se utiliza un MutableLiveData para notificar a los observadores cuando los datos cambian.
     *
     * @param birdLiveData MutableLiveData para notificar al observador con la lista de aves.
     */
    public void getBirds(MutableLiveData<List<Bird>> birdLiveData) {
        birdReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Bird> birds = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Bird bird = dataSnapshot.getValue(Bird.class);
                    assert bird != null;
                    bird.setBirdId(dataSnapshot.getKey());
                    birds.add(bird);
                }
                birdLiveData.setValue(birds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                birdLiveData.postValue(null);
                System.out.println("Error al obtener los aves: " + error.getMessage());
            }
        });
    }

    /**
     * Obtiene un ave específica por su birdId desde la base de datos.
     *
     * @param birdId       ID del ave que se desea recuperar.
     * @param birdLiveData MutableLiveData para notificar al observador con el ave recuperada.
     */
    public void getBirdById(String birdId, MutableLiveData<Bird> birdLiveData) {
        birdReference.child(birdId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Bird bird = snapshot.getValue(Bird.class);
                    birdLiveData.postValue(bird);
                } else {

                    birdLiveData.postValue(null); // Si no se encuentra el ave
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                birdLiveData.postValue(null);
                System.out.println("Error al obtener el ave: " + error.getMessage());
            }
        });
    }
}
