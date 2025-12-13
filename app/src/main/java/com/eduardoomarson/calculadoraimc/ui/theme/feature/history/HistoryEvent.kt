package com.eduardoomarson.calculadoraimc.ui.theme.feature.history

sealed interface HistoryEvent {
    data class Delete(val id: Long) : HistoryEvent
}