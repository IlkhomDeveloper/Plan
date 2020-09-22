package com.example.plan.presenters


import com.example.plan.R
import com.example.plan.contracts.AddPlanContract
import com.example.plan.data.entities.Plan

class AddPlanPresenter(private val view: AddPlanContract.View) : AddPlanContract.Presenter {

    override fun addButton() {
        var name = view.getPlanName()
        var info = view.getPlanInfo()
        var timeString = view.getPlanTimeString()
        var timeLong = view.getPlanTimeLong()
        var hashTag = view.getPlanHashTag()
        var priority = view.getPriority()
        var plan = Plan(
            name = name,
            info = info,
            timer = timeLong,
            time = timeString,
            priority = priority,
            hashTag = hashTag
        )
        view.moveMainActivity(plan)
    }

    override fun selectPriority(id: Int) {
        when (id) {
            R.id.addPriorityOne -> {
                view.setImage(0)
            }
            R.id.addPriorityTwo -> {
                view.setImage(1)
            }
            R.id.addPriorityThree -> {
                view.setImage(2)
            }
        }
    }
}