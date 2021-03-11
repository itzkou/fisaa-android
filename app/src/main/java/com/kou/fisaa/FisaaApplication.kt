package com.kou.fisaa

import android.app.Application
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp //triggers Dagger  component generation + its like @AndroidEntryPoint but for Application class
class FisaaApplication :Application() {

}