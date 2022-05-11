package com.geekymusketeers.imagefilter.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.imagefilter.data.ImageFilter
import com.geekymusketeers.imagefilter.repo.EditImageRepo
import com.geekymusketeers.imagefilter.utilities.Coroutines

class EditImageViewModel(private val editImageRepository: EditImageRepo) : ViewModel(){

    private val imagePreviewDataState = MutableLiveData<ImagePreviewDataState>()
    val imagePreviewUiState: LiveData<ImagePreviewDataState> get() = imagePreviewDataState

    fun prepareImagePreview(imageUri: Uri){
        Coroutines.io {
            runCatching {
                emitImagePreviewUiState(isLoading = true)
                editImageRepository.prepareImgPreview(imageUri)
            }.onSuccess { bitmap ->
                if (bitmap !=null){
                    emitImagePreviewUiState(bitmap = bitmap)
                }else{
                    emitImagePreviewUiState(
                        error = "Unable to prepare image preview",

                    )
                }
            }.onFailure {
                emitImagePreviewUiState(error = it.message.toString())
            }
        }
    }
    private fun emitImagePreviewUiState(
        isLoading: Boolean = false,
        bitmap: Bitmap? = null,
        error: String? = null,
    ){
        val dataState = ImagePreviewDataState(isLoading, bitmap, error)
        imagePreviewDataState.postValue(dataState)
    }

    data class ImagePreviewDataState(
        val isLoading: Boolean,
        val bitmap: Bitmap?,
        val error: String?
    )

    private val imageFilterDataState = MutableLiveData<ImageFilterDataState>()
    val imageFiltersUiState: LiveData<ImageFilterDataState> get() = imageFilterDataState


    fun loadImageFilters(originalImage: Bitmap){
        Coroutines.io {
            runCatching {
                editImageFilterUiState(isLoading = true)
                editImageRepository.getImageFilters(getPreviewImage(originalImage))
            }.onSuccess { imageFilter ->
                editImageFilterUiState(imageFilter = imageFilter)
            }.onFailure {
                editImageFilterUiState(error = it.message.toString())
            }
        }
    }

    private fun getPreviewImage(originalImage: Bitmap): Bitmap{
        return runCatching {
            val previewWidth = 150
            val previewHeight = originalImage.height * previewWidth / originalImage.width
            Bitmap.createScaledBitmap(originalImage,previewWidth,previewHeight,false)
        }.getOrDefault(originalImage)
    }

    private fun editImageFilterUiState(
        isLoading: Boolean = false,
        imageFilter: List<ImageFilter>? =null,
        error: String? = null
    ){
        val dataState = ImageFilterDataState(isLoading, imageFilter ,error)
        imageFilterDataState.postValue(dataState)
    }

    data class ImageFilterDataState(
        val isLoading: Boolean,
        val imageFilter: List<ImageFilter>?,
        val error: String?
    )
}