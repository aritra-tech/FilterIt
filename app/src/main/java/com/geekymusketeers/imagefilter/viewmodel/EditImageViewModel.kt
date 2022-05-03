package com.geekymusketeers.imagefilter.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekymusketeers.imagefilter.repo.EditImageRepo
import com.geekymusketeers.imagefilter.utilities.Coroutines

class EditImageViewModel(private val editImageRepository: EditImageRepo) : ViewModel(){

    private val imagePreviewDataState = MutableLiveData<ImagePreviewDataState>()
    val uniState: LiveData<ImagePreviewDataState> get() = imagePreviewDataState

    fun prepareImagePreview(imageUri: Uri){
        Coroutines.io {
            kotlin.runCatching {
                emitState(isLoading = true)
                editImageRepository.prepareImgPreview(imageUri)
            }.onSuccess { bitmap ->
                if (bitmap !=null){
                    emitState(bitmap = bitmap)
                }else{
                    emitState(error = "Unable to prepare image preview")
                }
            }.onFailure {
                emitState(error = it.message.toString())
            }
        }
    }
    private fun emitState(
        isLoading: Boolean = false,
        bitmap: Bitmap? =null,
        error: String? = null
    ){
        val dataState = ImagePreviewDataState(isLoading, bitmap, error)
        imagePreviewDataState.value = dataState
    }

    data class ImagePreviewDataState(
        val isLoading: Boolean,
        val bitmap: Bitmap?,
        val error: String?
    )
}