package com.lmr.pajareandoapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lmr.pajareandoapp.repositories.UserRepository;
import com.lmr.pajareandoapp.utils.AuthenticationResult;

/**
 * ViewModel para la pantalla de inicio de sesión en la aplicación.
 * Maneja la lógica de autenticación y notifica cambios a la UI.
 */
public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository; // Repositorio para gestionar la autenticación de usuarios

    // LiveData que almacena el resultado de la autenticación y notifica a la UI cuando cambia
    private final MutableLiveData<AuthenticationResult> authResult = new MutableLiveData<>();

    /**
     * Crea una instancia del ViewModel de inicio de sesión.
     * Inicializa el repositorio de usuarios.
     */
    public LoginViewModel() {
        this.userRepository = new UserRepository();
    }

    /**
     * Proporciona un LiveData para observar el resultado de la autenticación.
     * La UI se actualizará automáticamente cuando el resultado cambie.
     *
     * @return LiveData que notifica el resultado de la autenticación.
     */
    public LiveData<AuthenticationResult> getAuthResult() {
        return authResult;
    }

    /**
     * Inicia sesión con las credenciales proporcionadas.
     * Llama al repositorio para autenticar al usuario y actualiza `authResult`.
     * La UI observará este LiveData para reaccionar ante el éxito o fracaso del inicio de sesión.
     *
     * @param email    El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    public void loginUser(String email, String password) {
        userRepository.loginUser(email, password, authResult);
    }
}
