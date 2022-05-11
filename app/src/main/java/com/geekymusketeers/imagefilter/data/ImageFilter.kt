package com.geekymusketeers.imagefilter.data

import android.graphics.Bitmap
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

class ImageFilter(
    val name: String,
    val filter: GPUImageFilter,
    val filterPreview : Bitmap
)