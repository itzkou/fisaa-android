package com.kou.fisaa.utils

import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.format.DateUtils
import android.widget.*
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.textfield.TextInputLayout
import com.kou.fisaa.R
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

fun ImageView.loadCircle(photoUrl: String?) =    //TODO inject coil if injectable
    this.load("https://fisaa.herokuapp.com/images/$photoUrl") {
        crossfade(true)
        placeholder(R.drawable.ic_launcher_background)
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
}


