package com.eduardoomarson.calculadoraimc.domain

data class History(
    val id: Long,
    val date: String,
    val hour: String,
    val gender: String?,
    val age: String?,
    val height: String,
    val weight: String,
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

data class HistoryPesoIdeal(
    val id: Long,
    val date: String,
    val gender: String,
    val hour: String,
    val weight: String,
    val height: String,
    val pesoIdeal: String,
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


val history1 = History(
    id = 3,
    date = "15/12/2025",
    hour = "11:30",
    gender = "masculino",
    age = "19",
    height = "178",
    weight = "98.0",
    physicalActivities = null,
    imcDescription = "IMC: 34.9 \n Obesidade severa (Grau II)",
    tmbDescription = "34",
    pesoIdealDescription = null,
    caloriaDiariaDescription = null
)