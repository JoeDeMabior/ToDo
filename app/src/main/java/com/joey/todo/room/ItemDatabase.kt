package com.joey.todo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao() : ItemDao

    companion object {
        @Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getInstance(context: Context): ItemDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }

            synchronized(this) {
                val obj = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
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
