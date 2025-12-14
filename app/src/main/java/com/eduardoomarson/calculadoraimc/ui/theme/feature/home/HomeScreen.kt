@file:OptIn(ExperimentalMaterial3Api::class)

package com.eduardoomarson.calculadoraimc.ui.theme.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduardoomarson.calculadoraimc.UiEvent
import com.eduardoomarson.calculadoraimc.ui.theme.Blue
import com.eduardoomarson.calculadoraimc.ui.theme.LilasTMB
import com.eduardoomarson.calculadoraimc.ui.theme.PurpleCalorias
import com.eduardoomarson.calculadoraimc.ui.theme.PurpleHistorico
import com.eduardoomarson.calculadoraimc.ui.theme.PurplePeso
import com.eduardoomarson.calculadoraimc.ui.theme.White

@Composable
fun HomeScreen(
    onNavigateToIMC: (Long?) -> Unit,
    onNavigateToHistory: (Long?) -> Unit,
) {
    val viewModel = viewModel<HomeViewModel> {
        HomeViewModel()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate<*> -> {
                    // Sugestão Claude
                    when (val route = event.route) {
                        is com.eduardoomarson.calculadoraimc.navigation.IMCRoute -> {
                            onNavigateToIMC(route.id)
                        }

                        is com.eduardoomarson.calculadoraimc.navigation.HistoryRoute -> {
                            onNavigateToHistory(route.id)
                        }
                        // Adicione outros casos conforme necessário
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
    onEvent: (HomeEvent) -> Unit,
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
                    containerColor = Blue,
                    titleContentColor = White
                )
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Escolha uma calculadora:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            CalculatorCard(
                title = "Calcular IMC",
                subtitle = "Índice de Massa Corporal",
                color = Blue,
                onClick = { onEvent(HomeEvent.IMCNav(id = null)) }
            )

            CalculatorCard(
                title = "Calcular TMB",
                subtitle = "Taxa Metabólica Basal",
                color = LilasTMB,
                onClick = { onEvent(HomeEvent.TMBNav(id = null)) }
            )

            CalculatorCard(
                title = "Calcular Peso Ideal",
                subtitle = "Descubra seu peso ideal",
                color = PurplePeso,
                onClick = { onEvent(HomeEvent.PesoIdealNav(id = null)) }
            )

            CalculatorCard(
                title = "Calcular Calorias Diárias",
                subtitle = "Necessidade calórica diária",
                color = PurpleCalorias,
                onClick = { onEvent(HomeEvent.CaloriasNav(id = null)) }
            )

            CalculatorCard(
                title = "Histórico",
                subtitle = "Ver cálculos anteriores",
                color = PurpleHistorico,
                onClick = { onEvent(HomeEvent.HistoryNav(id = null)) }
            )
        }
    }
}

@Composable
fun CalculatorCard(
    title: String,
    subtitle: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(100.dp)
            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = White.copy(alpha = 0.9f)
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