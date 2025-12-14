package com.eduardoomarson.calculadoraimc.ui.theme.feature.CalculationsHubScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eduardoomarson.calculadoraimc.ui.theme.BlackPrimary
import com.eduardoomarson.calculadoraimc.ui.theme.White
import com.eduardoomarson.calculadoraimc.ui.theme.components.HomePrimaryCard
import com.eduardoomarson.calculadoraimc.ui.theme.components.HomeSecondaryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculationsHubScreen(
    navigateToIMC: () -> Unit,
    navigateToTMB: () -> Unit,
    navigateToHistory: () -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "O que deseja calcular?",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlackPrimary,
                    titleContentColor = White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(White)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Escolha uma métrica de saúde para continuar:",
                fontSize = 14.sp,
                color = BlackPrimary.copy(alpha = 0.7f)
            )

            HomeSecondaryCard(
                title = "Calcular IMC",
                subtitle = "Índice de Massa Corporal",
                icon = Icons.Default.MonitorWeight,
                onClick = navigateToIMC
            )

            HomeSecondaryCard(
                title = "Taxa Metabólica Basal",
                subtitle = "Calorias em repouso",
                icon = Icons.Default.LocalFireDepartment,
                onClick = navigateToTMB
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Você pode parar por aqui e voltar depois 🙂",
                fontSize = 12.sp,
                color = BlackPrimary.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
