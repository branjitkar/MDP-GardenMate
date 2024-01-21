package com.miu.gardenmate.ui.addplant

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.miu.gardenmate.data.entity.Plant
import com.miu.gardenmate.data.repository.PlantRepository
import kotlinx.coroutines.launch

class AddPlantViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PlantRepository

    init{
        repository = PlantRepository(application)
    }

    fun addPlant(plant: Plant) = viewModelScope.launch {
        repository.addPlant(plant)
    }
}