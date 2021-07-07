package com.the.war.of.thewarofstars

import android.app.Application
import com.the.war.of.thewarofstars.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

open class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        configureDi()
    }

    open fun configureDi() = startKoin {
        androidContext(this@Application)
        printLogger()
        modules(provideComponent())
    }

    open fun provideComponent() = appComponent


}