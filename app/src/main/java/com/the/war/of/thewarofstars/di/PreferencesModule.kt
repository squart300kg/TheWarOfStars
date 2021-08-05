package com.the.war.of.thewarofstars.di

import com.securepreferences.SecurePreferences
import org.koin.dsl.module

val preferencesModule = module {
    single {
        SecurePreferences(get(), "", "my_prefs.xml")
    }
}