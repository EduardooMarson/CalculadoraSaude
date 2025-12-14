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
    suspend fun insert(entity: HistoryEntity)

    @Delete
    suspend fun delete(entity: HistoryEntity)


    @Query("SELECT * from histories")
    fun getAll(): Flow<List<HistoryEntity>>

    @Query("SELECT * from histories WHERE id = :id")
    suspend fun getBy(id: Long) : HistoryEntity?

}