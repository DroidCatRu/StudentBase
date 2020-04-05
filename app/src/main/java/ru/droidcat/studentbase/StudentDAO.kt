package ru.droidcat.studentbase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDAO {
    @Query("SELECT * from student_table ORDER BY LOWER(lastname), LOWER(name), LOWER(middlename), id ASC")
    fun getStudentsSortedByName(): LiveData<List<Student>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()

    @Query("UPDATE student_table SET lastname = :lastname, name = :name, middlename = :middlename WHERE id = :uid")
    suspend fun updateStudentName(uid: String, lastname: String, name: String, middlename: String)
}