package com.geekymusketeers.imagefilter.repo

import android.graphics.Bitmap
import android.net.Uri
import com.geekymusketeers.imagefilter.data.ImageFilter

interface EditImageRepo {
    suspend fun prepareImgPreview(imageUri: Uri): Bitmap?
    suspend fun getImageFilters(imageUri: Bitmap): List<ImageFilter>
    suspend fun saveFilteredImage(filteredBitmap: Bitmap): Uri?
}