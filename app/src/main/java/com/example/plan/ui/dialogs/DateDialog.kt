package com.example.plan.ui.dialogs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TimePicker

class DateDialog(var context: Context) : TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
    private var myYear = 0
    private var myMonth = 0
    private var myDay = 0
    private var myHour = 0
    private var myMinute = 0

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myYear = year
        myMonth = month
        myDay = dayOfMonth
    }

    fun getSelectedTime() : String{
        return "\"$myYear/$myMonth/$myDay $myHour:$myMinute\""
    }
}