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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eduardoomarson.calculadoraimc.data.HistoryDatabaseProvider
import com.eduardoomarson.calculadoraimc.data.HistoryRepositoryImpl
import com.eduardoomarson.calculadoraimc.domain.HistoryIMC
import com.eduardoomarson.calculadoraimc.ui.theme.Blue
import com.eduardoomarson.calculadoraimc.ui.theme.LilasTMB
import com.eduardoomarson.calculadoraimc.ui.theme.PurpleCalorias
import com.eduardoomarson.calculadoraimc.ui.theme.PurpleHistorico
import com.eduardoomarson.calculadoraimc.ui.theme.PurplePeso
import com.eduardoomarson.calculadoraimc.ui.theme.White
import com.eduardoomarson.calculadoraimc.ui.theme.feature.history.HistoryEvent
import com.eduardoomarson.calculadoraimc.ui.theme.feature.imc.IMCEvent

@Composable
fun HomeScreen(
    id: Long? = null,
) {
    val context = LocalContext.current.applicationContext
    val database = HistoryDatabaseProvider.provide(context)
    val repository = HistoryRepositoryImpl(
        dao = database.historyDao
    )
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