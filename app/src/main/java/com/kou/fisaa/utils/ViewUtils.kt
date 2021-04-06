package com.kou.fisaa.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.kou.fisaa.R


fun coordinateBtnAndInputs(btn: ImageButton, vararg inputs: EditText) {

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btn.isEnabled = inputs.all { it.text.isNotEmpty()  }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        }
        inputs.forEach { it.addTextChangedListener(watcher) }
        btn.isEnabled = inputs.all { it.text.isNotEmpty()  }

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
                }
                else il.error=null


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

    Glide.with(this).load(photoUrl).circleCrop().fallback(
        R.drawable.ic_launcher_background
    ).into(this)
