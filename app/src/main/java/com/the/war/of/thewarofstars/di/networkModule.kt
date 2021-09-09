package com.the.war.of.thewarofstars.di

import android.util.Log
import com.the.war.of.thewarofstars.api.FunctionApi
import com.the.war.of.thewarofstars.api.LoginApi
import com.the.war.of.thewarofstars.api.YoutubeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIMEOUT = 10L

fun debugInterceptor(apiType: String) = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.d("${apiType}_API", message)
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
            .addInterceptor(debugInterceptor("Login"))
//            .addInterceptor(AuthInterceptor(get()))
            .build()

        val url = "https://openapi.naver.com/"

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }

    factory <FunctionApi> {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(debugInterceptor("Chatting"))
//            .addInterceptor(AuthInterceptor(get()))
            .build()

        val url = "https://us-central1-thewarofstars-8f9a8.cloudfunctions.net/"

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FunctionApi::class.java)
    }

    factory <YoutubeApi> {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(debugInterceptor("Chatting"))
//            .addInterceptor(AuthInterceptor(get()))
            .build()

        val url = "https://www.googleapis.com/youtube/v3/"

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YoutubeApi::class.java)
    }
}