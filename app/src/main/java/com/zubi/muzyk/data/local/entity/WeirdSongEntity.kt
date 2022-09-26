package com.zubi.muzyk.data.local.entity


import com.google.gson.annotations.SerializedName

data class WeirdSongEntity(
    @SerializedName("album_cover")
    val albumCover: String,
    @SerializedName("artist_name")
    val artistName: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("phrase_id")
    val phraseId: Int,
    @SerializedName("preview_url")
    val previewUrl: String? = null,
    @SerializedName("song_name")
    val songName: String,
    @SerializedName("uri")
    val uri: String
)