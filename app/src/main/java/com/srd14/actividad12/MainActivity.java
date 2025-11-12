package com.srd14.actividad12;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView statusText;
    private BiometricHelper biometricHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar elementos de la interfaz de usuario
        statusText = findViewById(R.id.statusText);
        Button authenticateButton = findViewById(R.id.authenticateButton);

        biometricHelper = new BiometricHelper(this);

        // Configurar el botón de autenticación
        authenticateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (biometricHelper.canAuthenticate()) {
                    // Crear un BiometricPrompt para autenticación biométrica
                    BiometricPrompt biometricPrompt = biometricHelper.createBiometricPrompt(
                            new BiometricPrompt.AuthenticationCallback() {
                                // Si la autenticación es exitosa, muestra un mensaje


                                @Override
                                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                                    super.onAuthenticationSucceeded(result);
                                    statusText.setText("Autenticación exitosa");
                                }

                                // Si la autenticación falla, muestra un mensaje
                                @Override
                                public void onAuthenticationFailed() {
                                    super.onAuthenticationFailed();
                                    statusText.setText("Autenticación fallida");
                                }

                                // Si ocurre un error, muestra un mensaje
                                @Override
                                public void onAuthenticationError(int errorCode, CharSequence errString) {
                                    super.onAuthenticationError(errorCode, errString);
                                    statusText.setText("Error: " + errString);
                                }
                            });

                    // Mostrar el diálogo de autenticación
                    biometricPrompt.authenticate(biometricHelper.createPromptInfo());

                // Si el dispositivo no soporta autenticación biométrica, muestra un mensaje
                } else {
                    statusText.setText("La autenticación biométrica no está disponible");
                }
            }

        });

    }
}