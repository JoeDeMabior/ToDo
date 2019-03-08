package com.joey.todo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.joey.todo.R
import com.joey.todo.model.Item
import com.joey.todo.my_interface.ItemRowListener

class ItemAdapter(context: Context, items: MutableList<Item>) : BaseAdapter() {
    private val inflater = LayoutInflater.from(context)
    private var itemList = items
    private var rowListener = context as ItemRowListener

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val objectId: String = itemList[position].id as String
        val itemText: String = itemList[position].text as String
        val done: Boolean = itemList[position].done as Boolean

        val view: View
        val holder: ListRowHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.model, parent, false)
            holder = ListRowHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ListRowHolder
        }

        holder.label.text = itemText
        holder.isDone.isChecked = done

        holder.isDone.setOnClickListener {
            rowListener.modifyItemState(objectId, !done)
        }

        holder.del.setOnClickListener {
            rowListener.onItemDelete(objectId)
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    private class ListRowHolder(row: View?) {
        val label: TextView = row!!.findViewById(R.id.text)
        val isDone: CheckBox = row!!.findViewById(R.id.status)
        val del: ImageButton = row!!.findViewById(R.id.clear)
    }

}
