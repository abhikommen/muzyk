package com.zubi.muzyk.data.local.entity


import com.google.gson.annotations.SerializedName

data class NewTrack(
    @SerializedName("id")
    val id: String,
    @SerializedName("artists")
    val artists: List<String>,
    @SerializedName("explicit")
    val explicit: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("poster")
    val poster: String,
    @SerializedName("preview_url")
    val previewUrl: String?,
    @SerializedName("uri")
    val uri: String
)