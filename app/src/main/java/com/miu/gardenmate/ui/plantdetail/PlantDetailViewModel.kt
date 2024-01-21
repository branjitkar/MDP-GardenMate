package com.miu.gardenmate.ui.plantdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.miu.gardenmate.data.entity.Plant
import com.miu.gardenmate.data.repository.PlantRepository
import kotlinx.coroutines.launch

class PlantDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PlantRepository
    lateinit var plantData: LiveData<Plant>

    init {
        var plantId:Long = 1L;
        repository = PlantRepository(application)
    }

    public fun setPlantData(plantId: Long) {
        viewModelScope.launch {
            plantData = repository.getPlantById(plantId);
        }
    }
}