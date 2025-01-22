package com.lmr.pajareandoapp.utils;

/**
 * Representa el resultado de una operación de autenticación en la aplicación.
 */
public class AuthentificationResult {
    private final boolean success;
    private final String message;

    /**
     * Crea una nueva instancia de AuthResult con el resultado de la operación de autenticación.
     * @param success Indica si la operación de autenticación fue exitosa.
     * @param message Mensaje de error en caso de que la operación falle.
     **/
    public AuthentificationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
