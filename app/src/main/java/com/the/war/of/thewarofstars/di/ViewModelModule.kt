package com.the.war.of.thewarofstars.di

import com.the.war.of.thewarofstars.ui.login.LoginViewModel
import com.the.war.of.thewarofstars.ui.message.MessageViewModel
import com.the.war.of.thewarofstars.ui.home.HomeViewModel
import com.the.war.of.thewarofstars.ui.home.sub.GamerDetailViewModel
import com.the.war.of.thewarofstars.ui.home.sub.question.QuestionViewModel
import com.the.war.of.thewarofstars.ui.login.EmailLoginViewModel
import com.the.war.of.thewarofstars.ui.login.RegisterViewModel
import com.the.war.of.thewarofstars.ui.mypage.MyPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { MessageViewModel() }
    viewModel { MyPageViewModel() }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { EmailLoginViewModel(get()) }
    viewModel { GamerDetailViewModel(get()) }
    viewModel { QuestionViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
}