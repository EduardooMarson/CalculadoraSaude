package com.eduardoomarson.calculadoraimc.domain

data class HistoryIMC(
    val id: Long,
    val date: String,
    val hour: String,
    val weight: String,
    val height: String,
    val imcDescription: String,
)



val historyIMC1 = HistoryIMC(
    id = 1,
    date = "12/12/2025",
    hour = "11:30",
    weight = "61.0",
    height = "165",
    imcDescription = "IMC: 24.9 \n Peso Normal"
)

val historyIMC2 = HistoryIMC(
    id = 2,
    date = "15/12/2025",
    hour = "17:04",
    weight = "98.0",
    height = "177",
    imcDescription = "IMC: 34.9 \n Obesidade severa (Grau II)"
)


