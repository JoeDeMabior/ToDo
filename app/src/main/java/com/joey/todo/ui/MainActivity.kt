package com.joey.todo.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joey.todo.R
import com.joey.todo.adapter.TaskAdapter
import com.joey.todo.room.Task
import com.joey.todo.viewmodel.TaskViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskAdapter = TaskAdapter(this)

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK)
        }

        initUI()
    }

    private fun initUI() {
        val recyclerView = findViewById<RecyclerView>(R.id.tasks)
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        taskViewModel.allTasks.observe(this, Observer {
            taskAdapter.setItems(it)
        })

        taskAdapter.alterTask(object : TaskAdapter.TaskAdapterListener {
            override fun editTask(task: Task) {
                val intent = Intent(this@MainActivity, NewTaskActivity::class.java)
                intent.putExtra(NewTaskActivity.EXTRA_ID, task.id)
                intent.putExtra(NewTaskActivity.EXTRA_NAME, task.title)
                intent.putExtra(NewTaskActivity.EXTRA_DESC, task.description)
                intent.putExtra(NewTaskActivity.EXTRA_DATE, task.deadline)
                startActivityForResult(intent, EDIT_TASK)
            }

            override fun deleteTask(task: Task) {
                AlertDialog.Builder(this@MainActivity)
                    .setMessage("Are you sure you want to delete ${task.title}?")
                    .setPositiveButton("OK") { _, _ ->
                        taskViewModel.delete(task)
                        Toasty.info(this@MainActivity, "${task.title} deleted.", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.delete_all) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to delete all tasks?")
            builder.setPositiveButton("OK") { _, _ ->
                taskViewModel.deleteAll()
                Toasty.success(this, "All tasks deleted.", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (requestCode == ADD_TASK && resultCode == Activity.RESULT_OK) {
                val task = Task(
                    data.getStringExtra(NewTaskActivity.EXTRA_NAME),
                    data.getStringExtra(NewTaskActivity.EXTRA_DESC),
                    data.getStringExtra(NewTaskActivity.EXTRA_DATE)
                )
                taskViewModel.insert(task)
                Toasty.success(this, "Task saved successfully.", Toast.LENGTH_SHORT).show()
            } else if (requestCode == EDIT_TASK && resultCode == Activity.RESULT_OK) {
                val taskId = data.getIntExtra(NewTaskActivity.EXTRA_ID, -1)
                if (taskId == -1) {
                    Toasty.error(this, "Error, could not update task.", Toast.LENGTH_SHORT).show()
                    return
                }

                val task = Task(
                    data.getStringExtra(NewTaskActivity.EXTRA_NAME),
                    data.getStringExtra(NewTaskActivity.EXTRA_DESC),
                    data.getStringExtra(NewTaskActivity.EXTRA_DATE)
                )
                task.id = taskId
                taskViewModel.update(task)
                Toasty.success(this, "Task updated successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val ADD_TASK = 1
        const val EDIT_TASK = 2
    }
}
