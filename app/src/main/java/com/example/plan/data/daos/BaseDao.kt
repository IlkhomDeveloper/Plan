package com.example.plan.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert
    fun insert(data: T): Long

    @Insert
    fun insertAll(data: List<T>): List<Long>

    @Delete
    fun delete(data: T): Int

    @Delete
    fun deleteAll(data: List<T>): Int

    @Update
    fun update(data: T): Int

    @Update
    fun updateAll(data: List<T>): Int
}