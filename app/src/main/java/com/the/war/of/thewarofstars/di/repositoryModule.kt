package com.the.war.of.thewarofstars.di

import com.the.war.of.thewarofstars.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    factory <LoginRepository> { LoginRepositoryImp(get()) }
    factory <ChattingRepository> { ChattingRepositoryImp(get()) }
    factory <FreeLectureRepository> { FreeLectureRepositoryImp(get()) }
}