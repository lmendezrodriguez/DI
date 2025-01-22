package com.lmr.pajareandoapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lmr.pajareandoapp.repositories.UserRepository;
import com.lmr.pajareandoapp.utils.AuthenticationResult;

/**
 * ViewModel para la pantalla de registro de usuario en la aplicación.
 */
public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<AuthenticationResult> authResult = new MutableLiveData<>();

    /**
     * Crea una instancia del ViewModel de registro de usuario.
     */
    public RegisterViewModel() {
        userRepository = new UserRepository();
    }

    /**
     * Obtiene el resultado de la autenticación.
     * @return LiveData que notifica el resultado de la autenticación.
     */
    public LiveData<AuthenticationResult> getAuthResult() {
        return authResult;
    }

    /**
     * Registra un nuevo usuario en la aplicación. Incluye los detalles del usuario en la base de datos.
     * y notifica el resultado de la operación.
     * @param email
     * @param password
     * @param name
     * @param telephone
     * @param address
     */
    public void registerUser(String email, String password, String name, String telephone, String address) {
        userRepository.registerUser(email, password, name, telephone, address, authResult);
    }
}