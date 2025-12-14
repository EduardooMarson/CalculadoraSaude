sealed class TMBEvent {
    data class OnWeightChange(val value: String) : TMBEvent()
    data class OnHeightChange(val value: String) : TMBEvent()
    data class OnAgeChange(val value: String) : TMBEvent()
    data class OnGenderChange(val value: String) : TMBEvent()
    object CalculateTBM : TMBEvent()
}
