package com.eduardoomarson.calculadoraimc.ui.theme.feature.imc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduardoomarson.calculadoraimc.UiEvent
import com.eduardoomarson.calculadoraimc.data.HistoryDatabaseProvider
import com.eduardoomarson.calculadoraimc.data.HistoryRepositoryImpl
import com.eduardoomarson.calculadoraimc.ui.theme.BlackPrimary
import com.eduardoomarson.calculadoraimc.ui.theme.OrangePrimary
import com.eduardoomarson.calculadoraimc.ui.theme.White
import com.eduardoomarson.calculadoraimc.ui.theme.components.HomeSecondaryCard
import com.eduardoomarson.calculadoraimc.ui.theme.components.NextCalculationCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IMCScreen(
    id: Long? = null,
    navigateBack: (() -> Unit)? = null,
    navigateToTMB: () -> Unit,
    navigateHome: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val database = HistoryDatabaseProvider.provide(context)
    val repository = HistoryRepositoryImpl(
        dao = database.historyDao)
    val viewModel = viewModel<IMCViewModel> {
        IMCViewModel(
            id = id,
            repository = repository
        )
    }


    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit){
        viewModel.uiEvent.collect{ uiEvent ->
            when(uiEvent){
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }
                is UiEvent.Navigate<*> -> {
                    
                }
                UiEvent.NavigateBack -> {
                    navigateBack?.invoke()
                }
            }
        }
    }

    IMCContent(
        date = viewModel.date,
        hour = viewModel.hour,
        height = viewModel.height,
        weight = viewModel.weight,
        imcDescription = viewModel.imcDescription,
        isError = viewModel.isError,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        onNavigateToTMB = navigateToTMB,
        onNavigateToHistory = {
            navigateHome()
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IMCContent(
    date: String,
    hour: String,
    height: String,
    weight: String,
    imcDescription: String,
    isError: Boolean,
    snackbarHostState: SnackbarHostState,
    onEvent: (IMCEvent) -> Unit,
    onNavigateToTMB: () -> Unit,
    onNavigateToHistory: () -> Unit

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Índice de Massa Corporal",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlackPrimary,
                    titleContentColor = White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(White)
                .verticalScroll(rememberScrollState())
        ) {

            /* ---------- DESCRIÇÃO ---------- */
            Text(
                text = "Calcule rapidamente seu IMC com base na sua altura e peso.",
                fontSize = 14.sp,
                color = BlackPrimary.copy(alpha = 0.7f),
                modifier = Modifier.padding(20.dp)
            )

            /* ---------- ALTURA ---------- */
            OutlinedTextField(
                value = height,
                onValueChange = { onEvent(IMCEvent.OnHeightChange(it)) },
                label = { Text("Altura (cm)") },
                placeholder = { Text("Ex: 170") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isError && height.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
                    focusedLabelColor = OrangePrimary,
                    cursorColor = OrangePrimary,
                    unfocusedBorderColor = BlackPrimary.copy(alpha = 0.4f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            /* ---------- PESO ---------- */
            OutlinedTextField(
                value = weight,
                onValueChange = { onEvent(IMCEvent.OnWeightChange(it)) },
                label = { Text("Peso (kg)") },
                placeholder = { Text("Ex: 70.5") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = isError && weight.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
                    focusedLabelColor = OrangePrimary,
                    cursorColor = OrangePrimary,
                    unfocusedBorderColor = BlackPrimary.copy(alpha = 0.4f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            /* ---------- BOTÃO CALCULAR ---------- */
            Button(
                onClick = { onEvent(IMCEvent.IMCCalculations) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangePrimary,
                    contentColor = White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100 .dp)
                    .padding(20.dp)
            ) {
                Text(
                    text = "CALCULAR IMC",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            /* ---------- RESULTADO ---------- */
            if (imcDescription.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .background(
                            color = OrangePrimary.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(18.dp)
                        )
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Resultado",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = BlackPrimary
                    )

                    Text(
                        text = imcDescription,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangePrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }

            /* ---------- PRÓXIMOS PASSOS ---------- */
            if (imcDescription.isNotEmpty()) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Próximos cálculos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    NextCalculationCard(
                        title = "Taxa Metabólica Basal",
                        subtitle = "Descubra quantas calorias seu corpo gasta",
                        icon = Icons.Default.LocalFireDepartment,
                        onClick = onNavigateToTMB
                    )

                }
            }


        }
    }
}


@Preview
@Composable
private fun IMCScreenPreview() {
    IMCContent(
        date = "",
        hour = "",
        height = "",
        weight = "",
        imcDescription = "",
        isError = false,
        snackbarHostState = SnackbarHostState(),
        onEvent = { },
        onNavigateToTMB = TODO(),
        onNavigateToHistory = TODO()
    )
}