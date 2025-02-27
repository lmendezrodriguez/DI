package com.lmr.pajareandoapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lmr.pajareandoapp.models.Bird;
import com.lmr.pajareandoapp.repositories.BirdRepository;
import com.lmr.pajareandoapp.repositories.UserRepository;

import java.util.List;

/**
 * ViewModel para el dashboard de la aplicación.
 * Maneja la lógica de la UI relacionada con la lista de aves.
 * Se encarga de obtener y mantener actualizada la lista de aves en tiempo real
 * mediante el repositorio correspondiente.
 */
public class DashboardViewModel extends ViewModel {
    // LiveData que almacena la lista de aves y notifica cambios a la UI
    private final MutableLiveData<List<Bird>> birdsLiveData = new MutableLiveData<>();

    private final BirdRepository birdRepository;  // Repositorio para acceder a los datos de aves
    private final UserRepository userRepository;  // Repositorio para gestionar datos del usuario

    /**
     * Constructor: inicializa los repositorios y carga la lista de aves al iniciar el ViewModel.
     */
    public DashboardViewModel() {
        birdRepository = new BirdRepository();
        userRepository = new UserRepository();
        loadBirds();  // Carga inicial de aves
    }

    /**
     * Proporciona un LiveData para que la UI observe la lista de aves.
     * Cada vez que esta lista cambie, la UI se actualizará automáticamente.
     */
    public MutableLiveData<List<Bird>> getBirdsLiveData() {
        return birdsLiveData;
    }

    /**
     * Obtiene la lista de aves desde el repositorio y actualiza birdsLiveData.
     * La UI se actualizará automáticamente al observar este LiveData.
     */
    private void loadBirds() {
        birdRepository.getBirds(birdsLiveData);
    }

    /**
     * Cierra la sesión del usuario a través del repositorio de usuarios.
     * No utiliza LiveData, ya que el resultado no impacta directamente en la UI del dashboard.
     */
    public void logoutUser() {
        this.userRepository.logoutUser();
    }
}
