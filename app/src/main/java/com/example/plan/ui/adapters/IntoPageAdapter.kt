package com.example.plan.ui.adapters

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.plan.data.models.IntoData
import com.example.plan.ui.fragments.IntoFragment
import com.example.plan.utils.typleases.ItemClick
import com.example.plan.utils.extensions.putArguments

class IntoPageAdapter(private val intoData: List<IntoData>, activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var listener: ItemClick<IntoData>? = null
    override fun getItemCount(): Int = intoData.size

    override fun createFragment(position: Int) = IntoFragment().apply {
        listener.let { this.setOnclickButtonNextPage(it!!)}
    }.putArguments {
        putSerializable("DATA_PAGE", intoData[position])
    }

    fun setOnClickNextPage(f: ItemClick<IntoData>){
        listener = f
    }
}