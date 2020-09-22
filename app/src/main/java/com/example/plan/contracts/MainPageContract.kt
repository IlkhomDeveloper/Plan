package com.example.plan.contracts

import com.example.plan.data.entities.Plan

interface MainPageContract {
    interface Model{
        fun getAllPlans(data : (ArrayList<Plan>) -> Unit)
        fun addPlan(plan: Plan, block : (Plan) -> Unit)
        fun cancelPlan(plan: Plan, block : (Plan) -> Unit)
        fun updatePlan(plan: Plan, block : (Plan) -> Unit)
        fun donePlan(plan: Plan, block : (Plan) -> Unit)
    }
    interface View{
        fun loadData(ls:List<Plan>)
        fun homeActivity()
        fun moveAddPlanActivity()
        fun moveBasketActivity()
        fun moveAllPlansToSeeActivity()
        fun moveEditPlansActivity()
        fun moveHistoryPlansActivity()
        fun moveShareAppActivity()
        fun moveUseAppConditionalActivity()
        fun moveAppInstructionActivity()
        fun visibilityJustDoIt()
        fun getPlanLastCreated() : Plan
        fun moveSettingsActivity()
        fun insertAdapter(plan: Plan)
        fun cancelAdapter(plan: Plan)
        fun deleteAdapter(plan: Plan)
        fun updateAdapter(index: Int,plan: Plan)
        fun doneAdapter(plan: Plan)
        fun showDialog(index: Int,plan: Plan)
    }
    interface Presenter{
        fun moveAnotherActivity(id:Int)
        fun addPlan(plan: Plan)
        fun cancelPlan(plan: Plan)
        fun updatePlan(index:Int,plan: Plan)
        fun donePlan(plan: Plan)
        fun clickDialog(index: Int,plan: Plan)
    }
}