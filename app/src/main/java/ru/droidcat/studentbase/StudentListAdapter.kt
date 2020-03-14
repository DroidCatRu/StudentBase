package ru.droidcat.studentbase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var students = emptyList<Student>() // Cached copy of words
    //lateinit var mOnItemClickListener: View.OnClickListener

    class StudentViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val time: TextView = view.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = inflater.inflate(R.layout.studentlist_item, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val current = students[position]
        val nameBoxText = current.id + " " + current.name
        holder.name.text = nameBoxText
        holder.time.text = current.time
        holder.itemView.tag = holder
        //holder.itemView.setOnClickListener(mOnItemClickListener)
    }

    internal fun setStudents(students: List<Student>) {
        this.students = students
        notifyDataSetChanged()
    }

    override fun getItemCount() = students.size
}