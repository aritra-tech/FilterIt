package com.geekymusketeers.imagefilter.activity.editimages

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.geekymusketeers.imagefilter.activity.MainActivity
import com.geekymusketeers.imagefilter.databinding.ActivityEditImageScreenBinding

class EditImageScreen : AppCompatActivity() {

    private lateinit var binding: ActivityEditImageScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListerner()
        displayImagePreview()
    }

    private fun displayImagePreview() {
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            val inputStream = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.imagePreview.setImageBitmap(bitmap)
            binding.imagePreview.visibility = View.VISIBLE
        }
    }

    private fun setListerner() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}