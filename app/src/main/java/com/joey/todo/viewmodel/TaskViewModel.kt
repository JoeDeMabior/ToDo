package com.joey.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.joey.todo.repository.TaskRepo
import com.joey.todo.room.Task
import com.joey.todo.room.TaskRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: TaskRepo
    val allTasks: LiveData<List<Task>>

    init {
        val itemDao = TaskRoomDatabase.getInstance(application).itemDao()
        repo = TaskRepo(itemDao)
        allTasks = repo.allTasks
    }

    private var job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    fun insert(task: Task) = scope.launch(Dispatchers.IO) {
        repo.insert(task)
    }

    fun update(task: Task) = scope.launch(Dispatchers.IO) {
        repo.update(task)
    }

    fun delete(task: Task) = scope.launch(Dispatchers.IO) {
        repo.delete(task)
    }

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
