package com.miu.gardenmate.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miu.gardenmate.data.dao.PlantDao
import com.miu.gardenmate.data.entity.Plant

@Database(
    entities = [Plant::class],
    version = 1,
    exportSchema = false
)
abstract class GardenMateDatabase(): RoomDatabase() {
    abstract fun getPlanDao(): PlantDao

    companion object {
        @Volatile
        private var instance : GardenMateDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GardenMateDatabase::class.java,
            "GardenMateDatabase"
        ).build()
    }
}