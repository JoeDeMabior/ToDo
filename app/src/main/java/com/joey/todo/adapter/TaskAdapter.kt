package com.joey.todo.adapter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.joey.todo.R
import com.joey.todo.room.Item
import com.joey.todo.ui.NewTaskActivity
import com.joey.todo.viewmodel.TaskViewModel
import es.dmoral.toasty.Toasty

class TaskAdapter internal constructor(context: Context) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private var items = ArrayList<Item>()
    var clickListener: TaskAdapterListener? = null

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

        holder.del.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                clickListener?.deleteTask(items[holder.adapterPosition])
            }
        }

        holder.edit.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                clickListener?.editTask(items[holder.adapterPosition])
            }
        }
    }

    fun setItems(items: List<Item>) {
        this.items = items as ArrayList<Item>
        notifyDataSetChanged()
    }

    /*
    fun removeTask(item: Item) {
        items.forEachIndexed { index, task ->
            if (task.id == item.id) {
                items.removeAt(index)
                notifyDataSetChanged()
                return@forEachIndexed
            }
        }
    }
    */

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
        val now: TextView = itemView.findViewById(R.id.moment)
        val del: ImageButton = itemView.findViewById(R.id.clear)
        val edit: ImageButton = itemView.findViewById(R.id.modify)
    }

    interface TaskAdapterListener {
        fun editTask(item: Item)
        fun deleteTask(item: Item)
    }

    fun alterTask(clickListener: TaskAdapterListener) {
        this.clickListener = clickListener
    }

}
