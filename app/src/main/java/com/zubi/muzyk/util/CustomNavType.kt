package com.zubi.muzyk.util

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson

class CustomNavType<T : Parcelable>(private val type: Class<T>) :
    NavType<T>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): T {
        return Gson().fromJson<T>(value, type)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }
}