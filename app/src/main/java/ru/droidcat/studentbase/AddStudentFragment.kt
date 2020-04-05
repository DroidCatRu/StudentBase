package ru.droidcat.studentbase

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class AddStudentFragment: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.add_student_sheet, container, false)

        dialog!!.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from<View?>(bottomSheetInternal!!).state =
                BottomSheetBehavior.STATE_EXPANDED
        }


        val studentNameField: TextInputLayout = view.findViewById(R.id.add_student_name)
        val studentLastNameField: TextInputLayout = view.findViewById(R.id.add_student_lastname)
        val studentMiddleNameField: TextInputLayout = view.findViewById(R.id.add_student_middlename)

        val studentNameEditText: TextInputEditText = view.findViewById(R.id.add_student_name_edit_text)
        val studentLastNameEditText: TextInputEditText = view.findViewById(R.id.add_student_lastname_edit_text)
        val studentMiddleNameEditText: TextInputEditText = view.findViewById(R.id.add_student_middlename_edit_text)

        val addStudentButton: MaterialButton = view.findViewById(R.id.add_student_button)

        addStudentButton.setOnClickListener {
            val name = studentNameField.editText?.text.toString()
            val lastname = studentLastNameField.editText?.text.toString()
            val middlename = studentMiddleNameField.editText?.text.toString()

            if(name.isNotEmpty() && lastname.isNotEmpty() && middlename.isNotEmpty()) {
                val intent = Intent("ru.droidcatru.action.NAME_ENTERED")
                intent.putExtra("name", name)
                intent.putExtra("lastname", lastname)
                intent.putExtra("middlename", middlename)
                Log.d("Intent sent", "$lastname $name $middlename")
                context?.sendBroadcast(intent)
                dismiss()
            }
            if(name.isEmpty()) {
                studentNameField.error = getString(R.string.error_name_empty)
            }
            if(lastname.isEmpty()) {
                studentLastNameField.error = getString(R.string.error_lastname_empty)
            }
            if(middlename.isEmpty()) {
                studentMiddleNameField.error = getString(R.string.error_middlename_empty)
            }
        }

        studentNameEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s!!
                if(studentNameField.editText?.text.toString().isNotEmpty()) {
                    studentNameField.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                s!!
            }
        })

        studentLastNameEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s!!
                if(studentLastNameField.editText?.text.toString().isNotEmpty()) {
                    studentLastNameField.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                s!!
            }
        })

        studentMiddleNameEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s!!
                if(studentMiddleNameField.editText?.text.toString().isNotEmpty()) {
                    studentMiddleNameField.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                s!!
            }
        })

        return view
    }

}