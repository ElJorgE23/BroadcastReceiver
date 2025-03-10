package com.example.autorespondercompose

import android.content.IntentFilter
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.autorespondercompose.ui.theme.AutoResponderComposeTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.example.autorespondercompose.ui.theme.AutoResponderUI

class MyActivity : ComponentActivity() {
    private lateinit var callReceiver: IncomingCallReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Solicitar permisos usando funciones definidas en ActivityFunctions.kt
        requestPermissions(this)

        // Registrar el BroadcastReceiver para escuchar cambios en el estado del teléfono
        callReceiver = IncomingCallReceiver()
        val filter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(callReceiver, filter)
        Log.d("MyActivity", "BroadcastReceiver registrado")

        // Configurar la interfaz de usuario con Jetpack Compose
        setContent {
            AutoResponderComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    AutoResponderUI()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(callReceiver)
        Log.d("MyActivity", "BroadcastReceiver desregistrado")
    }
}
