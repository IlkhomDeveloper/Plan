package com.example.plan.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.plan.R
import com.example.plan.data.models.IntoData
import com.example.plan.utils.typleases.ItemClick
import kotlinx.android.synthetic.main.into_screen.*

class IntoFragment : Fragment(R.layout.into_screen) {
    private var listener:ItemClick<IntoData> ?= null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = requireArguments()
        var intoData = bundle.getSerializable("DATA_PAGE") as IntoData
        intoTitle.text = intoData.title
        intoTextInfo.text = intoData.info
        intoImage.setImageResource(intoData.picture)
        intoNextPage.setOnClickListener { listener?.invoke(intoData) }
    }
    fun setOnclickButtonNextPage(f:ItemClick<IntoData>){
        listener = f
    }
}