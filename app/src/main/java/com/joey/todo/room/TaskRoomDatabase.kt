package com.joey.todo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskRoomDatabase : RoomDatabase() {

    abstract fun itemDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null
        private const val database_name = "task_db"

        fun getInstance(context: Context): TaskRoomDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }

            synchronized(this) {
                val obj = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDatabase::class.java,
                    database_name
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = obj
                return obj
            }
        }
    }
}
