package com.joey.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.joey.todo.repository.ItemRepo
import com.joey.todo.room.Item
import com.joey.todo.room.ItemDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: ItemRepo
    private val allItems: LiveData<List<Item>>

    init {
        val itemDao = ItemDatabase.getInstance(application).itemDao()
        repo = ItemRepo(itemDao)
        allItems = repo.allItems
    }

    private var job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    fun insert(item: Item) = scope.launch(Dispatchers.IO) {
        repo.insert(item)
    }

    fun update(item: Item) = scope.launch(Dispatchers.IO) {
        repo.update(item)
    }

    fun delete(item: Item) = scope.launch(Dispatchers.IO) {
        repo.delete(item)
    }

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
