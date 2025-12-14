package com.eduardoomarson.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eduardoomarson.calculadoraimc.data.CalculationsDatabase
import com.eduardoomarson.calculadoraimc.data.HistoryDatabaseProvider
import com.eduardoomarson.calculadoraimc.data.HistoryRepositoryImpl
import com.eduardoomarson.calculadoraimc.navigation.CalculationsNavHost
import com.eduardoomarson.calculadoraimc.ui.theme.CalculadoraIMCUpgradeTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        HistoryDatabaseProvider.provide(applicationContext)
    }

    private val repository by lazy {
        HistoryRepositoryImpl(database.historyDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCUpgradeTheme {
                CalculationsNavHost(repository = repository)
            }
        }
    }
}