package com.eduardoomarson.calculadoraimc.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HistoryIMCEntity)

    @Delete
    suspend fun delete(entity: HistoryIMCEntity)

    @Query("SELECT * from historiesImc")
    fun getAll(): Flow<List<HistoryIMCEntity>>

    @Query("SELECT * from historiesImc WHERE id = :id")
    suspend fun getBy(id: Long) : HistoryIMCEntity?
}