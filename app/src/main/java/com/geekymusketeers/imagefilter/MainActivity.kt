package com.geekymusketeers.imagefilter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geekymusketeers.imagefilter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        private const val REQUEST_CODE_PICK_IMAGE =1
        const val KEY_IMAGE_URI = "imageuri"
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}