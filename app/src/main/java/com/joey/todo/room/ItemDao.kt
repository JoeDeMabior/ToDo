package com.joey.todo.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface ItemDao {
    @Insert(onConflict = REPLACE)
    fun insert(item: Item)

    @Update(onConflict = REPLACE)
    fun update(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("SELECT * FROM todo_list ORDER BY id")
    fun getAllItems(): LiveData<List<Item>>

    @Query("DELETE FROM todo_list")
    fun deleteAll()
}
