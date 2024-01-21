package com.miu.gardenmate.ui.gardenlog

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.miu.gardenmate.data.entity.Plant
import com.miu.gardenmate.databinding.FragmentGardenLogBinding
import com.miu.gardenmate.snackBar
import com.miu.gardenmate.toast

class GardenLogFragment: Fragment() {
    private lateinit var binding: FragmentGardenLogBinding
    private lateinit var viewModel: GardenLogViewModel
    private lateinit var gardenLogAdapter: GardenLogAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGardenLogBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(GardenLogViewModel::class.java)

        binding.gardenRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        gardenLogAdapter = GardenLogAdapter(
            { plant:Plant -> onCardClicked(plant) },
            { plant:Plant -> onDeleteClicked(plant) },
            { plant:Plant -> onWaterClicked(plant) }
        )

        binding.gardenRecyclerView.adapter = gardenLogAdapter

        binding.addNewGardenLog.setOnClickListener {
            var addPlantAction = GardenLogFragmentDirections.actionGardenLogFragmentToAddPlantFragment()
            findNavController().navigate(addPlantAction)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allPlants.observe(viewLifecycleOwner) { plantList: List<Plant> ->
            gardenLogAdapter.setPlantList(plantList)
        }
    }

    private fun onCardClicked(plant: Plant) {
        var plantDetailAction = GardenLogFragmentDirections.actionGardenLogFragmentToPlantDetailFragment(plant.id)
        findNavController().navigate(plantDetailAction)
    }

    private fun onDeleteClicked(plant: Plant) {
        showDeleteConfirmationDialog(plant)
    }

    private fun onWaterClicked(plant: Plant) {
        showWaterConfirmationDialog(plant)
    }

    private fun showDeleteConfirmationDialog(plant: Plant) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Plant")
            .setMessage("Are you sure you want to delete this plant?")
            .setPositiveButton("Yes") { dialog, which ->
                viewModel.deletePlant(plant)
                context.snackBar(requireView(), "Plant deleted")
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showWaterConfirmationDialog(plant: Plant) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Log Plant Watering")
            .setMessage("Are you sure you want log watering for Plant?")
            .setPositiveButton("Yes") { dialog, which ->
                plant.lastWateredDate = System.currentTimeMillis();
                viewModel.updatePlant(plant)
                context.snackBar(requireView(), "Plant watering logged.")
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}


