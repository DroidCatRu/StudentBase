package ru.droidcat.studentbase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student(@PrimaryKey
                   @ColumnInfo(name = "id") val id: String,
                   @ColumnInfo(name = "name") val name: String,
                   @ColumnInfo(name = "time") val time: String)
