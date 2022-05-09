package ru.mirea.zotovml.mireaproject.roomFiles

import android.app.Application
import androidx.room.Room

class App : Application() {
    var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "accounts")
            .allowMainThreadQueries()
            .build()
    }

    companion object {
        var instance: App? = null
    }
}