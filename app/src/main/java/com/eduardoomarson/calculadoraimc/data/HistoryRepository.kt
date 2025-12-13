package com.eduardoomarson.calculadoraimc.data

import com.eduardoomarson.calculadoraimc.domain.HistoryIMC
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun insertIMC(
        date: String,
        hour: String,
        weight: String,
        height: String,
        imcDescription: String
    )

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<HistoryIMC>>

    suspend fun getBy(id: Long) : HistoryIMC?
}