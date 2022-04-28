package ru.mirea.zotovml.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Employee {
    @PrimaryKey(autoGenerate = true)
    public var id:Long = 0
    public var name:String = ""
    public var salary:Int = 0
}