package com.eduardoomarson.calculadoraimc.ui.theme.feature.Calculations

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.eduardoomarson.calculadoraimc.data.HistoryRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CalculationsState(
    val calculationType: CalculationsType = CalculationsType.IMC,
    val height: String = "",
    val weight: String = "",
    val age: String = "",
    val gender: String = "Masculino",
    val activityLevel: String = "Sedentário",
    val isError: Boolean = false,

    // Resultados persistidos
    val imcDescription: String = "",
    val tmbDescription: String = "",
    val dailyCalories: String = "",
    val idealWeightResult: String = "",

    // Estado de salvamento
    val isSaving: Boolean = false,
    val showSuccessMessage: Boolean = false
)

class CalculationsViewModel(
    private val repository: HistoryRepository
) : ViewModel() {
    private val _state = mutableStateOf(CalculationsState())
    val state: State<CalculationsState> = _state

    fun onEvent(event: CalculationsEvent) {
        when (event) {
            is CalculationsEvent.SetCalculationType -> {
                _state.value = _state.value.copy(
                    calculationType = event.type,
                    isError = false
                )
            }
            is CalculationsEvent.OnHeightChange -> {
                _state.value = _state.value.copy(height = event.value)
            }
            is CalculationsEvent.OnWeightChange -> {
                _state.value = _state.value.copy(weight = event.value)
            }
            is CalculationsEvent.OnAgeChange -> {
                _state.value = _state.value.copy(age = event.value)
            }
            is CalculationsEvent.OnGenderChange -> {
                _state.value = _state.value.copy(gender = event.value)
            }
            is CalculationsEvent.OnActivityLevelChange -> {
                _state.value = _state.value.copy(activityLevel = event.level)
            }
            CalculationsEvent.Calculate -> {
                calculateCurrent()
            }
            is CalculationsEvent.SaveAndNavigateHome -> {
                saveToDatabase(event.onSuccess)
            }
        }
    }

    private fun calculateCurrent() {
        val s = _state.value
        val height = s.height.toDoubleOrNull()
        val weight = s.weight.toDoubleOrNull()
        val age = s.age.toIntOrNull()

        when (s.calculationType) {
            CalculationsType.IMC -> {
                if (height == null || weight == null || height <= 0 || weight <= 0) {
                    _state.value = s.copy(isError = true)
                    return
                }

                val imc = weight / ((height / 100.0) * (height / 100.0))
                val description = when {
                    imc < 18.5 -> "Abaixo do peso"
                    imc in 18.5..24.9 -> "Peso Normal"
                    imc in 25.0..29.9 -> "Sobrepeso"
                    imc in 30.0..34.9 -> "Obesidade (Grau I)"
                    imc in 35.0..39.9 -> "Obesidade Severa (Grau II)"
                    else -> "Obesidade Mórbida (Grau III)"
                }
                _state.value = s.copy(
                    imcDescription = "IMC: ${"%.1f".format(imc)}\n$description",
                    isError = false
                )
            }

            CalculationsType.TMB -> {
                if (height == null || weight == null || height <= 0 || weight <= 0) {
                    _state.value = s.copy(isError = true)
                    return
                }
                if (age == null || age <= 0) {
                    _state.value = s.copy(isError = true)
                    return
                }

                val tmb = if (s.gender == "Masculino") {
                    88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age)
                } else {
                    447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age)
                }

                val activityFactor = when (s.activityLevel) {
                    "Sedentário" -> 1.2
                    "Leve" -> 1.375
                    "Moderado" -> 1.55
                    "Intenso" -> 1.725
                    else -> 1.2
                }
                val dailyCaloriesValue = tmb * activityFactor

                _state.value = s.copy(
                    tmbDescription = "TMB: ${"%.0f".format(tmb)} kcal/dia",
                    dailyCalories = "${"%.0f".format(dailyCaloriesValue)} kcal/dia",
                    isError = false
                )
            }

            CalculationsType.PESO_IDEAL -> {
                if (height == null || height <= 0) {
                    _state.value = s.copy(isError = true)
                    return
                }

                val heightInCm = height

                val devineWeight = if (s.gender == "Masculino") {
                    50.0 + 2.3 * ((heightInCm / 2.54) - 60)
                } else {
                    45.5 + 2.3 * ((heightInCm / 2.54) - 60)
                }

                val robinsonWeight = if (s.gender == "Masculino") {
                    52.0 + 1.9 * ((heightInCm / 2.54) - 60)
                } else {
                    49.0 + 1.7 * ((heightInCm / 2.54) - 60)
                }

                val millerWeight = if (s.gender == "Masculino") {
                    56.2 + 1.41 * ((heightInCm / 2.54) - 60)
                } else {
                    53.1 + 1.36 * ((heightInCm / 2.54) - 60)
                }

                val averageWeight = (devineWeight + robinsonWeight + millerWeight) / 3

                val resultBuilder = StringBuilder()
                resultBuilder.append("Peso Ideal (média): ${"%.1f".format(averageWeight)} kg\n\n")
                resultBuilder.append("Fórmulas individuais:\n")
                resultBuilder.append("• Devine: ${"%.1f".format(devineWeight)} kg\n")
                resultBuilder.append("• Robinson: ${"%.1f".format(robinsonWeight)} kg\n")
                resultBuilder.append("• Miller: ${"%.1f".format(millerWeight)} kg")

                _state.value = s.copy(
                    idealWeightResult = resultBuilder.toString(),
                    isError = false
                )
            }
        }
    }

    private fun saveToDatabase(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isSaving = true)

                val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                val currentHour = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

                val s = _state.value

                repository.insert(
                    date = currentDate,
                    hour = currentHour,
                    gender = s.gender.takeIf { it.isNotEmpty() },
                    age = s.age.takeIf { it.isNotEmpty() },
                    height = s.height,
                    weight = s.weight,
                    physicalActivities = s.activityLevel.takeIf { it.isNotEmpty() },
                    imcDescription = s.imcDescription.takeIf { it.isNotEmpty() },
                    tmbDescription = s.tmbDescription.takeIf { it.isNotEmpty() },
                    pesoIdealDescription = s.idealWeightResult.takeIf { it.isNotEmpty() },
                    caloriaDiariaDescription = s.dailyCalories.takeIf { it.isNotEmpty() }
                )

                _state.value = s.copy(
                    isSaving = false,
                    showSuccessMessage = true
                )

                onSuccess()
            } catch (e: Exception) {
                _state.value = _state.value.copy(isSaving = false)
                // TODO: Tratar erro se necessário
            }
        }
    }
}