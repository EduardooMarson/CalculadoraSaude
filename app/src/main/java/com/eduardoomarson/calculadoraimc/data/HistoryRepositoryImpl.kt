package com.eduardoomarson.calculadoraimc.data

import com.eduardoomarson.calculadoraimc.domain.History
import com.eduardoomarson.calculadoraimc.domain.HistoryIMC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryRepositoryImpl(
    private val dao: HistoryDao
) : HistoryRepository {

    override suspend fun insert(
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
    ) {
        val entity = HistoryEntity(
            date = date,
            hour = hour,
            gender = gender,
            age = age,
            height = height,
            weight = weight,
            physicalActivities = physicalActivities,
            imcDescription = imcDescription,
            tmbDescription = tmbDescription,
            pesoIdealDescription = pesoIdealDescription,
            caloriaDiariaDescription = caloriaDiariaDescription
        )
        dao.insert(entity)
    }


    override suspend fun delete(id: Long) {
        val existentEntity = dao.getBy(id) ?: return
        dao.delete(existentEntity)
    }

    override fun getAll(): Flow<List<History>> {
        return dao.getAll().map{ entities ->
            entities.map{ entity ->
                History(
                    id = entity.id,
                    date = entity.date,
                    hour = entity.hour,
                    gender = entity.gender,
                    age= entity.age,
                    physicalActivities = entity.physicalActivities,
                    weight = entity.weight,
                    height = entity.height,
                    imcDescription = entity.imcDescription,
                    tmbDescription = entity.tmbDescription,
                    pesoIdealDescription = entity.pesoIdealDescription,
                    caloriaDiariaDescription = entity.caloriaDiariaDescription
                )
            }
        }
    }

    override suspend fun getBy(id: Long): History? {
        return dao.getBy(id)?.let{ entity ->
            History(
                id = entity.id,
                date = entity.date,
                hour = entity.hour,
                gender = entity.gender,
                age = entity.age,
                weight = entity.weight,
                height = entity.height,
                physicalActivities = entity.physicalActivities,
                imcDescription = entity.imcDescription,
                tmbDescription = entity.tmbDescription,
                pesoIdealDescription = entity.pesoIdealDescription,
                caloriaDiariaDescription = entity.caloriaDiariaDescription
            )
        }
    }

}