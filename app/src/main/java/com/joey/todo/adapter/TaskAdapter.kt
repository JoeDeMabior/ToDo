package com.joey.todo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joey.todo.R
import com.joey.todo.room.Task

class TaskAdapter internal constructor(context: Context) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private var items = ArrayList<Task>()
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

    fun setItems(tasks: List<Task>) {
        this.items = tasks as ArrayList<Task>
        notifyDataSetChanged()
    }

    /*
    fun removeTask(item: Task) {
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
        fun editTask(task: Task)
        fun deleteTask(task: Task)
    }

    fun alterTask(clickListener: TaskAdapterListener) {
        this.clickListener = clickListener
    }

}
