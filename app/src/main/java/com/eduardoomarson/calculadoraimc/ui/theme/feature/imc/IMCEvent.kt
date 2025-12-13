package com.eduardoomarson.calculadoraimc.ui.theme.feature.imc

sealed interface IMCEvent {
    // Sugestão Claude
    data class OnHeightChange(val height: String) : IMCEvent
    data class OnWeightChange(val weight: String) : IMCEvent
    // Fim sugestão Claude
    data object IMCCalculations : IMCEvent
    data object Save : IMCEvent
}

