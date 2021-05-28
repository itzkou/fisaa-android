package com.kou.fisaa.utils

import android.content.Context
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.format.DateUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*


fun coordinateBtnAndInputs(btn: ImageButton, vararg inputs: EditText) {

    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            btn.isEnabled = inputs.all { it.text.isNotEmpty() }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }
    inputs.forEach { it.addTextChangedListener(watcher) }
    btn.isEnabled = inputs.all { it.text.isNotEmpty() }

}

fun coordinatePwd(btn: ImageButton, input: EditText, il: TextInputLayout) {
    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            btn.isEnabled = input.text.length >= 6
            if (input.text.length < 6) {
                il.error = "Password is a least 6 charachters"
            } else il.error = null


        }
    }
    input.addTextChangedListener(watcher)
    btn.isEnabled = input.text.length >= 6

}

fun coordinateBtnAndInputs(btn: Button, vararg inputs: EditText) {

    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            btn.isEnabled = inputs.all { it.text.isNotEmpty() }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }
    inputs.forEach { it.addTextChangedListener(watcher) }
    btn.isEnabled = inputs.all { it.text.isNotEmpty() }

}


fun ImageView.loadCircle(photoUrl: String?) =
    this.load(photoUrl) {
        crossfade(true)
        transformations(CircleCropTransformation())
    }


fun formatRelativeTimestamp(start: Date, end: Date): CharSequence =
    DateUtils.getRelativeTimeSpanString(
        start.time, end.time, DateUtils.SECOND_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE
    ).replace(Regex("\\. ago$"), "")

fun TextView.setDate(date: Date? = null) {
    val dateSpannable = date?.let {
        val dateText = formatRelativeTimestamp(date, Date())
        val spannableString = SpannableString(dateText)
        spannableString.setSpan(
            null,
            0, dateText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString
    }
    this.text = dateSpannable
}

fun stringToDate(string: String): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val date: Date = formatter.parse(string) ?: Date()
    return Date(date.time)
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}




