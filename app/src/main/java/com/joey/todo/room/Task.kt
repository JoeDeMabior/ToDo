package com.joey.todo.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo_list")
data class Task(
    var title: String,
    var description: String,
    var deadline: String,
    var hasReminder: Boolean,
    var date: Date
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
