package com.eduardoomarson.calculadoraimc.data

import com.eduardoomarson.calculadoraimc.domain.HistoryIMC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryRepositoryImpl(
    private val dao: HistoryDao
) : HistoryRepository {
    override suspend fun insertIMC(
        date: String,
        hour: String,
        weight: String,
        height: String,
        imcDescription: String

    ) {
        val entity = HistoryIMCEntity(
            date = date,
            hour = hour,
            weight = weight,
            height = height,
            imcDescription = imcDescription
        )
        dao.insert(entity)
    }

    override suspend fun delete(id: Long) {
        val existentEntity = dao.getBy(id) ?: return
        dao.delete(existentEntity)
    }

    override fun getAll(): Flow<List<HistoryIMC>> {
        return dao.getAll().map{ entities ->
            entities.map{ entity ->
                HistoryIMC(
                    id = entity.id,
                    date = entity.date,
                    hour = entity.hour,
                    weight = entity.weight,
                    height = entity.height,
                    imcDescription = entity.imcDescription

                )
            }
        }
    }

    override suspend fun getBy(id: Long): HistoryIMC? {
        return dao.getBy(id)?.let{ entity ->
            HistoryIMC(
                id = entity.id,
                date = entity.date,
                hour = entity.hour,
                weight = entity.weight,
                height = entity.height,
                imcDescription = entity.imcDescription

            )
        }
    }
}