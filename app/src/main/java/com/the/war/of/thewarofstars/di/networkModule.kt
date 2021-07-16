package com.the.war.of.thewarofstars.di

import android.util.Log
import com.the.war.of.thewarofstars.api.LoginApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIMEOUT = 10L

val debugInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.d("API", message)
    }

}).apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val networkModule = module {
    factory <LoginApi> {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(debugInterceptor)
//            .addInterceptor(AuthInterceptor(get()))
            .build()

//        val url = when(securePreferences.getInt("LOGIN_TYPE", 0)) {
//            LoginType.OPEN.type -> "https://api.sauceflex.com/V1/"
//            LoginType.QA.type -> "https://stage.api.sauceflex.com/V1/"
//            LoginType.DEV.type -> "https://dev.api.sauceflex.com/V1/"
//            else -> "https://api.sauceflex.com/V1/"
//        }

        val url = "https://openapi.naver.com/"

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }
}