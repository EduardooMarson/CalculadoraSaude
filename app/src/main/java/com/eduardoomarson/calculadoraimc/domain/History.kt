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


