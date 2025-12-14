sealed interface HomeEvent {

    /** Abre o hub de cálculos */
    object OpenCalculations : HomeEvent

    data class IMCNav(val id: Long? = null) : HomeEvent
    data class TMBNav(val id: Long? = null) : HomeEvent
    data class PesoIdealNav(val id: Long? = null) : HomeEvent
    data class CaloriasNav(val id: Long? = null) : HomeEvent
    data class HistoryNav(val id: Long? = null) : HomeEvent
}
