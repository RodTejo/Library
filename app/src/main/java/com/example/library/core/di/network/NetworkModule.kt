package com.example.library.core.di.network

import android.app.Application
import com.example.library.core.common.AppConstants.API.BookList.API_BASE
import com.example.library.features.addbook.data.AddBookApi
import com.example.library.features.booklist.data.BookListApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    companion object{
        const val CACHE_SIZE: Long = 10 * 1024 * 1024;
        const val CONNECTION_TIMEOUT_IN_SECONDS: Long = 10
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(application: Application): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.cache(Cache(application.cacheDir, CACHE_SIZE))
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        httpClient.readTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        httpClient.writeTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(API_BASE)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideBookListApi(retrofit: Retrofit): BookListApi {
        return retrofit.create(BookListApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideAddBookApi(retrofit: Retrofit): AddBookApi {
        return retrofit.create(AddBookApi::class.java)
    }
}