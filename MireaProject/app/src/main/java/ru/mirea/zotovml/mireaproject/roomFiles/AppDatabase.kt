package ru.mirea.zotovml.mireaproject.roomFiles

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PersonDBEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun PersonDao(): PersonDao?
}