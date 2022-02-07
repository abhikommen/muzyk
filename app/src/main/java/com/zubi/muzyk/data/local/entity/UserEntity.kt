package com.zubi.muzyk.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_entity")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("profile_picture")
    val profilePicture: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
)