package com.the.war.of.thewarofstars.di

import com.the.war.of.thewarofstars.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
}