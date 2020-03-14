package ru.droidcat.studentbase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: StudentRepository
    // LiveData gives us updated words when they change.
    val allStudents: LiveData<List<Student>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val studentDao = StudentRoomDatabase.getDatabase(application, viewModelScope).studentDao()
        repository = StudentRepository(studentDao)
        allStudents = repository.allStudents
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on the mainthread, blocking
     * the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called viewModelScope which we
     * can use here.
     */
    fun insert(student: Student) = viewModelScope.launch {
        repository.insert(student)
    }

    fun insert(name: String) = viewModelScope.launch {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ENGLISH)
        var currentDate = sdf.format(Date())
        val id = size()?.plus(1)
        val student = Student(id.toString(), name, currentDate.toString())
        repository.insert(student)
    }

    fun size(): Int? {
        return allStudents.value?.size
    }

    fun updateStudentName(uid: String, name: String) = viewModelScope.launch {
        repository.updateStudentName(uid, name)
    }
}