package com.example.plan.contracts

import com.example.plan.data.entities.Plan

interface AllPlansContract {
    interface Model {
        fun getAllPlans(block: (ArrayList<ArrayList<Plan>>) -> Unit)
        fun delete(plan: Plan,block : (Int) -> Unit)
    }

    interface View {
        fun setList(ls:ArrayList<ArrayList<Plan>>)
        fun showDialog(plan: Plan)
    }

    interface Presenter {
        fun getAllPlans(block: (ArrayList<ArrayList<Plan>>) -> Unit)
        fun cancel(plan:Plan)
        fun update(plan: Plan)
        fun edit(plan: Plan)
        fun delete(plan: Plan)
    }
}