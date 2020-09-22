package com.example.plan.contracts

import com.example.plan.data.entities.Plan


interface AddPlanContract {
    interface Model {

    }

    interface View {
        fun setImage(pos: Int)
        fun getPlanName(): String
        fun getPlanInfo(): String
        fun getPlanTimeString(): String
        fun getPlanTimeLong(): Long
        fun getPlanHashTag(): String
        fun getPriority() : Int
        fun moveMainActivity(planner: Plan)
    }

    interface Presenter {
        fun addButton()
        fun selectPriority(id: Int)
    }
}