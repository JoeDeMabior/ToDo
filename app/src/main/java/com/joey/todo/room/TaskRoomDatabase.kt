package com.joey.todo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskRoomDatabase : RoomDatabase() {

    abstract fun itemDao() : TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null

        fun getInstance(context: Context): TaskRoomDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }

            synchronized(this) {
                val obj = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDatabase::class.java,
                    "task_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = obj
                return obj
            }
        }
    }
}
