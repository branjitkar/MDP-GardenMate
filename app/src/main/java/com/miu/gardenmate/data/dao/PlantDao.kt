package com.miu.gardenmate.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.miu.gardenmate.data.entity.Plant

@Dao
interface PlantDao {
    @Insert
    suspend fun addPlant(plant: Plant)
    @Query("SELECT * FROM plants ORDER BY id DESC")
    fun getAllPlants(): LiveData<List<Plant>>
    @Query("SELECT * FROM plants WHERE id = :id")
    fun getPlantById(id: Long): LiveData<Plant>
    @Update
    suspend fun updatePlant(plant: Plant)
    @Delete
    suspend fun deletePlant(plant: Plant)
    @Query("DELETE FROM plants")
    suspend fun deleteAll()
}