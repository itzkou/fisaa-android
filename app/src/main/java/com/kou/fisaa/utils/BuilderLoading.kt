package com.kou.fisaa.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.kou.fisaa.R

class BuilderLoading(context: Context) {


    val dialogView: View = LayoutInflater.from(context).inflate(R.layout.builder_loading, null)
    val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog).setView(dialogView)
    val dialog = builder.create()


    fun showDialog(flag: String) {

        val loader = dialogView.findViewById<LottieAnimationView>(R.id.loader)
        val done = dialogView.findViewById<LottieAnimationView>(R.id.done)


        dialog.show()
        when (flag) {
            "success" -> {
                loader.visibility = View.INVISIBLE
                done.visibility = View.VISIBLE
                done.playAnimation()
            }
            "loading" -> {
                done.visibility = View.INVISIBLE
                loader.visibility = View.VISIBLE
                loader.playAnimation()
            }

        }
    }


}

