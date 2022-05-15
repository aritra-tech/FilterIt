package com.geekymusketeers.imagefilter.listener

import com.geekymusketeers.imagefilter.data.ImageFilter

interface ImageFilterListener {
    fun onFilterSelected(imageFilter: ImageFilter)
}