package com.zubi.muzyk.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "track")
data class Track(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: String,
    @SerializedName("artists")
    val artists: List<String>?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("explicit")
    val explicit: Boolean?,
    @SerializedName("externalUrl")
    val externalUrl: String?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("popularity")
    val popularity: Int?,
    @SerializedName("previewUrl")
    val previewUrl: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("uri")
    val uri: String?
)