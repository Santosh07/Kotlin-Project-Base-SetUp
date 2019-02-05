package com.yipl.labelstep.di.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yipl.labelstep.api.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.ConnectionSpec
import java.util.*


@Module(includes = arrayOf(DBModule::class))
class AppModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        //Solves security error for old android device
        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_0)
                .allEnabledCipherSuites()
                .build()

        val client = OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .build()

        return retrofit2.Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build().create(ApiService::class.java)
    }
}