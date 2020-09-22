package com.example.plan.ui.adapters

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.plan.data.entities.Plan
import com.example.plan.data.models.Plans
import com.example.plan.ui.fragments.AllPlansPageFragment
import com.example.plan.utils.extensions.putArguments

class AllPlansPageAdapter(var list : ArrayList<ArrayList<Plan>>,activity: FragmentActivity, f: (Plan) -> Unit,l: (Plan) -> Unit) : FragmentStateAdapter(activity) {
    private var listenerPageAdapterUpdate: ((Plan) -> Unit) = f
    private var listenerPageAdapterDelete: ((Plan) -> Unit) = l


    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int) = AllPlansPageFragment().apply {
            this@apply.listener = listenerPageAdapterUpdate
            this@apply.listener = listenerPageAdapterDelete
    }.putArguments {
        var plansList = Plans(list[position])
        putSerializable("PLANS", plansList)
    }
}