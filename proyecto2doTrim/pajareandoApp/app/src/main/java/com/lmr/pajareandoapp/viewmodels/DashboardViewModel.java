package com.lmr.pajareandoapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lmr.pajareandoapp.models.Bird;
import com.lmr.pajareandoapp.repositories.BirdRepository;

import java.util.List;

/**
 * ViewModel para el dashboard de la aplicación.
 * Emplea el repositorio de aves para obtener la lista de aves
 * y la mantiente actualizada en tiempo real.
 */
public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<List<Bird>> birdsLiveData = new MutableLiveData<>();
    private final BirdRepository birdRepository;

    public DashboardViewModel() {
        birdRepository = new BirdRepository();
        loadBirds();
    }

    public MutableLiveData<List<Bird>> getBirdsLiveData() {
        return birdsLiveData;
    }

    private void loadBirds() {
        birdRepository.getBirds(birdsLiveData);
    }
}
