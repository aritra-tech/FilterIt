package com.geekymusketeers.imagefilter.dependency


import com.geekymusketeers.imagefilter.viewmodel.EditImageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { EditImageViewModel(editImageRepository = get()) }
}