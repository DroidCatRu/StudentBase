package ru.droidcat.studentbase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Database(entities = [Student::class], version = 2, exportSchema = false)
abstract class StudentRoomDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDAO

    companion object {
        @Volatile
        private var INSTANCE: StudentRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): StudentRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentRoomDatabase::class.java,
                    "student_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(StudentDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class StudentDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.studentDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(studentDAO: StudentDAO) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            studentDAO.deleteAll()

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ENGLISH)

            var currentDate = sdf.format(Date())
            var student = Student("1", "Геденидзе", "Давид", "Тимуриевич", currentDate)
            studentDAO.insert(student)

            currentDate = sdf.format(Date())
            student = Student("2", "Копотов", "Михаил", "Алексеевич", currentDate)
            studentDAO.insert(student)

            currentDate = sdf.format(Date())
            student = Student("3", "Горак", "Никита", "Сергеевич", currentDate)
            studentDAO.insert(student)

            currentDate = sdf.format(Date())
            student = Student("4", "Копташкина", "Татьяна", "Алексеевна", currentDate)
            studentDAO.insert(student)

            currentDate = sdf.format(Date())
            student = Student("5", "Кошкин", "Артем", "Сергеевич", currentDate)
            studentDAO.insert(student)
        }
    }

}
