package com.joey.todo.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.joey.todo.R
import es.dmoral.toasty.Toasty

class NewTaskActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var desc: EditText
    private lateinit var now: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        name = findViewById(R.id.taskName)
        desc = findViewById(R.id.description)
        now = findViewById(R.id.date)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        setTitle()

        val save = findViewById<Button>(R.id.create)
        save.setOnClickListener {
            saveTask()
        }
    }

    private fun setTitle() {
        val intent = Intent()
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Item"
            name.setText(intent.getStringExtra(EXTRA_NAME))
            desc.setText(intent.getStringExtra(EXTRA_DESC))
            now.setText(intent.getStringExtra(EXTRA_DATE))
        } else {
            title = "Add Task"
        }
    }

    private fun saveTask() {
        val taskName = name.text.toString()
        val description = desc.text.toString()
        val moment = now.text.toString()

        if (TextUtils.isEmpty(taskName) || TextUtils.isEmpty(description) || TextUtils.isEmpty(moment)) {
            Toasty.error(this, "Please leave no field empty.", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_NAME, taskName)
        intent.putExtra(EXTRA_DESC, description)
        intent.putExtra(EXTRA_DATE, moment)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {
        const val EXTRA_ID = "com.joey.todo.ui.EXTRA_ID"
        const val EXTRA_NAME = "com.joey.todo.ui.EXTRA_NAME"
        const val EXTRA_DESC = "com.joey.todo.ui.EXTRA_DESC"
        const val EXTRA_DATE = "com.joey.todo.ui.EXTRA_DATE"
    }
}
