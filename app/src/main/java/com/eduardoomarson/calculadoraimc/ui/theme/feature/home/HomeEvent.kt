package com.eduardoomarson.calculadoraimc.ui.theme.feature.home

sealed interface HomeEvent {
    data class IMCNav(val id : Long?) : HomeEvent
    data class TMBNav(val id : Long?) : HomeEvent
    data class PesoIdealNav(val id : Long?) : HomeEvent
    data class CaloriasNav(val id : Long?) : HomeEvent
    data class HistoryNav(val id : Long?) : HomeEvent
}