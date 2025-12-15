sealed interface HomeEvent {

    /* -- Sugestão Claude -----*/
    /*  Prompt : Estou usando Jetpack Compose com Navigation.
                Quero disparar um evento de UI que navegue da Home
                para uma tela unificada de cálculos */
    /** Abre o hub de cálculos */
    object OpenCalculations : HomeEvent // Criação de objeto adaptado pela LLM

    data class IMCNav(val id: Long? = null) : HomeEvent
    data class TMBNav(val id: Long? = null) : HomeEvent

    data class HistoryNav(val id: Long? = null) : HomeEvent
}
