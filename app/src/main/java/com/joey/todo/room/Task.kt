package com.joey.todo.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @NonNull var title: String,
    @NonNull var description: String,
    @NonNull var deadline: String
)
