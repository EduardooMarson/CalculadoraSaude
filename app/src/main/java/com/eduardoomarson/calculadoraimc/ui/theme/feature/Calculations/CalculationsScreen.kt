package com.eduardoomarson.calculadoraimc.ui.theme.feature.Calculations

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
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eduardoomarson.calculadoraimc.UiEvent
import com.eduardoomarson.calculadoraimc.data.HistoryRepository
import com.eduardoomarson.calculadoraimc.ui.theme.BlackPrimary
import com.eduardoomarson.calculadoraimc.ui.theme.GraySurface
import com.eduardoomarson.calculadoraimc.ui.theme.OrangePrimary
import com.eduardoomarson.calculadoraimc.ui.theme.Red
import com.eduardoomarson.calculadoraimc.ui.theme.White
import com.eduardoomarson.calculadoraimc.ui.theme.components.ActivityButton
import com.eduardoomarson.calculadoraimc.ui.theme.components.GenderButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    navigateBack: (() -> Unit)? = null,
    navigateHome: () -> Unit,
    onNavigateToIdealWeight: () -> Unit = {},
    repository: HistoryRepository
) {
    val viewModel: CalculationsViewModel = remember { CalculationsViewModel(repository) }
    val state by viewModel.state

    val snackbarHostState = remember { SnackbarHostState() }

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (state.calculationType) {
                            CalculationsType.IMC -> "Índice de Massa Corporal"
                            CalculationsType.TMB -> "Taxa Metabólica Basal"
                            CalculationsType.PESO_IDEAL -> "Peso Ideal"
                        },
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlackPrimary,
                    titleContentColor = White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Tipo de cálculo (IMC / TMB / PESO IDEAL)
            CalculationsTypeSelector(
                selectedType = state.calculationType,
                onTypeSelected = { type ->
                    viewModel.onEvent(CalculationsEvent.SetCalculationType(type))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Altura (comum a todos)
            OutlinedTextField(
                value = state.height,
                onValueChange = { viewModel.onEvent(CalculationsEvent.OnHeightChange(it)) },
                label = { Text(text = "Altura (cm)") },
                placeholder = { Text(text = "Ex: 170") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
                    focusedLabelColor = OrangePrimary,
                    cursorColor = OrangePrimary,
                    unfocusedBorderColor = BlackPrimary.copy(alpha = 0.4f),
                    errorLabelColor = Red
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Peso (apenas para IMC e TMB, não para Peso Ideal)
            if (state.calculationType != CalculationsType.PESO_IDEAL) {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.weight,
                    onValueChange = { viewModel.onEvent(CalculationsEvent.OnWeightChange(it)) },
                    label = { Text("Peso (kg)") },
                    placeholder = { Text("Ex: 70.5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = state.isError,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        focusedLabelColor = OrangePrimary,
                        cursorColor = OrangePrimary,
                        unfocusedBorderColor = BlackPrimary.copy(alpha = 0.4f),
                        errorLabelColor = Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Mostrar campos específicos do TMB
            if (state.calculationType == CalculationsType.TMB) {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.age,
                    onValueChange = { viewModel.onEvent(CalculationsEvent.OnAgeChange(it)) },
                    label = { Text("Idade") },
                    placeholder = { Text("Ex: 25") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = state.isError,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        focusedLabelColor = OrangePrimary,
                        cursorColor = OrangePrimary,
                        unfocusedBorderColor = BlackPrimary.copy(alpha = 0.4f),
                        errorLabelColor = Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Sexo biológico",
                    fontWeight = FontWeight.Bold,
                    color = BlackPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GenderButton(
                        text = "Masculino",
                        selected = state.gender == "Masculino",
                        onClick = { viewModel.onEvent(CalculationsEvent.OnGenderChange("Masculino")) },
                        modifier = Modifier.weight(1f)
                    )
                    GenderButton(
                        text = "Feminino",
                        selected = state.gender == "Feminino",
                        onClick = { viewModel.onEvent(CalculationsEvent.OnGenderChange("Feminino")) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Nível de atividade física",
                    fontWeight = FontWeight.Bold,
                    color = BlackPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActivityButton(
                        "Sedentário",
                        state.activityLevel == "Sedentário"
                    ) {
                        viewModel.onEvent(CalculationsEvent.OnActivityLevelChange("Sedentário"))
                    }
                    ActivityButton(
                        "Leve",
                        state.activityLevel == "Leve"
                    ) {
                        viewModel.onEvent(CalculationsEvent.OnActivityLevelChange("Leve"))
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActivityButton(
                        "Moderado",
                        state.activityLevel == "Moderado"
                    ) {
                        viewModel.onEvent(CalculationsEvent.OnActivityLevelChange("Moderado"))
                    }
                    ActivityButton(
                        "Intenso",
                        state.activityLevel == "Intenso"
                    ) {
                        viewModel.onEvent(CalculationsEvent.OnActivityLevelChange("Intenso"))
                    }
                }
            }

            // Campos específicos do Peso Ideal (Sexo)
            if (state.calculationType == CalculationsType.PESO_IDEAL) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Sexo biológico",
                    fontWeight = FontWeight.Bold,
                    color = BlackPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GenderButton(
                        text = "Masculino",
                        selected = state.gender == "Masculino",
                        onClick = { viewModel.onEvent(CalculationsEvent.OnGenderChange("Masculino")) },
                        modifier = Modifier.weight(1f)
                    )
                    GenderButton(
                        text = "Feminino",
                        selected = state.gender == "Feminino",
                        onClick = { viewModel.onEvent(CalculationsEvent.OnGenderChange("Feminino")) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botão calcular
            Button(
                onClick = { viewModel.onEvent(CalculationsEvent.Calculate) },
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "CALCULAR",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }

            // Resultado IMC
            if (state.calculationType == CalculationsType.IMC && state.imcDescription.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                        text = state.imcDescription,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangePrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }

            // Resultado TMB
            if (state.calculationType == CalculationsType.TMB && state.tmbDescription.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                // Card TMB
                Card(
                    colors = CardDefaults.cardColors(containerColor = GraySurface),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Taxa Metabólica Basal",
                            fontWeight = FontWeight.Bold,
                            color = BlackPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.tmbDescription,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = OrangePrimary,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "kcal/dia em repouso",
                            fontSize = 12.sp,
                            color = BlackPrimary.copy(alpha = 0.6f)
                        )
                    }
                }

                // Card de Necessidade Calórica Diária
                if (state.dailyCalories.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = OrangePrimary.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Necessidade Calórica Diária",
                                fontWeight = FontWeight.Bold,
                                color = BlackPrimary,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.dailyCalories,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = OrangePrimary,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Nível de atividade: ${state.activityLevel}",
                                fontSize = 12.sp,
                                color = BlackPrimary.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Resultado Peso Ideal
            if (state.calculationType == CalculationsType.PESO_IDEAL && state.idealWeightResult.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = GraySurface),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = state.idealWeightResult,
                            fontSize = 14.sp,
                            color = BlackPrimary,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // Próximos cálculos / Salvar
            if (state.imcDescription.isNotEmpty() || state.tmbDescription.isNotEmpty() || state.idealWeightResult.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.onEvent(CalculationsEvent.SaveAndNavigateHome(onSuccess = navigateHome))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BlackPrimary),
                    enabled = !state.isSaving,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = if (state.isSaving) "SALVANDO..." else "SALVAR E VOLTAR PARA HOME",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }
            }
        }
    }
}

@Composable
fun CalculationsTypeSelector(
    selectedType: CalculationsType,
    onTypeSelected: (CalculationsType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onTypeSelected(CalculationsType.IMC) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == CalculationsType.IMC) OrangePrimary else GraySurface,
                    contentColor = if (selectedType == CalculationsType.IMC) White else BlackPrimary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("IMC")
            }
            Button(
                onClick = { onTypeSelected(CalculationsType.TMB) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == CalculationsType.TMB) OrangePrimary else GraySurface,
                    contentColor = if (selectedType == CalculationsType.TMB) White else BlackPrimary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("TMB")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onTypeSelected(CalculationsType.PESO_IDEAL) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedType == CalculationsType.PESO_IDEAL) OrangePrimary else GraySurface,
                contentColor = if (selectedType == CalculationsType.PESO_IDEAL) White else BlackPrimary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("PESO IDEAL")
        }
    }
}