package com.the.war.of.thewarofstars

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nhn.android.naverlogin.OAuthLogin
import com.the.war.of.thewarofstars.di.networkModule
import com.the.war.of.thewarofstars.di.repositoryModule
import com.the.war.of.thewarofstars.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

open class Application: Application() {

    var firebaseDB: FirebaseFirestore? = null
    var naverLoginModule: OAuthLogin? = null

    override fun onCreate() {
        super.onCreate()

        naverInitialize()

        koinInitialize()

        contextInitialize()

        firebaseInitialize()
    }

    private fun firebaseInitialize() {
        firebaseDB = Firebase.firestore
    }

    private fun contextInitialize() {
        instance = this
    }

    private fun koinInitialize() = configureDi()

    private fun naverInitialize() {
        naverLoginModule = OAuthLogin.getInstance()
        naverLoginModule?.let {
            it.init(
                this,
                getString(R.string.naver_client_id),
                getString(R.string.naver_client_secret),
                getString(R.string.app_name)
            )
        }
    }

    open fun configureDi() = startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@Application)
        modules(listOf(networkModule ,repositoryModule ,viewModelModule))
    }

    companion object {
        private const val Tag = "Sauce Application"
        var instance: com.the.war.of.thewarofstars.Application? = null

    }

}