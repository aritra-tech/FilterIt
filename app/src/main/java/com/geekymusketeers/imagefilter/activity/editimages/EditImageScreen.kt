package com.geekymusketeers.imagefilter.activity.editimages

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.geekymusketeers.imagefilter.activity.MainActivity
import com.geekymusketeers.imagefilter.databinding.ActivityEditImageScreenBinding
import com.geekymusketeers.imagefilter.utilities.displayToast
import com.geekymusketeers.imagefilter.utilities.show
import com.geekymusketeers.imagefilter.viewmodel.EditImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageScreen : AppCompatActivity() {

    private lateinit var binding: ActivityEditImageScreenBinding

    private val viewModel : EditImageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListerner()
        displayImagePreview()
        setupObservers()
        prepareImgPreview()
    }

    private fun setListerner() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun displayImagePreview() {
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            val inputStream = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.imagePreview.setImageBitmap(bitmap)
            binding.imagePreview.visibility = View.VISIBLE
        }
    }

    private fun setupObservers(){
        viewModel.imagePreviewUiState.observe(this) {
            val dataState = it ?: return@observe
            dataState.bitmap?.let { bitmap ->
                binding.imagePreview.setImageBitmap(bitmap)
                binding.imagePreview.show()
            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        }
    }

    private fun prepareImgPreview() {
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            viewModel.prepareImagePreview(imageUri)
        }
    }

}