package com.example.plan.repositories

import android.os.Handler
import android.os.Looper
import com.example.plan.contracts.AllPlansContract
import com.example.plan.data.daos.PlanDao
import com.example.plan.data.databases.AppDatabase
import com.example.plan.data.entities.Plan
import java.util.concurrent.Executors

class AllPlansRepository(var currentTime:Long) : AllPlansContract.Model {
    private var executor = Executors.newSingleThreadExecutor()
    private var handle = Handler(Looper.getMainLooper())
    private val db = AppDatabase.getDatabase()
    private lateinit var planDao : PlanDao

    init {
        runOnWorkerThread {
            planDao = db.getPlans()
        }
    }
    override fun getAllPlans(block: (ArrayList<ArrayList<Plan>>) -> Unit) {
        runOnWorkerThread {
            var ls1 = planDao.getPlansFuture(currentTime)
            var ls2 = planDao.getPlansByDone()
            var ls3 = planDao.getPlansByCanceled()
            var ls4 = planDao.getPlansPast(currentTime)
            runOnUIThread {
                var allPlans = ArrayList<ArrayList<Plan>>()
                allPlans.add(ArrayList(ls1))
                allPlans.add(ArrayList(ls2))
                allPlans.add(ArrayList(ls3))
                allPlans.add(ArrayList(ls4))
                block(ArrayList(allPlans))
            }
        }
    }

    override fun delete(plan: Plan, block: (Int) -> Unit) {
        runOnWorkerThread {
            var result = planDao.delete(plan)
            runOnUIThread {
                block(result)
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