package com.joey.todo.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.joey.todo.room.Item
import com.joey.todo.room.ItemDao

class ItemRepo(private val itemDao: ItemDao) {
    val allItems: LiveData<List<Item>> = itemDao.getAllItems()

    @WorkerThread
    fun insert(item: Item) {
        itemDao.insert(item)
    }

    @WorkerThread
    fun update(item: Item) {
        itemDao.update(item)
    }

    @WorkerThread
    fun delete(item: Item) {
        itemDao.delete(item)
    }

    @WorkerThread
    fun deleteAll() {
        itemDao.deleteAll()
    }
}
