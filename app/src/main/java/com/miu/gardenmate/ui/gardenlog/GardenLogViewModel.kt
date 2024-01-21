package com.miu.gardenmate.ui.gardenlog

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.miu.gardenmate.data.entity.Plant
import com.miu.gardenmate.data.repository.PlantRepository
import kotlinx.coroutines.launch

class GardenLogViewModel(application: Application): AndroidViewModel(application) {
    private val repo: PlantRepository
    lateinit var allPlants: LiveData<List<Plant>>

    init {
        repo = PlantRepository(application)
        viewModelScope.launch {
            allPlants = repo.getAllPlants()
        }
    }

    fun deletePlant(plant: Plant) {
        viewModelScope.launch {
            try {
                repo.delete(plant)
            }
            catch (ex: Exception) {
                Log.e("GardenLogViewModel", "Error deleting plant. ${ex.stackTrace}")
            }
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            try {
                repo.updatePlant(plant)
            }
            catch (ex: Exception) {
                Log.e("GardenLogViewModel", "Error updating plant. ${ex.stackTrace}")
            }
        }
    }
}