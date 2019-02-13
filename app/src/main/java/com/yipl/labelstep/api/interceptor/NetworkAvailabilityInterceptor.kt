package com.yipl.labelstep.api.interceptor

import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class NetworkAvailabilityInterceptor @Inject constructor(val connectivityManager: ConnectivityManager): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val isConnected = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true

        if (!isConnected) {
            throw IOException("No internet")
        }

        return chain.proceed(request)
    }
}