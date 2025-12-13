package com.eduardoomarson.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eduardoomarson.calculadoraimc.ui.theme.feature.imc.IMCScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IMCScreen(
                id = TODO(),
                navigateToHistoryScreen = TODO(),
                navigateBack = TODO()
            )
        }
    }
}
