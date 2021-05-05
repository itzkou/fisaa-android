package com.kou.fisaa.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.kou.fisaa.R
class BuilderDatePicker {
    companion object {
        var pickedDate: String = ""

        fun showDialog(context: Context, editText: EditText) {
            val dialogView =
                LayoutInflater.from(context).inflate(R.layout.builder_date_picker, null)
            val builder = AlertDialog.Builder(context)
            val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
            builder.setView(dialogView)
            builder.setPositiveButton("confirm") { dialog, _ ->


                val day = datePicker.dayOfMonth
                val month = datePicker.month + 1
                val year = datePicker.year
                pickedDate = "$year-$month-$day"

                editText.setText(pickedDate)

                dialog?.dismiss()
            }

            builder.setNegativeButton(
                "Cancel"
            ) { dialog, _ ->
                dialog?.dismiss()
            }
            val dialog = builder.create()

            dialog.show()

        }

    }
}