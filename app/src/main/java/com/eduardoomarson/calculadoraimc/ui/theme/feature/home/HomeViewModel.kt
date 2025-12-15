package com.eduardoomarson.calculadoraimc.ui.theme.feature.home

import HomeEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduardoomarson.calculadoraimc.UiEvent
import com.eduardoomarson.calculadoraimc.navigation.CalculationsRoute
import com.eduardoomarson.calculadoraimc.navigation.HistoryRoute
import com.eduardoomarson.calculadoraimc.navigation.IMCRoute
import com.eduardoomarson.calculadoraimc.navigation.TMBRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    /* -- Sugestão Claude -----*/
    /*  Prompt : Estou usando Jetpack Compose com Navigation.
                Quero disparar um evento de UI que navegue da Home
                para uma tela unificada de cálculos */
    fun onEvent(event: HomeEvent) {
        when (event) {

            /* ---- Trecho adaptado pela LLM ----*/
            HomeEvent.OpenCalculations -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(CalculationsRoute)
                    )
                }
            }

            is HomeEvent.IMCNav -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(IMCRoute(event.id)))
                }
            }

            /* ---- Fim de trecho adaptado pela LLM -----*/

            is HomeEvent.TMBNav -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(TMBRoute(event.id)))
                }
            }

            is HomeEvent.HistoryNav -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(HistoryRoute(event.id)))
                }
            }

            else -> {}
        }
    }
}
