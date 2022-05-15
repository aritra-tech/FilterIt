package com.geekymusketeers.imagefilter.activity.editimages

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.geekymusketeers.imagefilter.activity.MainActivity
import com.geekymusketeers.imagefilter.adapter.ImageFilterAdapter
import com.geekymusketeers.imagefilter.data.ImageFilter
import com.geekymusketeers.imagefilter.databinding.ActivityEditImageScreenBinding
import com.geekymusketeers.imagefilter.listener.ImageFilterListener
import com.geekymusketeers.imagefilter.utilities.displayToast
import com.geekymusketeers.imagefilter.utilities.show
import com.geekymusketeers.imagefilter.viewmodel.EditImageViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageScreen : AppCompatActivity(), ImageFilterListener {

    private lateinit var binding: ActivityEditImageScreenBinding
    private val viewModel : EditImageViewModel by viewModel()
    private lateinit var gpuImage: GPUImage

    private lateinit var originalBitmap: Bitmap
    private val filterBitmap = MutableLiveData<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)/
        setListerner()
        displayImagePreview()
        setupObservers()
        prepareImgPreview()
    }

    private fun setListerner() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.imagePreview.setOnLongClickListener{
            binding.imagePreview.setImageBitmap(originalBitmap)
            return@setOnLongClickListener false
        }
        binding.imagePreview.setOnClickListener {
            binding.imagePreview.setImageBitmap(filterBitmap.value)
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
                originalBitmap = bitmap
                filterBitmap.value = bitmap

                with(originalBitmap){
                    gpuImage.setImage(this)
                    binding.imagePreview.show()
                    viewModel.loadImageFilters(this)
                }

            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        }
        viewModel.imageFiltersUiState.observe(this, {
            val imageFilterDataState = it ?: return@observe
            binding.imageFilterProgressBar.visibility =
                if (imageFilterDataState.isLoading) View.VISIBLE else View.GONE
            imageFilterDataState.imageFilter?.let { imageFilter ->
                ImageFilterAdapter(imageFilter,this).also { adapter ->
                    binding.filtersRecyclerView.adapter = adapter
                }
            } ?: kotlin.run {
                imageFilterDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
        filterBitmap.observe(this, { bitmap ->
            binding.imagePreview.setImageBitmap(bitmap)
        })
    }

    private fun prepareImgPreview() {
        gpuImage = GPUImage(applicationContext)
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            viewModel.prepareImagePreview(imageUri)
        }
    }

    override fun onFilterSelected(imageFilter: ImageFilter) {
        with(imageFilter){
            with(gpuImage){
                setFilter(filter)
                filterBitmap.value = bitmapWithFilterApplied
            }
        }
    }

}