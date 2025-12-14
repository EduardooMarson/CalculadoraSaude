package com.eduardoomarson.calculadoraimc.ui.theme.feature.tmb

import TMBEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduardoomarson.calculadoraimc.UiEvent
import com.eduardoomarson.calculadoraimc.ui.theme.BlackPrimary
import com.eduardoomarson.calculadoraimc.ui.theme.GraySurface
import com.eduardoomarson.calculadoraimc.ui.theme.OrangePrimary
import com.eduardoomarson.calculadoraimc.ui.theme.WhitePrimary
import com.eduardoomarson.calculadoraimc.ui.theme.components.ActivityButton
import com.eduardoomarson.calculadoraimc.ui.theme.feature.tbm.TBMViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TMBScreen(
    navigateBack: (() -> Unit)? = null,
    id: Long?,
    navigateHome: () -> Unit,
    onNavigateToIdealWeight: () -> Unit = {}
) {
    val viewModel = viewModel<TBMViewModel>()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(event.message)
                UiEvent.NavigateBack ->
                    navigateBack?.invoke()
                else -> {}
            }
        }
    }

    TMBContent(
        weight = viewModel.weight,
        height = viewModel.height,
        age = viewModel.age,
        gender = viewModel.gender,
        result = viewModel.result,
        isError = viewModel.isError,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        onNavigateToIdealWeight = onNavigateToIdealWeight
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TMBContent(
    weight: String,
    height: String,
    age: String,
    gender: String,
    result: String,
    isError: Boolean,
    snackbarHostState: SnackbarHostState,
    onEvent: (TMBEvent) -> Unit,
    onNavigateToIdealWeight: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Taxa Metabólica Basal",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlackPrimary,
                    titleContentColor = WhitePrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = WhitePrimary
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            /* ---------- DESCRIÇÃO ---------- */
            Text(
                text = "Informe seus dados para calcular quantas calorias seu corpo gasta em repouso.",
                fontSize = 14.sp,
                color = BlackPrimary.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------- PESO ---------- */
            OutlinedTextField(
                value = weight,
                onValueChange = { onEvent(TMBEvent.OnWeightChange(it)) },
                label = { Text("Peso (kg)") },
                placeholder = { Text("Ex: 70.5") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = isError && weight.isEmpty(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- ALTURA ---------- */
            OutlinedTextField(
                value = height,
                onValueChange = { onEvent(TMBEvent.OnHeightChange(it)) },
                label = { Text("Altura (cm)") },
                placeholder = { Text("Ex: 170") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isError && height.isEmpty(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- IDADE ---------- */
            OutlinedTextField(
                value = age,
                onValueChange = { onEvent(TMBEvent.OnAgeChange(it)) },
                label = { Text("Idade") },
                placeholder = { Text("Ex: 25") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isError && age.isEmpty(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------- SEXO ---------- */
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
                    selected = gender == "Masculino",
                    onClick = { onEvent(TMBEvent.OnGenderChange("Masculino")) },
                    modifier = Modifier.weight(1f)
                )

                GenderButton(
                    text = "Feminino",
                    selected = gender == "Feminino",
                    onClick = { onEvent(TMBEvent.OnGenderChange("Feminino")) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            /* ---------- BOTÃO CALCULAR ---------- */
            Button(
                onClick = { onEvent(TMBEvent.CalculateTBM) },
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "CALCULAR TMB",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = WhitePrimary
                )
            }

            /* ---------- RESULTADO ---------- */
            if (result.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = GraySurface
                    ),
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
                            text = result,
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
                /* ---------- FATOR ATIVIDADE ---------- */
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
                    val activityLevel = null
                    ActivityButton("Sedentário", activityLevel == "Sedentário") {
                        onEvent(TMBEvent.OnActivityLevelChange("Sedentário"))
                    }
                    ActivityButton("Leve", activityLevel == "Leve") {
                        onEvent(TMBEvent.OnActivityLevelChange("Leve"))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val activityLevel = null
                    ActivityButton("Moderado", activityLevel == "Moderado") {
                        onEvent(TMBEvent.OnActivityLevelChange("Moderado"))
                    }
                    ActivityButton("Intenso", activityLevel == "Intenso") {
                        onEvent(TMBEvent.OnActivityLevelChange("Intenso"))
                    }
                }

                /* ---------- PRÓXIMOS CÁLCULOS ---------- */
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
fun GenderButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) OrangePrimary else GraySurface,
            contentColor = if (selected) WhitePrimary else BlackPrimary
        )
    ) {
        Text(text)
    }
}

@Composable
fun NextCalculationCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors(
            containerColor = WhitePrimary
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(40.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = BlackPrimary
                )

                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = BlackPrimary.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TMBScreenPreview() {
    TMBContent(
        weight = "",
        height = "",
        age = "",
        gender = "Masculino",
        result = "",
        isError = false,
        snackbarHostState = SnackbarHostState(),
        onEvent = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun TMBScreenWithResultPreview() {
    TMBContent(
        weight = "70",
        height = "170",
        age = "25",
        gender = "Masculino",
        result = "Sua TMB é 1.674,97 kcal/dia",
        isError = false,
        snackbarHostState = SnackbarHostState(),
        onEvent = {}
    )
}