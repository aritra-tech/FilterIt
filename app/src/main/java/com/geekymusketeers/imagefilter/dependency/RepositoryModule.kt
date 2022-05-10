package com.geekymusketeers.imagefilter.dependency

import com.geekymusketeers.imagefilter.repo.EditImageRepo
import com.geekymusketeers.imagefilter.repo.EditImageRepoImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module{
    factory<EditImageRepo> { EditImageRepoImpl(androidContext()) }
}