package com.eduardoomarson.calculadoraimc.data

import com.eduardoomarson.calculadoraimc.domain.History
import com.eduardoomarson.calculadoraimc.domain.HistoryIMC
import com.eduardoomarson.calculadoraimc.domain.HistoryPesoIdeal
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun insert(
        date: String,
        hour: String,
        gender: String?,
        age: String?,
        height: String,
        weight: String,
        physicalActivities: String?,
        imcDescription: String?,
        tmbDescription: String?,
        pesoIdealDescription: String?,
        caloriaDiariaDescription: String?
    )

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<History>>

    suspend fun getBy(id: Long) : History?

}