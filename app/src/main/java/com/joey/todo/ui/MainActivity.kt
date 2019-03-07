package com.joey.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.joey.todo.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            addNewItem()
        }
    }

    private fun addNewItem() {
        val alert = AlertDialog.Builder(this)
        val item = EditText(this)
        alert.setTitle("New Entry")
        alert.setMessage("Add new item")
        alert.setView(item)
        alert.setPositiveButton("Add") { _, _ -> }
        alert.show()
    }
}
