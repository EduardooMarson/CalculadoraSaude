package com.eduardoomarson.calculadoraimc.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "historiesImc")
data class HistoryIMCEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val hour: String,
    val weight: String,
    val height: String,
    val imcDescription: String
)

