package com.joey.todo.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TaskDao {
    @Insert(onConflict = REPLACE)
    fun insert(task: Task)

    @Update(onConflict = REPLACE)
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM todo_list ORDER BY id")
    fun getAllItems(): LiveData<List<Task>>

    @Query("DELETE FROM todo_list")
    fun deleteAll()
}
