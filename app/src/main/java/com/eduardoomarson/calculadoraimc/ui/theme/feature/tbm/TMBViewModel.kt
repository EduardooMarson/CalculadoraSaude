package com.eduardoomarson.calculadoraimc.ui.theme.feature.tbm

import TMBEvent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduardoomarson.calculadoraimc.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TBMViewModel : ViewModel() {

    var weight by mutableStateOf("")
        private set
    var height by mutableStateOf("")
        private set
    var age by mutableStateOf("")
        private set
    var gender by mutableStateOf("Masculino")
        private set

    var result by mutableStateOf("")
        private set

    var isError by mutableStateOf(false)
        private set

    var activityLevel by mutableStateOf("Sedentário")
        private set

    var totalCalories by mutableStateOf("")
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: TMBEvent) {
        when (event) {
            is TMBEvent.OnWeightChange -> weight = event.value
            is TMBEvent.OnHeightChange -> height = event.value
            is TMBEvent.OnAgeChange -> age = event.value
            is TMBEvent.OnGenderChange -> gender = event.value
            is TMBEvent.OnActivityLevelChange -> activityLevel = event.value



            TMBEvent.CalculateTBM -> calculateTBM()
        }
    }

    private fun calculateTBM() {
        if (weight.isEmpty() || height.isEmpty() || age.isEmpty()) {
            isError = true
            viewModelScope.launch {
                _uiEvent.emit(UiEvent.ShowSnackbar("Preencha todos os campos"))
            }
            return
        }

        isError = false

        val peso = weight.toDouble()
        val altura = height.toDouble()
        val idade = age.toInt()

        val tbm = if (gender == "Masculino") {
            66.47 + (13.75 * peso) + (5.003 * altura) - (6.755 * idade)
        } else {
            655.1 + (9.563 * peso) + (1.850 * altura) - (4.676 * idade)
        }

        result = "Sua TMB é %.2f kcal/dia".format(tbm)
    }
}