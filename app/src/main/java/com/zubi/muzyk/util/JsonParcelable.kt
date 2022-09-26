package com.zubi.muzyk.util

import android.net.Uri
import android.os.Parcelable
import com.google.gson.Gson

abstract class JsonParcelable() : Parcelable {
    fun toJson(): String {
        return Uri.encode(Gson().toJson(this))
    }
}