package com.eduardoomarson.calculadoraimc.domain

data class History(
    val id: Long,
    val date: String,
    val hour: String,
    val gender: String?,
    val age: String?,
    val height: String?,
    val weight: String?,
    val physicalActivities: String?,
    val imcDescription: String?,
    val tmbDescription: String?,
    val pesoIdealDescription: String?,
    val caloriaDiariaDescription: String?
)
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

