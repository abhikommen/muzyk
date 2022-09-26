package com.zubi.muzyk.data.local.entity


import com.google.gson.annotations.SerializedName
import com.zubi.muzyk.util.JsonParcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistEntity(
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pfp")
    val pfp: String,
    @SerializedName("uri")
    val uri: String
) : JsonParcelable()