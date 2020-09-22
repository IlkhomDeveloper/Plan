package com.example.plan.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.plan.R
import com.example.plan.contracts.AllPlansContract
import com.example.plan.data.entities.Plan
import com.example.plan.presenters.AllPlansPresenter
import com.example.plan.repositories.AllPlansRepository
import com.example.plan.ui.adapters.AllPlansPageAdapter
import com.example.plan.utils.extensions.toLongDate
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_all_plans.*
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog.view.*
import java.util.*
import kotlin.collections.ArrayList

class AllPlansActivity : AppCompatActivity(), AllPlansContract.View,TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
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

    private var list = ArrayList<ArrayList<Plan>>()
    private lateinit var adapter: AllPlansPageAdapter
    private lateinit var presenter: AllPlansPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_plans)
        presenter = AllPlansPresenter(AllPlansRepository(getCurrentTimeAndDateToLong()), this)
        allPlansPageBack.setOnClickListener { finish() }
        val view =  LayoutInflater.from(this).inflate(R.layout.dialog,null,false)
        view.dialogTime.setOnClickListener {
            getTimeAndDate()
            DatePickerDialog(this, this, year, month, day).show()
        }
        presenter.getAllPlans {
            adapter = AllPlansPageAdapter(it, this,this::update,this::delete)

            allPlansViewPager.adapter = adapter
            TabLayoutMediator(allPlansPageTabLayout, allPlansViewPager) { tab, position ->
                if (position == 0) {
                    tab.text = "Upcoming"
                }
                if (position == 1) {
                    tab.text = "Done"
                }
                if (position == 2) {
                    tab.text = "Cancel"
                }
                if (position == 3) {
                    tab.text = "Old"
                }
            }.attach()
        }
    }

    private fun update(item:Plan) {
        presenter.update(item)
    }
    private fun delete(item:Plan) {
        presenter.delete(item)
    }

    override fun setList(ls: ArrayList<ArrayList<Plan>>) {
        list.addAll(ls)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun showDialog(plan: Plan) {
        val view =  LayoutInflater.from(this).inflate(R.layout.dialog,null,false)
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Update plan").setView(view).setNegativeButton("cancel", null).setPositiveButton("update"){_,_->
            plan.name = view.dialogName.text.toString()
            plan.info = view.dialogInfo.text.toString()
            plan.hashTag = view.dialogHashTag.text.toString()
            plan.time = view.dialogTime.text.toString()
            plan.timer = view.dialogTime.text.toString().toLongDate("yyyy/MM/dd HH:mm")
            presenter.update(plan)
        }.create().show()
    }

    private fun getCurrentTimeAndDateToLong() : Long {
        var calendar = Calendar.getInstance()
        var myYear = calendar.get(Calendar.YEAR)
        var myMonth = calendar.get(Calendar.MONTH)
        var myDay = calendar.get(Calendar.DAY_OF_MONTH)
        var myHour = calendar.get(Calendar.HOUR)
        var myMinute = calendar.get(Calendar.MINUTE)
        return (myYear * 525600 + myMonth * 43200 + myDay * 1440 + myHour * 60 + myMinute).toLong()
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
        dialogTime.text = "$myYear/$myMonth/$myDay $myHour : $myMinute"
    }
    private fun getTimeAndDate() {
        var calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

}
