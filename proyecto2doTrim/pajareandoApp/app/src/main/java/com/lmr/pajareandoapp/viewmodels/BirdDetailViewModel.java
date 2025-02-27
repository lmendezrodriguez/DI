package com.lmr.pajareandoapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lmr.pajareandoapp.models.Bird;
import com.lmr.pajareandoapp.repositories.BirdRepository;
import com.lmr.pajareandoapp.repositories.UserRepository;

/**
 * ViewModel para la pantalla de detalles de un ave.
 * Se encarga de manejar la lógica de UI y la obtención de datos,
 * siguiendo el patrón MVVM.
 */
public class BirdDetailViewModel extends ViewModel {
    private final BirdRepository birdRepository;  // Repositorio para acceder a los datos de las aves
    private final UserRepository userRepository;  // Repositorio para gestionar datos del usuario

    // LiveData que mantiene la información del ave seleccionada
    private final MutableLiveData<Bird> birdLiveData = new MutableLiveData<>();

    // LiveData para indicar si el ave es favorita del usuario
    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();

    // Constructor que inicializa los repositorios
    public BirdDetailViewModel() {
        this.birdRepository = new BirdRepository();
        this.userRepository = new UserRepository();
    }

    /**
     * Proporciona un LiveData para observar los datos del ave.
     * La UI (Activity/Fragment) se suscribirá a este LiveData para actualizarse automáticamente
     * cuando los datos cambien.
     */
    public LiveData<Bird> getBirdLiveData() {
        return birdLiveData;
    }

    /**
     * Proporciona un LiveData para observar si el ave es favorita.
     * Esto permite que la UI refleje el estado favorito en tiempo real.
     */
    public LiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    /**
     * Carga los datos de un ave específica desde el repositorio y los asigna a birdLiveData.
     * La UI se actualizará automáticamente al observar este LiveData.
     */
    public void loadBirdById(String birdId) {
        birdRepository.getBirdById(birdId, birdLiveData);
    }

    /**
     * Carga datos de un ave aleatoria desde el repositorio y los asigna a birdLiveData.
     */
    public void loadRandomBird() {
        birdRepository.getRandomBird(birdLiveData);
    }

    /**
     * Verifica si el ave es favorita consultando el repositorio de usuarios
     * y actualiza el LiveData correspondiente.
     */
    public void checkIfFavorite(String birdId) {
        userRepository.checkIfFavorite(birdId, isFavorite);
    }

    /**
     * Alterna el estado de favorito del ave en el repositorio de usuarios.
     * La UI se actualizará automáticamente al observar isFavorite.
     */
    public void toggleFavorite(String birdId) {
        userRepository.toggleFavorite(birdId, isFavorite);
    }
}
