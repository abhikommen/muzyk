package com.zubi.muzyk.data.local.entity


import com.google.gson.annotations.SerializedName

data class SearchEntity(
    @SerializedName("total")
    val total: Int,
    @SerializedName("tracks")
    val searchTracks: List<SearchTracks>
)