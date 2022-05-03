package com.geekymusketeers.imagefilter.repo

import android.graphics.Bitmap
import android.net.Uri

interface EditImageRepo {
    suspend fun prepareImgPreview(imageUri: Uri): Bitmap?
}