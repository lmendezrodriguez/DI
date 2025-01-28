package com.lmr.pajareandoapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lmr.pajareandoapp.repositories.UserRepository;
import com.lmr.pajareandoapp.utils.AuthenticationResult;


/**
 * ViewModel para la pantalla de inicio de sesión en la aplicación.
 */
public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<AuthenticationResult> authResult = new MutableLiveData<>();

    /**
     * Crea una instancia del ViewModel de inicio de sesión.
     */
    public LoginViewModel() {
        this.userRepository = new UserRepository();
    }

    /**
     * Obtiene el resultado de la autenticación.
     *
     * @return LiveData que notifica el resultado de la autenticación.
     */
    public LiveData<AuthenticationResult> getAuthResult() {
        return authResult;
    }

    /**
     * Inicia sesión con las credenciales proporcionadas.
     *
     * @param email    El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    public void loginUser(String email, String password) {
        userRepository.loginUser(email, password, authResult);
    }

}
