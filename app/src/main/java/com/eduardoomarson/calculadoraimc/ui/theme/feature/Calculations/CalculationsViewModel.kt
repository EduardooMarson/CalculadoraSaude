package com.eduardoomarson.calculadoraimc.ui.theme.feature.Calculations

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

data class CalculationsState(
    val calculationType: CalculationsType = CalculationsType.IMC,
    val height: String = "",
    val weight: String = "",
    val age: String = "",
    val gender: String = "Masculino",
    val activityLevel: String = "Sedentário",
    val result: String = "",
    val isError: Boolean = false,
    val imcDescription: String = ""
)

class CalculationsViewModel : ViewModel() {
    private val _state = mutableStateOf(CalculationsState())
    val state: State<CalculationsState> = _state

    fun onEvent(event: CalculationsEvent) {
        when (event) {
            is CalculationsEvent.SetCalculationType -> {
                _state.value = _state.value.copy(
                    calculationType = event.type,
                    result = "",
                    imcDescription = "",
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
                val s = _state.value
                val height = s.height.toDoubleOrNull()
                val weight = s.weight.toDoubleOrNull()
                val age = s.age.toIntOrNull()

                if (height == null || weight == null || height <= 0 || weight <= 0) {
                    _state.value = s.copy(isError = true)
                    return
                }

                when (s.calculationType) {
                    CalculationsType.IMC -> {
                        val imc = weight / ((height / 100.0) * (height / 100.0))
                        val description = when {
                            imc < 18.5 -> "Abaixo do peso"
                            imc < 25.0 -> "Peso normal"
                            imc < 30.0 -> "Sobrepeso"
                            else -> "Obesidade"
                        }
                        _state.value = s.copy(
                            imcDescription = "IMC: ${"%.1f".format(imc)}\n$description",
                            isError = false
                        )
                    }
                    CalculationsType.TMB -> {
                        if (age == null || age <= 0) {
                            _state.value = s.copy(isError = true)
                            return
                        }
                        val tmb = if (s.gender == "Masculino") {
                            88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age)
                        } else {
                            447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age)
                        }
                        val resultText = "TMB: ${"%.0f".format(tmb)} kcal/dia"
                        _state.value = s.copy(
                            result = resultText,
                            isError = false
                        )
                    }
                }
            }
        }
    }
}