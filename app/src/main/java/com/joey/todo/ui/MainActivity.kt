package com.joey.todo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.joey.todo.R

class MainActivity : AppCompatActivity() {

    private var tag = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {

        }
    }

    companion object {
        const val ADD_ITEM_REQUEST_CODE = 1
    }
}
