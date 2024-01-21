package com.miu.gardenmate.ui.gardenlog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.miu.gardenmate.R
import com.miu.gardenmate.data.entity.Plant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GardenLogAdapter(
    private val onCardClicked: (Plant) -> Unit,
    private val onDeleteClicked: (Plant) -> Unit,
    private val onWaterClicked: (Plant) -> Unit
): RecyclerView.Adapter<GardenLogAdapter.GardenLogViewHolder>() {
    private var plantList = listOf<Plant>()

    fun setPlantList(value: List<Plant>) {
        this.plantList = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenLogViewHolder {
        val plantListView = LayoutInflater.from(parent.context).inflate(R.layout.plant_list_view, parent, false)
        return GardenLogViewHolder(plantListView)
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    override fun onBindViewHolder(holder: GardenLogViewHolder, position: Int) {
        val plant = plantList[position]
        holder.plantNameTxtView.text = plant?.name
        holder.plantTypeTxtView.text = plant?.type
        var daysUntilNextWatering = getDaysUntilNextWatering(plant)
        holder.plantNextWateringDateView.text = daysUntilNextWatering
        if(daysUntilNextWatering == "N/A" || daysUntilNextWatering.toInt() < 0)
            holder.plantNextWateringDateView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.days_to_water_urgent))
        else if(daysUntilNextWatering.toInt() > 1)
            holder.plantNextWateringDateView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.days_to_water_safe))
        else
            holder.plantNextWateringDateView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.days_to_water_normal))

        holder.itemView.setOnClickListener {
            onCardClicked(plant)
        }
        holder.plantDeleteBtn.setOnClickListener {
            onDeleteClicked(plant)
        }
        holder.logWateringBtn.setOnClickListener {
            onWaterClicked(plant)
        }
    }

    private fun getDaysUntilNextWatering(plant: Plant?): String {
        return plant?.let {
            val calendarLastWatered = Calendar.getInstance().apply {
                timeInMillis = plant.lastWateredDate
            }
            val calendarToday = Calendar.getInstance()
            val daysSinceLastWatered = ((calendarToday.timeInMillis - calendarLastWatered.timeInMillis) / (24 * 60 * 60 * 1000)).toInt()
            val daysUntilNextWatering = plant.wateringFrequency - daysSinceLastWatered
            daysUntilNextWatering.toString()
        } ?: "N/A"
    }

    class GardenLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantAvatar: ImageButton = itemView.findViewById(R.id.plantAvatar)
        val plantNameTxtView: TextView = itemView.findViewById(R.id.plantNameView)
        val plantTypeTxtView: TextView = itemView.findViewById(R.id.plantTypeView)
        val plantNextWateringDateView: TextView = itemView.findViewById(R.id.plantNextWateringDateView)
        val plantDeleteBtn: ImageButton = itemView.findViewById(R.id.deletePlantBtn)
        val logWateringBtn: ImageButton = itemView.findViewById(R.id.logWateringBtn)
    }
}