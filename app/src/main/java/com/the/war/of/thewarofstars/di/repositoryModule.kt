package com.the.war.of.thewarofstars.di

import com.the.war.of.thewarofstars.repository.ChattingRepository
import com.the.war.of.thewarofstars.repository.ChattingRepositoryImp
import com.the.war.of.thewarofstars.repository.LoginRepository
import com.the.war.of.thewarofstars.repository.LoginRepositoryImp
import org.koin.dsl.module

val repositoryModule = module {
    factory <LoginRepository> { LoginRepositoryImp(get()) }
    factory <ChattingRepository> { ChattingRepositoryImp(get()) }
}