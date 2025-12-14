package com.eduardoomarson.calculadoraimc.ui.theme.feature.imc

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduardoomarson.calculadoraimc.UiEvent
import com.eduardoomarson.calculadoraimc.UiEvent.*
import com.eduardoomarson.calculadoraimc.data.HistoryRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
//Sugestão Claude
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
// Sugestão Claude

class IMCViewModel(
    private val id: Long?= null,
    private val repository: HistoryRepository,
) : ViewModel() {

    var date by mutableStateOf("")
        private set
    var hour by mutableStateOf("")
        private set
    var height by mutableStateOf("")
        private set
    var weight by mutableStateOf("")
        private set
    var imcDescription by mutableStateOf("")
        private set
    var isError by mutableStateOf(false)
        private set

    init{
        id?.let{
            viewModelScope.launch {
                val historyImc = repository.getBy(it)
                date = historyImc?.date ?: ""
                hour = historyImc?.hour ?: ""
                height = historyImc?.height ?: ""
                weight = historyImc?.weight ?: ""
                imcDescription =  historyImc?.imcDescription ?: ""
            }
        }
    }
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: IMCEvent){
        when(event){
            // Sugestão Claude
            is IMCEvent.OnHeightChange -> {
                if (event.height.length <= 3) {
                    height = event.height
                }
            }
            is IMCEvent.OnWeightChange -> {
                if (event.weight.length <= 7) {
                    weight = event.weight
                }
            }
            // Fim sugestão Claude
            IMCEvent.IMCCalculations -> {
                calculateIMC()
            }
            IMCEvent.Save -> {
                saveIMCHistory()
            }

        }
    }

    @SuppressLint("DefaultLocale")
    private fun calculateIMC( ){

        if (height.isNotEmpty() && weight.isNotEmpty()) {
            val weightFormatted = weight.replace(",", ".").toDoubleOrNull()
            val heightFormatted = height.toDoubleOrNull()

            if (weightFormatted != null && heightFormatted != null) {
                val heightMeters = heightFormatted / 100
                val imc = weightFormatted / (heightMeters * heightMeters)
                val imcFormatted = String.format("%.2f", imc)

                isError = false

                imcDescription = when {
                    imc < 18.5 -> "IMC: $imcFormatted\nAbaixo do peso"
                    imc in 18.5..24.9 -> "IMC: $imcFormatted\nPeso Normal"
                    imc in 25.0..29.9 -> "IMC: $imcFormatted\nSobrepeso"
                    imc in 30.0..34.9 -> "IMC: $imcFormatted\nObesidade (Grau I)"
                    imc in 35.0..39.9 -> "IMC: $imcFormatted\nObesidade Severa (Grau II)"
                    else -> "IMC: $imcFormatted\nObesidade Mórbida (Grau III)"
                }
            }

            // Sugestão Claude
            val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            date = currentDate
            hour = currentTime

            // Sugestão Claude

            saveIMCHistory()
        } else {
            isError = true
            viewModelScope.launch {
                _uiEvent.send(UiEvent.ShowSnackbar("Preencha todos os campos!"))
            }
            return
        }
    }

    private fun saveIMCHistory() {
        if (imcDescription.isEmpty()) {
            viewModelScope.launch {
                _uiEvent.send(UiEvent.ShowSnackbar("Calcule o IMC primeiro!"))
            }
            return
        }

        viewModelScope.launch {
            try {
                repository.insertIMC(date, hour, weight, height, imcDescription)
                _uiEvent.send(UiEvent.NavigateBack)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.ShowSnackbar("Erro ao salvar: ${e.message}"))
            }
        }
    }
}

