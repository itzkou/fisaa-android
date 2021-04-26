package com.kou.fisaa

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp //triggers Dagger  component generating + its like @AndroidEntryPoint but for Application class
class FisaaApplication : Application()