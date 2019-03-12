package com.joey.todo.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.joey.todo.room.Task
import com.joey.todo.room.TaskDao

class TaskRepo(private val taskDao: TaskDao) {
    val allTasks: LiveData<List<Task>> = taskDao.getAllItems()

    @WorkerThread
    fun insert(task: Task) {
        taskDao.insert(task)
    }

    @WorkerThread
    fun update(task: Task) {
        taskDao.update(task)
    }

    @WorkerThread
    fun delete(task: Task) {
        taskDao.delete(task)
    }

    @WorkerThread
    fun deleteAll() {
        taskDao.deleteAll()
    }
}
