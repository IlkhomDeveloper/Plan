package com.example.plan.repositories

import android.os.Handler
import android.os.Looper
import com.example.plan.app.App
import com.example.plan.contracts.MainPageContract
import com.example.plan.data.daos.PlanDao
import com.example.plan.data.databases.AppDatabase
import com.example.plan.data.entities.Plan
import java.util.concurrent.Executors

class MainPageRepository(private val currentTime:Long) : MainPageContract.Model {
    private var executor = Executors.newSingleThreadExecutor()
    private var handle = Handler(Looper.getMainLooper())
    private val db = AppDatabase.getDatabase()
    private lateinit var planDao : PlanDao

    init {
        runOnWorkerThread {
            planDao = db.getPlans()
        }
    }
    override fun getAllPlans(data: (ArrayList<Plan>) -> Unit) {
        runOnWorkerThread {
            var ls = planDao.getAllPlans(currentTime)
            runOnUIThread {
                data(ArrayList(ls))
            }
        }
    }

    override fun addPlan(plan: Plan, block: (Plan) -> Unit) {
        runOnWorkerThread {
            var id = planDao.insert(plan)
            runOnUIThread {
               plan.id = id
                block(plan)
            }
        }
    }

    override fun cancelPlan(plan: Plan, block: (Plan) -> Unit) {
        runOnWorkerThread {
            plan.canceled = 1
            planDao.update(plan)
            runOnUIThread {
                block(plan)
            }
        }
    }

    override fun updatePlan(plan: Plan, block: (Plan) -> Unit) {
        runOnWorkerThread {
            var id = planDao.update(plan)
            runOnUIThread {
                plan.id = id.toLong()
                block(plan)
            }
        }
    }

    override fun donePlan(plan: Plan, block: (Plan) -> Unit) {
        runOnWorkerThread {
            plan.done = 1
            planDao.update(plan)
            runOnUIThread {
                block(plan)
            }
        }
    }

    private fun runOnWorkerThread(f: () -> Unit) {
        executor.execute { f() }
    }

    private fun runOnUIThread(f: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            f()
        } else {
            handle.post { f() }
        }
    }
}