package com.example.plan.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.view.get
import com.example.plan.R
import com.example.plan.contracts.AddPlanContract
import com.example.plan.data.entities.Plan
import com.example.plan.presenters.AddPlanPresenter
import kotlinx.android.synthetic.main.activity_add.*
import java.util.*


class AddActivity : AppCompatActivity(), AddPlanContract.View, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var year = 0
    private var month = 0
    private var day = 0
    private var hour = 0
    private var minute = 0

    private var myYear = 0
    private var myMonth = 0
    private var myDay = 0
    private var myHour = 0
    private var myMinute = 0

    private lateinit var presenter: AddPlanPresenter
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        presenter = AddPlanPresenter(this)
        textView = findViewById(R.id.addPlanTime)
        addPlanTime.setOnClickListener {
            getTimeAndDate()
            DatePickerDialog(this, this, year, month, day).show()
        }
        addPlanAddButton.setOnClickListener { presenter.addButton() }
        addPriorityOne.setOnClickListener { presenter.selectPriority(R.id.addPriorityOne) }
        addPriorityTwo.setOnClickListener { presenter.selectPriority(R.id.addPriorityTwo) }
        addPriorityThree.setOnClickListener { presenter.selectPriority(R.id.addPriorityThree) }
        backMainPage.setOnClickListener { setResult(2); finish() }
    }


    private fun getTimeAndDate() {
        var calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }


    override fun setImage(pos: Int) {
        when (pos) {
            0 -> {
                (linear[0] as TextView).text = "!"
                (linear[1] as TextView).text = ""
                (linear[2] as TextView).text = ""
                linear[0].setBackgroundResource(R.drawable.ic_select)
                linear[1].setBackgroundResource(R.drawable.shape_priority_two)
                linear[2].setBackgroundResource(R.drawable.shape_priority_three)
            }
            1 -> {
                (linear[0] as TextView).text = ""
                (linear[1] as TextView).text = "!"
                (linear[2] as TextView).text = ""
                linear[1].setBackgroundResource(R.drawable.ic_select)
                linear[0].setBackgroundResource(R.drawable.shape_priority_one)
                linear[2].setBackgroundResource(R.drawable.shape_priority_three)
            }
            2 -> {
                (linear[0] as TextView).text = ""
                (linear[1] as TextView).text = ""
                (linear[2] as TextView).text = "!"
                linear[2].setBackgroundResource(R.drawable.ic_select)
                linear[0].setBackgroundResource(R.drawable.shape_priority_one)
                linear[1].setBackgroundResource(R.drawable.shape_priority_two)
            }
        }
    }

    override fun getPlanName(): String {
        return addPlanName.text.toString()
    }

    override fun getPlanInfo(): String {
        return addPlanInfo.text.toString()
    }

    override fun getPlanTimeString(): String {
        return addPlanTime.text.toString()
    }

    override fun getPlanTimeLong(): Long {
        return (myYear * 525600 + myMonth * 43200 + myDay * 1440 + myHour * 60 + myMinute).toLong()
    }

    override fun getPlanHashTag(): String {
        return "#${addPlanHashTag.text}"
    }

    override fun getPriority(): Int {
        var priority = -1
        priority = when {
            (linear[0] as TextView).text == "!" -> {
                R.color.colorPriorityOne
            }
            (linear[1] as TextView).text == "!" -> {
                R.color.colorPriorityTwo
            }
            (linear[2] as TextView).text == "!" -> {
                R.color.colorPriorityThree
            }
            else -> {
                R.color.colorPrimary
            }
        }
        return priority
    }

    override fun moveMainActivity(plan: Plan) {
        var intent = Intent()
        intent.putExtra("PLAN", plan)
        setResult(1, intent)
        finish()
    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        myYear = year
        myMonth = monthOfYear
        myDay = dayOfMonth

        getTimeAndDate()

        TimePickerDialog(this, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        addPlanTime.text = "$myYear/$myMonth/$myDay $myHour : $myMinute"
    }
}
