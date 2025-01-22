package com.lmr.pajareandoapp.utils;

import android.accounts.NetworkErrorException;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.concurrent.TimeoutException;

public class SpanishExceptionHandler {
    public static String getSpanishErrorMessage(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            return "Usuario o contraseña incorrectos.";
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            return "El usuario ya existe.";
        } else if (exception instanceof FirebaseAuthWeakPasswordException) {
            return "La contraseña es débil.";
        } else if (exception instanceof NetworkErrorException) {
            return "Error de conexión a la red.";
        } else if (exception instanceof TimeoutException) {
            return "Tiempo de espera agotado.";
        } else {
            return "Error desconocido.";
        }
    }
}
