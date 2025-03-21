package com.example.autorespondercompose

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Log

fun requestPermissions(activity: Activity) {
    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS
            ),
            1 // Código de solicitud de permisos
        )
    } else {
        Log.d("Permissions", "Permisos ya otorgados")
    }
}


fun saveData(context: Context, phoneNumber: String, message: String) {
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("phoneNumber", phoneNumber)
        putString("message", message)
        apply() // 'apply' asegura que los cambios se guarden de inmediato
    }
    // Log para verificar que los datos se guardaron correctamente
    Log.d("ActivityFunctions", "Datos guardados: Número: $phoneNumber, Mensaje: $message")
}

fun loadSavedData(context: Context): Pair<String, String> {
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val phoneNumber = sharedPreferences.getString("phoneNumber", "") ?: ""
    val message = sharedPreferences.getString("message", "") ?: ""

    // Log para verificar qué datos se están recuperando
    Log.d("ActivityFunctions", "Datos cargados: Número: $phoneNumber, Mensaje: $message")

    return Pair(phoneNumber, message)

}
