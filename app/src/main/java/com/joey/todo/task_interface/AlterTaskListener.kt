package com.joey.todo.task_interface

import com.joey.todo.room.Item

interface AlterTaskListener {
    fun editTask(item: Item)
    fun deleteTask(item: Item)
}
