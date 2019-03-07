package com.joey.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.joey.todo.R
import com.joey.todo.model.Item
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance()
        reference = database.reference.child("items").push()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            addNewItem()
        }
    }

    private fun addNewItem() {
        val alert = AlertDialog.Builder(this)
        val item = EditText(this)
        alert.setTitle("Add new item")
        alert.setView(item)
        alert.setPositiveButton("Add") { dialog, _ ->
            val todoItem = Item.create()
            todoItem.text = item.text.toString()
            todoItem.done = false
            todoItem.id = reference.key
            reference.setValue(todoItem)

            dialog.dismiss()

            Toasty.success(this, "Item saved.", Toast.LENGTH_SHORT).show()
        }
        alert.show()
    }
}
