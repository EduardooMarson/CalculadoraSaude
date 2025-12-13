package com.eduardoomarson.calculadoraimc.ui.theme.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduardoomarson.calculadoraimc.UiEvent
import com.eduardoomarson.calculadoraimc.data.HistoryRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val id: Long? = null,
    private val repository: HistoryRepository,
) : ViewModel() {

    val histories = repository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue= emptyList(),
        )

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: HistoryEvent){
        when (event){
            is HistoryEvent.Delete -> {
                delete(event.id)
            }
        }
    }

    private fun delete(id: Long){
        viewModelScope.launch {
            repository.delete(id)
        }
    }

}

