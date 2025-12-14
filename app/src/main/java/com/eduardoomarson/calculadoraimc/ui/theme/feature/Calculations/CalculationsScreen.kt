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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduardoomarson.calculadoraimc.ui.theme.BlackPrimary
import com.eduardoomarson.calculadoraimc.ui.theme.GraySurface
import com.eduardoomarson.calculadoraimc.ui.theme.OrangePrimary
import com.eduardoomarson.calculadoraimc.ui.theme.White
import com.eduardoomarson.calculadoraimc.ui.theme.components.ActivityButton
import com.eduardoomarson.calculadoraimc.ui.theme.components.NextCalculationCard
import com.eduardoomarson.calculadoraimc.ui.theme.feature.tmb.GenderButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    navigateBack: (() -> Unit)? = null,
    navigateHome: () -> Unit,
    onNavigateToIdealWeight: () -> Unit = {}
) {
    val viewModel: CalculationsViewModel = viewModel()
    val state by viewModel.state

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        // Se quiser mostrar Snackbar em algum UiEvent, adicione aqui
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (state.calculationType) {
                            CalculationsType.IMC -> "Índice de Massa Corporal"
                            CalculationsType.TMB -> "Taxa Metabólica Basal"
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
            // Tipo de cálculo (IMC / TMB)
            CalculationsTypeSelector(
                selectedType = state.calculationType,
                onTypeSelected = { type ->
                    viewModel.onEvent(CalculationsEvent.SetCalculationType(type))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Altura (comum aos dois)
            OutlinedTextField(
                value = state.height,
                onValueChange = { viewModel.onEvent(CalculationsEvent.OnHeightChange(it)) },
                label = { Text(text = "Altura (cm)") },
                placeholder = { Text(text = "Ex: 170") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.isError && state.height.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
                    focusedLabelColor = OrangePrimary,
                    cursorColor = OrangePrimary,
                    unfocusedBorderColor = BlackPrimary.copy(alpha = 0.4f)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Peso (comum aos dois)
            OutlinedTextField(
                value = state.weight,
                onValueChange = { viewModel.onEvent(CalculationsEvent.OnWeightChange(it)) },
                label = { Text("Peso (kg)") },
                placeholder = { Text("Ex: 70.5") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = state.isError && state.weight.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
                    focusedLabelColor = OrangePrimary,
                    cursorColor = OrangePrimary,
                    unfocusedBorderColor = BlackPrimary.copy(alpha = 0.4f)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Mostrar campos específicos do TMB
            if (state.calculationType == CalculationsType.TMB) {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.age,
                    onValueChange = { viewModel.onEvent(CalculationsEvent.OnAgeChange(it)) },
                    label = { Text("Idade") },
                    placeholder = { Text("Ex: 25") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = state.isError && state.age.isEmpty(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        focusedLabelColor = OrangePrimary,
                        cursorColor = OrangePrimary,
                        unfocusedBorderColor = BlackPrimary.copy(alpha = 0.4f)
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
            if (state.calculationType == CalculationsType.TMB && state.result.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
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
                            text = "Resultado",
                            fontWeight = FontWeight.Bold,
                            color = BlackPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.result,
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
            }

            // Próximos cálculos
            if (state.imcDescription.isNotEmpty() || state.result.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Próximos cálculos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                NextCalculationCard(
                    title = "Peso Ideal",
                    subtitle = "Descubra qual é o seu peso ideal",
                    icon = Icons.Default.FitnessCenter,
                    onClick = onNavigateToIdealWeight
                )
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
    Row(
        modifier = modifier.fillMaxWidth(),
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
}



@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen(
        navigateBack = {},
        navigateHome = {},
        onNavigateToIdealWeight = {}
    )
}

@Preview(showBackground = true, name = "IMC Calculado")
@Composable
fun CalculatorScreenIMCResultPreview() {
    // Criar um ViewModel mockado com estado preenchido
    val mockViewModel = CalculationsViewModel().apply {
        onEvent(CalculationsEvent.OnHeightChange("170"))
        onEvent(CalculationsEvent.OnWeightChange("70"))
        onEvent(CalculationsEvent.Calculate)
    }

    CalculatorScreen(
        navigateBack = {},
        navigateHome = {},
        onNavigateToIdealWeight = {}
    )
}

@Preview(showBackground = true, name = "TMB Selecionado")
@Composable
fun CalculatorScreenTMBPreview() {
    val mockViewModel = CalculationsViewModel().apply {
        onEvent(CalculationsEvent.SetCalculationType(CalculationsType.TMB))
        onEvent(CalculationsEvent.OnHeightChange("175"))
        onEvent(CalculationsEvent.OnWeightChange("80"))
        onEvent(CalculationsEvent.OnAgeChange("30"))
        onEvent(CalculationsEvent.OnGenderChange("Masculino"))
        onEvent(CalculationsEvent.OnActivityLevelChange("Moderado"))
    }

    CalculatorScreen(
        navigateBack = {},
        navigateHome = {},
        onNavigateToIdealWeight = {}
    )
}

@Preview(showBackground = true, name = "Erro de Validação")
@Composable
fun CalculatorScreenErrorPreview() {
    val mockViewModel = CalculationsViewModel().apply {
        onEvent(CalculationsEvent.Calculate) // Tentar calcular sem preencher
    }

    CalculatorScreen(
        navigateBack = {},
        navigateHome = {},
        onNavigateToIdealWeight = {}
    )
}

@Preview(showBackground = true, name = "Type Selector")
@Composable
fun CalculationsTypeSelectorPreview() {
    CalculationsTypeSelector(
        selectedType = CalculationsType.IMC,
        onTypeSelected = {}
    )
}