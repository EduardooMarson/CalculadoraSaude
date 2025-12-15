package com.eduardoomarson.calculadoraimc.data

import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "histories")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id:Long = 0,
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
