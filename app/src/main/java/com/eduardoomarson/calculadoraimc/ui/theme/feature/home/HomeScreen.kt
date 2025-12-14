@file:OptIn(ExperimentalMaterial3Api::class)

package com.eduardoomarson.calculadoraimc.ui.theme.feature.home

import HomeEvent
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduardoomarson.calculadoraimc.UiEvent
import com.eduardoomarson.calculadoraimc.ui.theme.BlackPrimary
import com.eduardoomarson.calculadoraimc.ui.theme.WhitePrimary
import com.eduardoomarson.calculadoraimc.ui.theme.components.HomePrimaryCard
import com.eduardoomarson.calculadoraimc.ui.theme.components.HomeSecondaryCard

@Composable
fun HomeScreen(
    onNavigateToIMC: (Long?) -> Unit,
    onNavigateToHistory: (Long?) -> Unit,
    onNavigateToCalculations: () -> Unit
)
{
    val viewModel = viewModel<HomeViewModel> {
        HomeViewModel()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate<*> -> {
                    when (val route = event.route) {
                        is com.eduardoomarson.calculadoraimc.navigation.IMCRoute -> {
                            onNavigateToIMC(route.id)
                        }
                        is com.eduardoomarson.calculadoraimc.navigation.HistoryRoute -> {
                            onNavigateToHistory(route.id)
                        }
                        is com.eduardoomarson.calculadoraimc.navigation.CalculationsHubRoute -> {
                            onNavigateToCalculations()
                        }
                    }
                }
                else -> Unit
            }
        }
    }

    HomeContent(
        onEvent = viewModel::onEvent
    )
    // Fim sugestão Claude
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeContent(
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Calculadora de Saúde",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlackPrimary,
                    titleContentColor = WhitePrimary
                )
            )
        },
        containerColor = WhitePrimary
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {

            Text(
                text = "O que você deseja fazer?",
                style = MaterialTheme.typography.titleMedium,
                color = BlackPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            /* ---------- CARD (DESTAQUE) ---------- */
            HomePrimaryCard(
                title = "Calcular",
                subtitle = "Calcular métricas de saúde",
                icon = Icons.Default.MonitorWeight,
                onClick = { onEvent(HomeEvent.OpenCalculations) }
            )


            Spacer(modifier = Modifier.height(24.dp))

            /* ---------- HISTÓRICO (SECUNDÁRIO) ---------- */
            HomeSecondaryCard(
                title = "Histórico",
                subtitle = "Ver cálculos anteriores",
                icon = Icons.Default.History,
                onClick = { onEvent(HomeEvent.HistoryNav(id = null)) }
            )
        }
    }
}



@Preview
@Composable
fun HomeScreenPreview() {
    HomeContent(
        onEvent = {},
    )

}