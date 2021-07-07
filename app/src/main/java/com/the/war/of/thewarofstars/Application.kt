package com.the.war.of.thewarofstars

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.the.war.of.thewarofstars.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

open class Application: Application() {

    var firebaseDB: FirebaseFirestore? = null

    override fun onCreate() {
        super.onCreate()

        configureDi()

        instance = this

        firebaseDB = Firebase.firestore
    }

    open fun configureDi() = startKoin {
        androidContext(this@Application)
        printLogger()
        modules(provideComponent())
    }

    open fun provideComponent() = appComponent

    companion object {
        private const val Tag = "Sauce Application"
        var instance: com.the.war.of.thewarofstars.Application? = null

    }

}