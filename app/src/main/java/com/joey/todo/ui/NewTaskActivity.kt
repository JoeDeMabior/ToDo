package com.joey.todo.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.joey.todo.R
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.*

class NewTaskActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var desc: EditText
    private lateinit var now: EditText
    private lateinit var reminder: RadioButton
    private lateinit var head: TextView

    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        name = findViewById(R.id.taskName)
        desc = findViewById(R.id.description)
        now = findViewById(R.id.date)
        reminder = findViewById(R.id.radio)
        head = findViewById(R.id.addOrEdit)

        setTitle()

        val save = findViewById<Button>(R.id.create)
        save.setOnClickListener {
            saveTask()
        }

        setDateFormat()
    }

    private fun setTitle() {
        val edit = "Edit Your Task"
        val intent = intent
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Task"
            head.text = edit
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

        val taskId = 0
        intent.putExtra(EXTRA_ID, taskId)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun setDateFormat() {
        val date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dateFormat()
        }

        val time = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
        }

        now.setOnClickListener {
            DatePickerDialog(
                this,
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()

            TimePickerDialog(
                this,
                time,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    private fun dateFormat() {
        val format = "EEE, MMM dd, yyyy  HH:mm"
        val sdf = SimpleDateFormat(format, Locale.US)
        now.setText(sdf.format(calendar.time))
    }

    companion object {
        const val EXTRA_ID = "com.joey.todo.ui.EXTRA_ID"
        const val EXTRA_NAME = "com.joey.todo.ui.EXTRA_NAME"
        const val EXTRA_DESC = "com.joey.todo.ui.EXTRA_DESC"
        const val EXTRA_DATE = "com.joey.todo.ui.EXTRA_DATE"
    }
}
