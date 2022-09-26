package com.zubi.muzyk.util

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        Log.d("NetworkInterceptor", "Request : ${chain.request().url} ")
        request.header("Authorization", "Bearer " + SharedPref.getStringVal(USER_TOKEN))
        return chain.proceed(request.build())
    }
}