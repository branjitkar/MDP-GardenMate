package com.miu.gardenmate.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.miu.gardenmate.data.dao.PlantDao
import com.miu.gardenmate.data.database.GardenMateDatabase
import com.miu.gardenmate.data.entity.Plant
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Locale

class PlantRepository private constructor(application: Application) {
    private val plantDao: PlantDao

    init {
        plantDao = GardenMateDatabase(application).getPlanDao()
        runBlocking {
            refreshTestData()
        }
    }

    private suspend fun refreshTestData() {
        plantDao.deleteAll()
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        val testPlants = listOf(
            Plant(name = "Rose", type = "Flower", wateringFrequency = 2, plantedDate = dateFormat.parse("01/19/2024")?.time?:-1L, lastWateredDate = dateFormat.parse("01/19/2024")?.time?:-1L),
            Plant(name = "Basil", type = "Herb", wateringFrequency = 1, plantedDate = dateFormat.parse("01/18/2024")?.time?:-1L, lastWateredDate = dateFormat.parse("01/18/2024")?.time?:-1L),
            Plant(name = "Lily", type = "Flower", wateringFrequency = 3, plantedDate = System.currentTimeMillis(), lastWateredDate = System.currentTimeMillis()),
            Plant(name = "Mint", type = "Herb", wateringFrequency = 2, plantedDate = System.currentTimeMillis(), lastWateredDate = System.currentTimeMillis())
        )

        for (plant in testPlants) {
            plantDao.addPlant(plant)
        }
    }

    fun getAllPlants(): LiveData<List<Plant>> {
        return plantDao.getAllPlants()
    }

    fun getPlantById(plantId: Long): LiveData<Plant> {
        return plantDao.getPlantById(plantId)
    }

    suspend fun addPlant(plant: Plant) {
        plantDao.addPlant(plant)
    }

    suspend fun updatePlant(plant: Plant) {
        plantDao.updatePlant(plant)
    }

    suspend fun delete(plant: Plant) {
        plantDao.deletePlant(plant)
    }

    companion object {
        @Volatile
        private var instance: PlantRepository? = null

        operator fun invoke(application: Application): PlantRepository {
            return instance ?: synchronized(this) {
                instance ?: PlantRepository(application).also { instance = it }
            }
        }
    }
}
