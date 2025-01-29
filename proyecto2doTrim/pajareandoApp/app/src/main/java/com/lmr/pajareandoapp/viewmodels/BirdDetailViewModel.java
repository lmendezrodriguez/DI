package com.lmr.pajareandoapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lmr.pajareandoapp.models.Bird;
import com.lmr.pajareandoapp.repositories.BirdRepository;
import com.lmr.pajareandoapp.repositories.UserRepository;

/**
 * ViewModel para la pantalla de detalles de un ave.
 */

public class BirdDetailViewModel extends ViewModel {
    private final BirdRepository birdRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<Bird> birdLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();

    public BirdDetailViewModel() {
        this.birdRepository = new BirdRepository();
        this.userRepository = new UserRepository();
    }

    public LiveData<Bird> getBirdLiveData() {
        return birdLiveData;
    }

    public LiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    public void loadBirdById(String birdId) {
        birdRepository.getBirdById(birdId, birdLiveData);
    }

    public void checkIfFavorite(String birdId) {
        userRepository.checkIfFavorite(birdId, isFavorite);
    }

    public void toggleFavorite(String birdId) {
        userRepository.toggleFavorite(birdId, isFavorite);
    }
}
