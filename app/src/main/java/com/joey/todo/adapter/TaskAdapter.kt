package com.joey.todo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joey.todo.R
import com.joey.todo.room.Item

class TaskAdapter internal constructor(context: Context): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private var items: List<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.model, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = items[position]
        holder.txt.text = currentItem.title
        holder.desc.text = currentItem.description
        holder.now.text = currentItem.deadline
    }

    fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
        val now: TextView = itemView.findViewById(R.id.moment)
    }
}
