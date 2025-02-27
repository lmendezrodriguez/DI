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
    // LiveData que almacena la lista de aves favoritas y notifica cambios a la UI
    private final MutableLiveData<List<Bird>> favouriteBirdsLiveData = new MutableLiveData<>();

    private final FavouriteRepository favouriteRepository; // Repositorio para gestionar favoritos

    /**
     * Constructor: inicializa el repositorio y carga la lista de aves favoritas al iniciar el ViewModel.
     */
    public FavouritesViewModel() {
        favouriteRepository = new FavouriteRepository();
        loadFavouriteBirds();  // Carga inicial de aves favoritas
    }

    /**
     * Proporciona un LiveData para que la UI observe la lista de aves favoritas.
     * Cada vez que esta lista cambie, la UI se actualizará automáticamente.
     */
    public MutableLiveData<List<Bird>> getFavouriteBirdsLiveData() {
        return favouriteBirdsLiveData;
    }

    /**
     * Obtiene la lista de aves favoritas desde el repositorio y actualiza favouriteBirdsLiveData.
     * La UI se actualizará automáticamente al observar este LiveData.
     */
    private void loadFavouriteBirds() {
        favouriteRepository.getFavoriteBirds(favouriteBirdsLiveData);
    }

    /**
     * Cierra la sesión del usuario a través del repositorio de favoritos.
     * No utiliza LiveData, ya que el resultado no impacta directamente en la UI de favoritos.
     */
    public void logoutUser() {
        this.favouriteRepository.logoutUser();
    }
}
