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


        val studentNameField: TextInputLayout = view.findViewById(R.id.add_student_field)
        val studentNameEditText: TextInputEditText = view.findViewById(R.id.add_student_edit_text)

        studentNameField.setEndIconOnClickListener {
            if(studentNameField.editText?.text.toString().isNotEmpty()) {
                val intent = Intent("ru.droidcatru.action.NAME_ENTERED")
                intent.putExtra("name", studentNameField.editText?.text.toString())
                Log.d("Intent sent", studentNameField.editText?.text.toString())
                context?.sendBroadcast(intent)
                dismiss()
            }
            else {
                studentNameField.error = getString(R.string.error_name_empty)
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
        return view
    }

}