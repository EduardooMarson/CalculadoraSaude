package com.eduardoomarson.calculadoraimc.ui.theme.feature.Calculations

sealed class CalculationsEvent {
    data class SetCalculationType(val type: CalculationsType) : CalculationsEvent()
    data class OnHeightChange(val value: String) : CalculationsEvent()
    data class OnWeightChange(val value: String) : CalculationsEvent()
    data class OnAgeChange(val value: String) : CalculationsEvent()
    data class OnGenderChange(val value: String) : CalculationsEvent()
    data class OnActivityLevelChange(val level: String) : CalculationsEvent()
    object Calculate : CalculationsEvent()
}

enum class CalculationsType {
    IMC,
    TMB
}
