package com.joey.todo.offline

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class OfflineData: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
