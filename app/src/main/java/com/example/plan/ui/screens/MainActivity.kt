package com.example.plan.ui.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan.R
import com.example.plan.contracts.MainPageContract
import com.example.plan.data.entities.Plan
import com.example.plan.data.models.MAIN_ACTIVITY
import com.example.plan.presenters.MainPagePresenter
import com.example.plan.repositories.MainPageRepository
import com.example.plan.ui.adapters.MainPageAdapter
import com.example.plan.utils.extensions.toLongDate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.main_activity_item.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), MainPageContract.View, TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var plan: Plan
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
    private var list = ArrayList<Plan>()
    private lateinit var presenter: MainPagePresenter
    private var adapter = MainPageAdapter(MAIN_ACTIVITY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadInfo()
        loadNavigation()

        adapter.setOnClickUpdate {
            var index = list.indexOf(it)
            presenter.clickDialog(index,it)
        }
        adapter.setOnClickCancel { presenter.cancelPlan(it) }
        adapter.setOnClickDone { presenter.donePlan(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1) {
            plan = data?.getSerializableExtra("PLAN") as Plan
            presenter.addPlan(plan)
        }
        if (resultCode == 2) {
            titleMainPage.text = "Note ToDo"
        }
    }

    override fun loadData(ls: List<Plan>) {
        adapter.submitList(ls)
        list.addAll(ls)
    }

    override fun homeActivity() {
        startActivity(Intent(this,this::class.java))
        finish()
    }

    override fun moveAddPlanActivity() {
        startActivityForResult(Intent(this, AddActivity::class.java), 1)
    }

    override fun moveBasketActivity() {
        startActivity(Intent(this, AllPlansActivity::class.java))
    }

    override fun moveAllPlansToSeeActivity() {
        startActivity(Intent(this, AllPlansActivity::class.java))
    }

    override fun moveEditPlansActivity() {
        startActivity(Intent(this, EditPlansActivity::class.java))
    }

    override fun moveHistoryPlansActivity() {
        startActivity(Intent(this, HistoryActivity::class.java))
    }

    override fun moveShareAppActivity() {
        sendApkFile(this)
    }

    override fun moveUseAppConditionalActivity() {
        loadDialog("Use app")
    }

    override fun moveAppInstructionActivity() {
        loadDialog("Instruction")
    }

    override fun visibilityJustDoIt() {
        justDoIt.visibility = View.VISIBLE
    }

    override fun getPlanLastCreated(): Plan {
        return plan
    }

    override fun moveSettingsActivity() {
        loadDialog("Settings")
    }

    override fun insertAdapter(plan: Plan) {
        adapter.insert(plan)
    }

    override fun cancelAdapter(plan: Plan) {
        adapter.delete(plan)
    }

    override fun deleteAdapter(plan: Plan) {
        adapter.delete(plan)
    }

    override fun updateAdapter(index: Int, plan: Plan) {
        adapter.update(index,plan)
    }

    override fun doneAdapter(plan: Plan) {
        adapter.done(plan)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun showDialog(index: Int, plan: Plan) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog, null, false)
        view.dialogTime.setOnClickListener {
            getTimeAndDate()
            DatePickerDialog(this, this, year, month, day).show()
        }
        var dialog = AlertDialog.Builder(this)
        dialog
            .setTitle("Update plan").setView(view)
            .setNegativeButton("cancel", null)
            .setPositiveButton("update") { _, _ ->
                plan.name = view.dialogName.text.toString()+""
                plan.info = view.dialogInfo.text.toString()+""
                plan.hashTag = view.dialogHashTag.text.toString()+""
                plan.time = "$myYear/$myMonth/$myDay $myHour : $myMinute"
                plan.timer = (myYear * 525600 + myMonth * 43200 + myDay * 1440 + myHour * 60 + myMinute).toLong()
                presenter.updatePlan(index,plan)
            }
            .create()
            .show()
    }


    private fun loadNavigation() {
        navigation.setNavigationItemSelectedListener {
            presenter.moveAnotherActivity(it.itemId)
            drawerLayout.closeDrawer(GravityCompat.START, true)
            true
        }
    }

    private fun loadInfo() {
        presenter = MainPagePresenter(MainPageRepository(getCurrentTimeAndDateToLong()), this)
        recycleMainPage.adapter = adapter
        recycleMainPage.layoutManager = LinearLayoutManager(this)
        addMainPageButton.setOnClickListener {
            startActivityForResult(Intent(this, AddActivity::class.java), 1)
        }
        setSupportActionBar(findViewById(R.id.toolbar))
        title = ""
        mainPageMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START, true) }
    }

    private fun loadDialog(message: String) {
        val dialog = AlertDialog.Builder(this)
        dialog
            .setTitle(message)
            .setMessage("it may be complete next week and you will enjoy it")
            .setPositiveButton("Ok", null).create().show()
    }

    private fun getCurrentTimeAndDateToLong(): Long {
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

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val v = LayoutInflater.from(this).inflate(R.layout.dialog, null, false)
        myHour = hourOfDay
        myMinute = minute
        v.dialogTime.text = "$myYear/$myMonth/$myDay $myHour : $myMinute"
    }

    private fun getTimeAndDate() {
        var calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    private fun sendApkFile(context: Context) {
        try {
            val pm = context.packageManager
            val ai = pm.getApplicationInfo(context.packageName, 0)
            val srcFile = File(ai.publicSourceDir)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            val uri: Uri = FileProvider.getUriForFile(this, context.packageName, srcFile)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            context.grantUriPermission(
                context.packageManager.toString(),
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
