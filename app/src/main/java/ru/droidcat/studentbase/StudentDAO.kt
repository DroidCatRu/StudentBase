package ru.droidcat.studentbase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDAO {
    @Query("SELECT * from student_table ORDER BY LOWER(name) ASC")
    fun getStudentsSortedByName(): LiveData<List<Student>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()

    @Query("UPDATE student_table SET name = :name WHERE id = :uid")
    suspend fun updateStudentName(uid: String, name: String)
}