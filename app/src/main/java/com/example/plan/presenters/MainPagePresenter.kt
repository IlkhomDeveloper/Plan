package com.example.plan.presenters

import com.example.plan.R
import com.example.plan.contracts.MainPageContract
import com.example.plan.data.entities.Plan

class MainPagePresenter(private val model: MainPageContract.Model,private val view:MainPageContract.View) : MainPageContract.Presenter {

    init {
        model.getAllPlans { view.loadData(it) }
    }
    override fun moveAnotherActivity(id: Int) {
        when (id) {
            R.id.homeMainPage ->{
                view.homeActivity()
            }
            R.id.conditionals -> {
                view.moveUseAppConditionalActivity()
            }
            R.id.basketPlans -> {
                view.moveBasketActivity()
            }
            R.id.share ->{
                view.moveShareAppActivity()
            }
            R.id.settings ->{
                view.moveSettingsActivity()
            }
            R.id.instructions -> {
                view.moveAppInstructionActivity()
            }
            R.id.conditionals ->{
                view.moveUseAppConditionalActivity()
            }
        }
    }

    override fun addPlan(plan: Plan) {
        model.addPlan(plan){
            if (it.id > 0){
                view.insertAdapter(plan)
            }
        }
    }

    override fun cancelPlan(plan: Plan) {
        model.cancelPlan(plan){
            view.cancelAdapter(plan)
        }
    }

    override fun updatePlan(index: Int, plan: Plan) {
        model.updatePlan(plan){
            view.updateAdapter(index,plan)
        }
    }

    override fun donePlan(plan: Plan) {
        model.donePlan(plan){
            view.doneAdapter(plan)
        }
    }

    override fun clickDialog(index: Int, plan: Plan) {
        view.showDialog(index,plan)
    }
}