package com.lmr.pajareandoapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lmr.pajareandoapp.repositories.UserRepository;
import com.lmr.pajareandoapp.utils.AuthenticationResult;

/**
 * ViewModel para la pantalla de registro de usuario en la aplicación.
 * Maneja la lógica de autenticación y creación de nuevos usuarios,
 * notificando los resultados a la UI.
 */
public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository; // Repositorio para gestionar la autenticación y usuarios

    // LiveData que almacena el resultado del registro y notifica cambios a la UI
    private final MutableLiveData<AuthenticationResult> authResult = new MutableLiveData<>();

    /**
     * Crea una instancia del ViewModel de registro de usuario.
     * Inicializa el repositorio de usuarios.
     */
    public RegisterViewModel() {
        userRepository = new UserRepository();
    }

    /**
     * Proporciona un LiveData para observar el resultado de la autenticación.
     * La UI se actualizará automáticamente cuando el resultado cambie.
     *
     * @return LiveData que notifica el resultado del registro.
     */
    public LiveData<AuthenticationResult> getAuthResult() {
        return authResult;
    }

    /**
     * Registra un nuevo usuario en la aplicación.
     * Envía los datos al repositorio y actualiza `authResult` con el resultado.
     * La UI observará este LiveData y reaccionará ante el éxito o fallo del registro.
     *
     * @param email     El correo electrónico del usuario.
     * @param password  Contraseña del usuario.
     * @param name      Nombre del usuario.
     * @param telephone Número de teléfono del usuario.
     * @param address   Dirección del usuario.
     */
    public void registerUser(String email, String password, String name, String telephone, String address) {
        userRepository.registerUser(email, password, name, telephone, address, authResult);
    }
}
