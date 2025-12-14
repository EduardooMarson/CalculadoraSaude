package com.eduardoomarson.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eduardoomarson.calculadoraimc.navigation.CalculationsNavHost
import com.eduardoomarson.calculadoraimc.ui.theme.CalculadoraIMCUpgradeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCUpgradeTheme {
                CalculationsNavHost()
            }
        }
    }
}