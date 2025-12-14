package com.eduardoomarson.calculadoraimc.ui.theme.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduardoomarson.calculadoraimc.UiEvent
import com.eduardoomarson.calculadoraimc.navigation.HistoryRoute
import com.eduardoomarson.calculadoraimc.navigation.IMCRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val id: Long?= null
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: HomeEvent){
        when (event) {
            is HomeEvent.IMCNav -> {
                viewModelScope.launch{
                    _uiEvent.send(UiEvent.Navigate(IMCRoute(event.id)))
                }
            }
            is HomeEvent.TMBNav -> TODO()
            is HomeEvent.CaloriasNav -> TODO()
            is HomeEvent.PesoIdealNav -> TODO()
            is HomeEvent.HistoryNav ->  {
                viewModelScope.launch{
                    _uiEvent.send(UiEvent.Navigate(HistoryRoute(event.id)))
                }
            }
        }
    }
}