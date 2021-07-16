package com.kou.fisaa.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.*
import android.text.format.DateUtils
import android.text.method.LinkMovementMethod
import android.text.style.TypefaceSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.textfield.TextInputLayout
import com.kou.fisaa.R
import java.text.SimpleDateFormat
import java.util.*


class CustomTypefaceSpan(family: String?, private val newType: Typeface) : TypefaceSpan(family) {
    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    companion object {
        private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
            val oldStyle: Int
            val old = paint.typeface
            oldStyle = old?.style ?: 0
            val fake = oldStyle and tf.style.inv()
            if (fake and Typeface.BOLD != 0) {
                paint.isFakeBoldText = true
            }
            if (fake and Typeface.ITALIC != 0) {
                paint.textSkewX = -0.25f
            }
            paint.typeface = tf
        }
    }
}
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

fun ImageView.loadAvatar(photoUrl: String?) {
    if (photoUrl.isNullOrEmpty()) {
        this.load(ContextCompat.getDrawable(this.context, R.drawable.ic_face)) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

    } else
        this.load(photoUrl)

}


fun formatRelativeTimestamp(start: Date, end: Date): CharSequence =
    DateUtils.getRelativeTimeSpanString(
        start.time, end.time, DateUtils.SECOND_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE
    ).replace(Regex("\\. ago$"), "")

fun TextView.setDate(date: Date?) {
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

    val date: Date = formatter.parse(string)
    return Date(date.time)
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun TextView.setCaptionText(username: String, caption: String) {
    val usernameSpannable = SpannableString(username)
    val captionSpannable = SpannableString(caption)
    val semiBold: Typeface? = ResourcesCompat.getFont(this.context, R.font.poppins_semi_bold)
    val regular: Typeface? = ResourcesCompat.getFont(this.context, R.font.poppins_regular)


    usernameSpannable.setSpan(
        semiBold?.let { CustomTypefaceSpan("", it) },
        0,
        usernameSpannable.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    captionSpannable.setSpan(
        ContextCompat.getColor(this.context, R.color.bluewey), 0, captionSpannable.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    text = SpannableStringBuilder().apply {
        append(usernameSpannable)
        append(" ")
        append(caption)

    }
    movementMethod = LinkMovementMethod.getInstance()
}


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}




