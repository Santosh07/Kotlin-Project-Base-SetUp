package com.yipl.labelstep.di.module

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yipl.labelstep.api.ApiClient
import com.yipl.labelstep.api.interceptor.NetworkAvailabilityInterceptor
import com.yipl.labelstep.db.LabelDatabase
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.ConnectionSpec
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*


@Module
class AppModule {
    val DATABASE_NAME = "label_database"
    val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideApiService(connectivityManager: ConnectivityManager): ApiClient {
        //Solves security error for old android device
        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_0)
                .allEnabledCipherSuites()
                .build()

        val client = OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .addInterceptor(NetworkAvailabilityInterceptor(connectivityManager))
                .addInterceptor(HttpLoggingInterceptor())
                .build()

        return retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build().create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): LabelDatabase {
        return Room
                .databaseBuilder(
                        application.applicationContext,
                        LabelDatabase::class.java, DATABASE_NAME
                )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(application: Application): ConnectivityManager {
        return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}