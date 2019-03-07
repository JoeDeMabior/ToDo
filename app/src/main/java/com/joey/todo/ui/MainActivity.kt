package com.joey.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.joey.todo.R
import com.joey.todo.adapter.ItemAdapter
import com.joey.todo.model.Item
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {
    private lateinit var reference: DatabaseReference

    private var itemList: MutableList<Item>? = null
    private lateinit var itemAdapter: ItemAdapter
    private var listViewItems: ListView? = null

    private var tag = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            addNewItem()
        }

        reference = FirebaseDatabase.getInstance().reference
        listViewItems = findViewById(R.id.items)
        itemList = mutableListOf()
        itemAdapter = ItemAdapter(this, itemList!!)
        listViewItems!!.adapter = itemAdapter

        val itemListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w(tag, "Failed fetching item(s).", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                addItemToList(p0)
            }

        }

        reference.orderByKey().addListenerForSingleValueEvent(itemListener)
    }

    private fun addItemToList(dataSnapshot: DataSnapshot) {
        val items = dataSnapshot.children.iterator()
        // Check if current DB contains any collection
        if (items.hasNext()) {
            val index = items.next()
            val itemsIterator = index.children.iterator()
            // Check if the collection has any items
            while (itemsIterator.hasNext()) {
                // Get current item
                val currentItem = itemsIterator.next()
                val item = Item.create()

                // Get current data in a map
                val map = currentItem.value as HashMap<*, *>
                // Get the Firebase ID from the key
                item.id = currentItem.key
                item.done = map["done"] as Boolean?
                item.text = map["text"] as String?

                itemList!!.add(item)
            }
        }
        itemAdapter.notifyDataSetChanged()
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

            val newItem = reference.child("items").push()
            todoItem.id = newItem.key
            newItem.setValue(todoItem)

            dialog.dismiss()

            if (todoItem.text.equals("")) {
                Toasty.info(this, "Empty note discarded.", Toast.LENGTH_SHORT).show()
            } else {
                Toasty.success(this, "Item saved.", Toast.LENGTH_SHORT).show()
            }
        }
        alert.show()
    }
}
