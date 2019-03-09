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
import com.joey.todo.room.Item
import com.joey.todo.task_interface.AlterTaskListener
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
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        initUI()
    }

    private fun initUI() {
        val recyclerView = findViewById<RecyclerView>(R.id.tasks)
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        taskViewModel.allItems.observe(this, Observer { items ->
            items?.let { taskAdapter.setItems(it) }
        })

        object : TaskAdapter.TaskAdapterListener {
            override fun editTask(item: Item) {
                val intent = Intent(this@MainActivity, NewTaskActivity::class.java)
                startActivityForResult(intent, EDIT_TASK_REQUEST_CODE)
            }

            override fun deleteTask(item: Item) {
                taskAdapter.removeTask(item)
            }

        }
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

        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val item = Item(
                    it.getStringExtra(NewTaskActivity.EXTRA_NAME),
                    it.getStringExtra(NewTaskActivity.EXTRA_DESC),
                    it.getStringExtra(NewTaskActivity.EXTRA_DATE)
                )

                taskViewModel.insert(item)
            }
            Toasty.success(this, "Task saved successfully.", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val item = Item(
                    it.getStringExtra(NewTaskActivity.EXTRA_NAME),
                    it.getStringExtra(NewTaskActivity.EXTRA_DESC),
                    it.getStringExtra(NewTaskActivity.EXTRA_DATE)
                )

                taskViewModel.update(item)
            }
            Toasty.success(this, "Task updated successfully.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ADD_TASK_REQUEST_CODE = 1
        const val EDIT_TASK_REQUEST_CODE = 2
    }
}
