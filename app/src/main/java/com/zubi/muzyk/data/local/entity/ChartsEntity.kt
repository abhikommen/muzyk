package com.zubi.muzyk.data.local.entity


import com.google.gson.annotations.SerializedName

data class ChartsEntity(
    @SerializedName("artists")
    val artists: String,
    @SerializedName("poster")
    val poster: String,
    @SerializedName("track_name")
    val trackName: String,
    @SerializedName("uri")
    val uri: String,
    @SerializedName("rank")
    val rank: Rank
)