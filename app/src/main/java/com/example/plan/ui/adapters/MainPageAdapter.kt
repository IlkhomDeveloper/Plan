package com.example.plan.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.*
import com.example.plan.R
import com.example.plan.data.entities.Plan
import com.example.plan.data.models.MAIN_ACTIVITY
import com.example.plan.utils.extensions.inflate
import kotlinx.android.synthetic.main.main_activity_item.view.*


class MainPageAdapter(private val selected:Int) : RecyclerView.Adapter<MainPageAdapter.ViewHolder>() {

    private var listener: ((Plan) -> Unit)? = null
    private var listenerCancel: ((Plan) -> Unit)? = null
    private var listenerUpdate: ((Plan) -> Unit)? = null
    private var listenerDone: ((Plan) -> Unit)? = null
    private var listenerClone: ((Plan) -> Unit)? = null
    private var listenerDelete: ((Plan) -> Unit)? = null

    private var callback = object : SortedListAdapterCallback<Plan>(this) {

        override fun areItemsTheSame(item1: Plan, item2: Plan) = item1.name == item2.name

        override fun compare(o1: Plan, o2: Plan) = o1.timer.compareTo(o2.timer)

        override fun areContentsTheSame(oldItem: Plan, newItem: Plan) = oldItem == newItem

    }

    private var sortedList = SortedList(Plan::class.java, callback)


    fun submitList(list: List<Plan>) {
        sortedList.beginBatchedUpdates()
        sortedList.addAll(list)
        sortedList.endBatchedUpdates()
    }

    fun insert(plan: Plan) {
        sortedList.add(plan)
    }
    fun delete(plan: Plan){
        sortedList.remove(plan)
    }
    fun update(index:Int,plan: Plan){
        sortedList.updateItemAt(index,plan)
       /* sortedList.removeItemAt(index)
        sortedList.add(plan)*/
    }
    fun done(plan: Plan){
        sortedList.remove(plan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.main_activity_item))

    override fun getItemCount(): Int = sortedList.size()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            itemView.apply {
                val data = sortedList[adapterPosition]
                planName.text = data.name
                planInfo.text = data.info
                planTime.text = data.time
                planHashTag.text = data.hashTag
                planPriorityLeft.setBackgroundResource(data.priority)
                planPriorityRight.setBackgroundResource(data.priority)
                if (data.visibility == 1) {
                    justDoIt.visibility = View.VISIBLE
                } else {
                    justDoIt.visibility = View.INVISIBLE
                }

                itemCard.setOnLongClickListener {
                    itemCard.setOnLongClickListener {
                        var popupMenu = PopupMenu(context, it)
                        if (selected == MAIN_ACTIVITY){
                            popupMenu.inflate(R.menu.main_item_menu)
                        }
                        else{
                            popupMenu.inflate(R.menu.all_item_menu)
                        }
                        popupMenu.setOnMenuItemClickListener { m ->
                            when (m.itemId) {
                                R.id.menuUpdate -> {
                                    listenerUpdate?.invoke(data)
                                }
                                R.id.menuDelete ->{
                                    listenerCancel?.invoke(data)
                                }
                                R.id.menuDone ->{
                                    listenerDone?.invoke(data)
                                }
                                R.id.allMenuClone ->{
                                    listenerClone?.invoke(data)
                                }
                                R.id.allMenuDelete ->{
                                    listenerDelete?.invoke(data)
                                }
                            }
                            true
                        }
                        popupMenu.show()
                        return@setOnLongClickListener true
                    }


                    listener?.invoke(data)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    fun setOnClick(f: (Plan) -> Unit) {
        listener = f
    }

    fun setOnClickCancel(f: (Plan) -> Unit) {
        listenerCancel = f
    }

    fun setOnClickUpdate(f: (Plan) -> Unit) {
        listenerUpdate = f
    }

    fun setOnClickDone(f: (Plan) -> Unit) {
        listenerDone = f
    }

    fun setOnClickDelete(f: (Plan) -> Unit) {
        listenerDelete = f
    }

    fun setOnClickClone(f: (Plan) -> Unit) {
        listenerClone = f
    }
}