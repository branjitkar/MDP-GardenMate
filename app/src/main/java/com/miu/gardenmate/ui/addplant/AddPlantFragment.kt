package com.miu.gardenmate.ui.addplant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.miu.gardenmate.data.entity.Plant
import com.miu.gardenmate.databinding.FragmentAddPlantBinding
import com.miu.gardenmate.snackBar
import com.miu.gardenmate.toast
import java.util.Calendar

class AddPlantFragment: Fragment() {
    private lateinit var binding: FragmentAddPlantBinding
    private lateinit var viewModel: AddPlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPlantBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AddPlantViewModel::class.java)

        binding.addPlantBtn.setOnClickListener {
            addPlant()
        }

        binding.addPlantBackButton.setOnClickListener {
            navigateBack()
        }

        return binding.root
    }

    private fun navigateBack() {
        binding.plantNameField.text = null
        binding.plantTypeField.text = null
        binding.wateringFrequency.text = null
        findNavController().navigateUp()
    }

    private fun addPlant() {
        if(binding.plantNameField.text.toString().isNullOrBlank()
            || binding.plantTypeField.text.toString().isNullOrBlank()
            || binding.wateringFrequency.text.toString().isNullOrBlank()) {
            context.snackBar(requireView(), "Fields cannot be blank.")
            return
        }
        try {
            viewModel.addPlant(Plant(0,
                binding.plantNameField.text.toString(),
                binding.plantTypeField.text.toString(),
                binding.wateringFrequency.text.toString().toInt(),
                Calendar.getInstance().timeInMillis ,
                Calendar.getInstance().timeInMillis ))

            context.snackBar(requireView(), "Plant Added Successfully.")

            navigateBack()
        } catch (ex: Exception) {
            Log.e("AddPlantFragment: addPlant", "Exception in adding new plant: ${ex.stackTrace}")
            context.snackBar(requireView(), "Plant Add Failed.")
        }

    }

}