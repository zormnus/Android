package ru.mirea.zotovml.mireaproject.roomFiles

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonDao {
    @Query("SELECT * FROM accounts WHERE password = :password AND email = :email LIMIT 1")
    fun getOne(email: String?, password: String?): PersonDBEntity?

    @Query("SELECT * FROM accounts WHERE id = :id")
    fun getInfo(id: Long?): List<PersonDBEntity?>?

    @Query("SELECT * FROM accounts WHERE id = :id")
    fun getById(id: Long?): PersonDBEntity?

    @Insert
    fun insert(personDBEntity: PersonDBEntity?)

    @Update
    fun update(personDBEntity: PersonDBEntity?)
}