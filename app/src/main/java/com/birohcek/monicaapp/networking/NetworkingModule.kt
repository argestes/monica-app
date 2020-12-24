package com.birohcek.monicaapp.networking

import android.content.Context
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import okhttp3.CookieJar
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkingModule {

    @Provides
    @Singleton
    fun networkFlipperPlugin(): NetworkFlipperPlugin {
        return NetworkFlipperPlugin()
    }

    @Provides
    fun provideFlipperPlugin(networkFlipperPlugin: NetworkFlipperPlugin): FlipperOkhttpInterceptor {
        return FlipperOkhttpInterceptor(networkFlipperPlugin)
    }

    @Provides
    fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
        return builder.build();
    }

    @Provides
    @Singleton
    fun provideCookieJar(@ApplicationContext context: Context) : CookieJar {
        return PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
    }

    @Provides
    fun okhttpBuilder(okhttpInterceptor: FlipperOkhttpInterceptor): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .addNetworkInterceptor(okhttpInterceptor)

    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        val asConverterFactory = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }.asConverterFactory(MediaType.parse("application/json")!!)

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(asConverterFactory)
    }
}