package com.zubi.muzyk.data.local.entity


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("country")
    val country: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pfp")
    val pfp: String,
    @SerializedName("uri")
    val uri: String
)