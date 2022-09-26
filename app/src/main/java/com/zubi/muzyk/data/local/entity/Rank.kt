package com.zubi.muzyk.data.local.entity


import com.google.gson.annotations.SerializedName

data class Rank(
    @SerializedName("currentRank")
    val currentRank: Int,
    @SerializedName("entryStatus")
    val entryStatus: String,
    @SerializedName("previousRank")
    val previousRank: Int
)


