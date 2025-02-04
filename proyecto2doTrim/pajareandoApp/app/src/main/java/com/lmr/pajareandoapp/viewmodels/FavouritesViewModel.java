package com.lmr.pajareandoapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lmr.pajareandoapp.models.Bird;
import com.lmr.pajareandoapp.repositories.FavouriteRepository;

import java.util.List;

/**
 * ViewModel para gestionar las aves favoritas del usuario.
 * Se encarga de obtener la lista de aves marcadas como favoritas
 * y la mantiene actualizada en tiempo real.
 */
public class FavouritesViewModel extends ViewModel {
    private final MutableLiveData<List<Bird>> favouriteBirdsLiveData = new MutableLiveData<>();
    private final FavouriteRepository favouriteRepository;

    public FavouritesViewModel() {
        favouriteRepository = new FavouriteRepository();
        loadFavouriteBirds();
    }

    public MutableLiveData<List<Bird>> getFavouriteBirdsLiveData() {
        return favouriteBirdsLiveData;
    }

    private void loadFavouriteBirds() {
        favouriteRepository.getFavoriteBirds(favouriteBirdsLiveData);
    }
    public void logoutUser() { this.favouriteRepository.logoutUser(); }
}
