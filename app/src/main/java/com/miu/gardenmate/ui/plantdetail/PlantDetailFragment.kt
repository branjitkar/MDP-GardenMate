package com.miu.gardenmate.ui.plantdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.miu.gardenmate.data.entity.Plant
import com.miu.gardenmate.databinding.FragmentPlantDetailBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PlantDetailFragment: Fragment() {
    private lateinit var binding: FragmentPlantDetailBinding
    private lateinit var viewModel: PlantDetailViewModel
    private val nargs : PlantDetailFragmentArgs by navArgs()

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlantDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(PlantDetailViewModel::class.java)
        viewModel.setPlantData(nargs.plantId);

        viewModel.plantData.observe(viewLifecycleOwner) {plant ->
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            binding.plantNameTxtView.text = plant.name
            binding.plantTypeTxtView.text = plant.type
            binding.frequencyTxtView.text = "${plant.wateringFrequency.toString()} days"
            binding.plantedDateTxtView.text = dateFormat.format(Date(plant.plantedDate))
            binding.lastWateredDateTxtView.text = dateFormat.format(Date(plant.lastWateredDate))
            binding.nextWateringDateTxtView.text = dateFormat.format(getNextWateringDate(plant))
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun getNextWateringDate(plant: Plant): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = plant.lastWateredDate
        calendar.add(Calendar.DAY_OF_MONTH, plant.wateringFrequency)
        return Date(calendar.timeInMillis)
    }
}