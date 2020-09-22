package com.example.plan.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.plan.R
import com.example.plan.data.local.LocalStorage
import com.example.plan.data.models.intoDataInfo
import com.example.plan.ui.adapters.IntoPageAdapter
import kotlinx.android.synthetic.main.activity_into.*

class IntoActivity : AppCompatActivity() {

    private lateinit var adapter: IntoPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_into)
        LocalStorage.init(this)
        var isFirst = LocalStorage.instance.isFirst
        if (isFirst) {
            adapter = IntoPageAdapter(intoDataInfo, this)
            pagerInto.adapter = adapter
            adapter.setOnClickNextPage {
                if (pagerInto.currentItem == intoDataInfo.size - 1) {
                    startActivity(Intent(this, MainActivity::class.java))
                    LocalStorage.instance.isFirst = false
                    finish()
                }
                pagerInto.setCurrentItem(pagerInto.currentItem + 1, true)
            }
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
