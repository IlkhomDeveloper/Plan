package com.example.plan.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.plan.data.entities.Plan

@Dao
interface PlanDao : BaseDao<Plan> {

    @Query("SELECT * FROM `Plan` WHERE timer > :time AND done = 0 AND canceled = 0 AND deleted = 0")
    fun getAllPlans(time: Long): List<Plan>

    @Query("SELECT * FROM `Plan` WHERE timer > :time AND done = 0 AND canceled = 0 AND deleted = 0")
    fun getPlansFuture(time: Long): List<Plan>

    @Query("SELECT * FROM `Plan` WHERE timer < :time ")
    fun getPlansPast(time: Long): List<Plan>

    @Query("SELECT * FROM `Plan` WHERE timer < :time and done = 1")
    fun getPlansPastNotToDo(time: Long): List<Plan>

    @Query("SELECT * FROM `Plan` WHERE deleted = 1")
    fun getPlansByDeleted(): List<Plan>

    @Query("SELECT * FROM `Plan` WHERE canceled = 1")
    fun getPlansByCanceled(): List<Plan>

    @Query("SELECT * FROM `Plan` WHERE done = 1")
    fun getPlansByDone(): List<Plan>
}