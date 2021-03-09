package com.kou.fisaa

import android.app.Application
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp //triggers Dagger  component generation + its like @AndroidEntryPoint but for Application class
class FisaaApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show()
    }
}