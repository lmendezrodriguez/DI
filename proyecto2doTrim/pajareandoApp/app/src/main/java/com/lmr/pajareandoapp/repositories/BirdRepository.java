package com.lmr.pajareandoapp.repositories;
//Firebase Database
//        ↓
//        ValueEventListener
//        ↓
//        List<Bird> / Bird
//        ↓
//        MutableLiveData
//        ↓
//        ViewModel
//        ↓
//        UI

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.*;
import com.lmr.pajareandoapp.models.Bird;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Repositorio que implementa el patrón Repository en MVVM.
 * Actúa como única fuente de verdad para los datos de aves.
 * Abstrae el origen de datos (Firebase) del resto de la aplicación.
 */
public class BirdRepository {
    // Referencia al nodo "birds" en Firebase Realtime Database
    // Esta referencia se usará para todas las operaciones de lectura
    private final DatabaseReference birdReference;

    /**
     * Constructor que inicializa la conexión con Firebase.
     * getInstance() obtiene la instancia de Firebase configurada en la app
     * getReference("birds") apunta al nodo "birds" en la base de datos
     */
    public BirdRepository() {
        this.birdReference = FirebaseDatabase.getInstance().getReference("birds");
    }

    /**
     * OPERACIÓN DE LECTURA CONTINUA
     * Implementa un observable permanente sobre la colección de aves.
     * <p>
     * Flujo de datos:
     * 1. Firebase → 2. ValueEventListener → 3. List<Bird> → 4. MutableLiveData → 5. ViewModel
     *
     * @param birdLiveData Contenedor observable que notificará los cambios al ViewModel
     */
    public void getBirds(MutableLiveData<List<Bird>> birdLiveData) {
        // addValueEventListener se mantiene escuchando cambios constantemente
        birdReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Crear lista temporal para almacenar resultados
                List<Bird> birds = new ArrayList<>();

                // Iterar sobre cada documento en la colección "birds"
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Convertir el documento a objeto Bird
                    Bird bird = dataSnapshot.getValue(Bird.class);
                    assert bird != null;
                    // Guardar el ID del documento en el objeto
                    bird.setBirdId(dataSnapshot.getKey());
                    birds.add(bird);
                }

                // Actualizar LiveData desde el hilo principal
                birdLiveData.setValue(birds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Notificar error de forma thread-safe
                birdLiveData.postValue(null);
                System.out.println("Error al obtener los aves: " + error.getMessage());
            }
        });
    }

    /**
     * OPERACIÓN DE LECTURA ÚNICA
     * Recupera un ave específica una sola vez.
     * <p>
     * Flujo de datos:
     * 1. Firebase(bird/{id}) → 2. SingleValueEvent → 3. Bird → 4. MutableLiveData → 5. ViewModel
     *
     * @param birdId       ID del ave a recuperar
     * @param birdLiveData Contenedor observable para un ave individual
     */
    public void getBirdById(String birdId, MutableLiveData<Bird> birdLiveData) {
        // child(birdId) navega al documento específico
        // addListenerForSingleValueEvent realiza una única lectura
        birdReference.child(birdId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Documento encontrado: convertir y notificar
                    Bird bird = snapshot.getValue(Bird.class);
                    birdLiveData.postValue(bird);
                } else {
                    // Documento no encontrado: notificar null
                    birdLiveData.postValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Error en la consulta: notificar null
                birdLiveData.postValue(null);
                System.out.println("Error al obtener el ave: " + error.getMessage());
            }
        });
    }

    /**
     * Implementa un observable permanente sobre un ave recogida aleatoriamente de la base de datos
     *
     * @param birdLiveData Contenedor observable que notificará los cambios al ViewModel
     */
    public void getRandomBird(MutableLiveData<Bird> birdLiveData) {
        // addValueEventListener se mantiene escuchando cambios constantemente
        birdReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Crear lista temporal para almacenar resultados
                List<Bird> birds = new ArrayList<>();

                // Iterar sobre cada documento en la colección "birds"
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Convertir el documento a objeto Bird
                    Bird bird = dataSnapshot.getValue(Bird.class);
                    assert bird != null;
                    // Guardar el ID del documento en el objeto
                    bird.setBirdId(dataSnapshot.getKey());
                    birds.add(bird);
                }

                // Actualizar LiveData desde el hilo principal
                Random random = new Random();
                int randomIndex = random.nextInt(birds.size());
                Bird randomBird = birds.get(randomIndex);
                birdLiveData.setValue(randomBird);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Notificar error de forma thread-safe
                birdLiveData.postValue(null);
                System.out.println("Error al obtener ave aleatorio: " + error.getMessage());
            }
        });
    }
}