package com.zubi.muzyk.data.local.entity

import com.google.gson.annotations.SerializedName

data class SearchTracks(
    @SerializedName("artists")
    val artists: List<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String
)