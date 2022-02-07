package com.zubi.muzyk.data.local.entity


import com.google.gson.annotations.SerializedName

data class SimilarTrackEntity(
    @SerializedName("originalTrack")
    val originalTrack: Track,
    @SerializedName("total")
    val total: Int,
    @SerializedName("tracks")
    val similarTracks: List<Track>
)