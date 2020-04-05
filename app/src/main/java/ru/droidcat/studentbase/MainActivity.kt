package ru.droidcat.studentbase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //private val newStudentActivityRequestCode = 1
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var fab: FloatingActionButton
    private lateinit var br: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottom_appbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = StudentListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        studentViewModel = ViewModelProvider(this).get(StudentViewModel::class.java)
        studentViewModel.allStudents.observe(this, Observer { students ->
            // Update the cached copy of the students in the adapter.
            students?.let { adapter.setStudents(it) }
        })

        fab = findViewById(R.id.add_student)
        fab.setOnClickListener {
            val addStudentFragment = AddStudentFragment()
            addStudentFragment.show(supportFragmentManager, addStudentFragment.tag)
        }

        br = object : BroadcastReceiver() {
            // действия при получении сообщений
            override fun onReceive(context: Context?, intent: Intent) {
                val lastname = intent.getStringExtra("lastname")
                val name = intent.getStringExtra("name")
                val middlename = intent.getStringExtra("middlename")
                if(lastname != null && name != null && middlename != null) {
                    studentViewModel.insert(lastname, name, middlename)
                }
                else {
                    Log.i("Broadcast", "broadcast corrupted")
                }
            }
        }
        // создаем фильтр для BroadcastReceiver
        val intFilt = IntentFilter("ru.droidcatru.action.NAME_ENTERED")
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(br, intFilt)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.change_student_name -> {
                val id = studentViewModel.size()
                studentViewModel.updateStudentName(id.toString(), "Иванов","Иван", "Иванович")
            }
        }

        return true
    }
}
