package com.miu.gardenmate.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val type: String,
    val wateringFrequency: Int,
    val plantedDate: Long,
    var lastWateredDate: Long
): Serializable