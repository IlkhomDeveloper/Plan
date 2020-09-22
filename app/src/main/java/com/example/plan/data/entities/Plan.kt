package com.example.plan.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Plan(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    var name: String = "",
    var info: String = "",
    var timer: Long = 0,
    var time: String = "",
    var priority: Int = 0,
    var done: Int = 0,
    var canceled: Int = 0,
    var hashTag: String = "",
    var deleted: Int = 0,
    var visibility: Int = 0
) : Serializable