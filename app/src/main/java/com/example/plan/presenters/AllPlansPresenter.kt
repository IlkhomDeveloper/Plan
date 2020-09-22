package com.example.plan.presenters

import com.example.plan.contracts.AllPlansContract
import com.example.plan.data.entities.Plan

class AllPlansPresenter(var repository:AllPlansContract.Model, var view:AllPlansContract.View) : AllPlansContract.Presenter{

    init {
        repository.getAllPlans { view.setList(it) }
    }
    override fun getAllPlans(block: (ArrayList<ArrayList<Plan>>) -> Unit) {
        repository.getAllPlans {
            block(it)
        }
    }

    override fun cancel(plan: Plan) {

    }

    override fun update(plan: Plan) {

    }

    override fun edit(plan: Plan) {

    }

    override fun delete(plan: Plan) {
        repository.delete(plan) {
            if (it > 0){
                view
            }
        }
    }
}