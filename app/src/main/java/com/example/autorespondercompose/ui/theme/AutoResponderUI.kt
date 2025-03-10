package com.example.autorespondercompose.ui.theme

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.autorespondercompose.loadSavedData
import com.example.autorespondercompose.saveData

@Composable
fun AutoResponderUI() {
    var phoneNumber by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Cargar los datos guardados mediante la función definida en ActivityFunctions.kt
    LaunchedEffect(Unit) {
        val (savedNumber, savedMessage) = loadSavedData(context)
        phoneNumber = savedNumber
        message = savedMessage
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Número de teléfono") },
           )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Mensaje") },
         )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                saveData(context, phoneNumber, message)
                Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()
                Log.d("AutoResponderUI", "Número guardado: $phoneNumber")
                Log.d("AutoResponderUI", "Mensaje guardado: $message")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AutoResponderComposeTheme {
        AutoResponderUI()
    }
}
