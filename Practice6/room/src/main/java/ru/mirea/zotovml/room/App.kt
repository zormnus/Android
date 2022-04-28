package ru.mirea.zotovml.room

import android.app.Application
import androidx.room.Room

class App : Application() {
    companion object { lateinit var instance:App }
    private lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            this, AppDatabase::class.java, "database"
        ).allowMainThreadQueries().build()
    }

    fun getInstance() : App {
        return instance
    }
    fun getDatabase() : AppDatabase {
        return database
    }

}