package com.lmr.pajareandoapp.utils;

import android.accounts.NetworkErrorException;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.concurrent.TimeoutException;

/**
 * Manejador de excepciones de autenticación en Firebase.
 * Traduce las excepciones de autenticación de Firebase a mensajes en español.
 */
public class SpanishExceptionHandler {
    public static String getSpanishErrorMessage(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            FirebaseAuthInvalidCredentialsException e = (FirebaseAuthInvalidCredentialsException) exception;

            // Verificar el error basado en el código específico de FirebaseAuthInvalidCredentialsException
            if (e.getErrorCode().equals("ERROR_INVALID_EMAIL")) {
                return "El correo electrónico proporcionado no es válido.";
            } else if (e.getErrorCode().equals("ERROR_WRONG_PASSWORD")) {
                return "La contraseña es incorrecta.";
            } else if (e.getErrorCode().equals("ERROR_USER_NOT_FOUND")) {
                return "El usuario no existe con ese correo electrónico.";
            } else {
                return "Usuario o contraseña incorrectos.";
            }
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            return "El usuario ya existe.";
        } else if (exception instanceof NetworkErrorException) {
            return "Error de conexión a la red.";
        } else if (exception instanceof TimeoutException) {
            return "Tiempo de espera agotado.";
        } else {
            return "Error desconocido.";
        }
    }
}
