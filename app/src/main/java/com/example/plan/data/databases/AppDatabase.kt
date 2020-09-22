package com.example.plan.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plan.app.App
import com.example.plan.data.daos.PlanDao
import com.example.plan.data.entities.Plan

@Database(entities = [Plan::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPlans(): PlanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase() : AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(App.instance,AppDatabase::class.java,"database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}