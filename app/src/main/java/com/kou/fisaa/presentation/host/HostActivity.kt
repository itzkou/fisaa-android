package com.kou.fisaa.presentation.host

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kou.fisaa.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}