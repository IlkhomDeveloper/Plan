package com.example.plan.data.local

import android.content.Context
import com.example.plan.utils.extensions.BooleanPreferenceDefTrue

class LocalStorage private constructor(context: Context){
    companion object{
        lateinit var instance : LocalStorage; private set
        fun init(context: Context){
            instance =
                LocalStorage(context)
        }
    }
    private val pref = context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
    var isFirst : Boolean by BooleanPreferenceDefTrue(pref)
}