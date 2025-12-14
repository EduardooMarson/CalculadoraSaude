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