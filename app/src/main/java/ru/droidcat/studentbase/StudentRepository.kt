package ru.droidcat.studentbase

import androidx.lifecycle.LiveData

class StudentRepository(private val studentDAO: StudentDAO) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allStudents: LiveData<List<Student>> = studentDAO.getStudentsSortedByName()

    suspend fun insert(student: Student) {
        studentDAO.insert(student)
    }

    suspend fun updateStudentName(uid: String, lastname: String, name: String, middlename: String) {
        studentDAO.updateStudentName(uid, lastname, name, middlename)
    }
}