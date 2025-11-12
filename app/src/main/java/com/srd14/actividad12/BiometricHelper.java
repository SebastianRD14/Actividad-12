package com.srd14.actividad12;

import android.content.Context;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class BiometricHelper {

    private final Context context;
    public BiometricHelper(Context context){
        this.context = context;
    }

    // Verficia si el dispositivo soporta autenticación biométrica
    public boolean canAuthenticate(){
        BiometricManager biometricManager = BiometricManager.from(context);
        int result = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);
        return result == BiometricManager.BIOMETRIC_SUCCESS;
    }

    // Crea un BiometricPrompt para autenticación biométrica
    public BiometricPrompt createBiometricPrompt(BiometricPrompt.AuthenticationCallback callback) {
        Executor executor = ContextCompat.getMainExecutor(context);
        return new BiometricPrompt((androidx.fragment.app.FragmentActivity) context, executor, callback);
    }

    // Crea un PromptInfo para mostrar los textos que le saldran al usuario
    public androidx.biometric.BiometricPrompt.PromptInfo createPromptInfo() {
        return new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Verificación biométrica")
                .setSubtitle("Usa tu huella digital para continuar")
                .setDescription("Coloca tu dedo en el sensor")
                .setNegativeButtonText("Cancelar")
                .build();
    }

}
