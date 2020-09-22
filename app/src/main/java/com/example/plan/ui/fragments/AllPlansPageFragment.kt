package com.example.plan.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan.R
import com.example.plan.data.entities.Plan
import com.example.plan.data.models.ALL_ACTIVITY
import com.example.plan.data.models.Plans
import com.example.plan.ui.adapters.MainPageAdapter
import kotlinx.android.synthetic.main.all_plans_page_fragment.*

class AllPlansPageFragment : Fragment(R.layout.all_plans_page_fragment) {
    var listener: ((Plan) -> Unit)? = null
    private lateinit var adapter: MainPageAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = requireArguments()
        var list = bundle.getSerializable("PLANS") as Plans

        allPlansPageRecycle.layoutManager = LinearLayoutManager(context)
        adapter = MainPageAdapter(ALL_ACTIVITY)
        adapter.submitList(list.plans)
        allPlansPageRecycle.adapter = adapter
        adapter.setOnClick { listener?.invoke(it) }
        adapter.setOnClickCancel { listener?.invoke(it) }
        adapter.setOnClickDone { listener?.invoke(it) }
        adapter.setOnClickUpdate { listener?.invoke(it) }
        adapter.setOnClickDelete { listener?.invoke(it) }
        adapter.setOnClickClone { listener?.invoke(it) }
    }

}