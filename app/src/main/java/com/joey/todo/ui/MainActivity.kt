package com.joey.todo.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.joey.todo.R
import com.joey.todo.adapter.TaskAdapter
import com.joey.todo.room.Item
import com.joey.todo.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel
    private var tag = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        initUI()
    }

    private fun initUI() {
        val recyclerView = findViewById<RecyclerView>(R.id.tasks)
        val taskAdapter = TaskAdapter(this)
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        taskViewModel.allItems.observe(this, Observer { items ->
            items?.let { taskAdapter.setItems(it) }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val item = Item(
                    it.getStringExtra(NewTaskActivity.EXTRA_NAME),
                    it.getStringExtra(NewTaskActivity.EXTRA_DESC),
                    it.getStringExtra(NewTaskActivity.EXTRA_DATE)
                )

                taskViewModel.insert(item)
            }
        }
    }

    companion object {
        const val ADD_TASK_REQUEST_CODE = 1
        const val EDIT_TASK_REQUEST_CODE = 2
    }
}
