package com.joey.todo.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class Item(
    @NonNull var title: String,
    @NonNull var description: String,
    @NonNull var deadline: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
