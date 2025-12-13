package com.eduardoomarson.calculadoraimc.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryIMCEntity::class],
    version = 1,

    )
abstract class CalculationsDatabase: RoomDatabase() {

    abstract val historyDao: HistoryDao
}

object HistoryDatabaseProvider {

    @Volatile
    private var INSTANCE: CalculationsDatabase? = null

    fun provide(context: Context): CalculationsDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                CalculationsDatabase::class.java,
                "calculations-app"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}