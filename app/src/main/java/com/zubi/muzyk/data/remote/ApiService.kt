package com.zubi.muzyk.data.remote

import com.zubi.muzyk.data.local.entity.SearchEntity
import com.zubi.muzyk.data.local.entity.SimilarTrackEntity
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.data.local.entity.UserEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/registerUser")
    suspend fun registerUser(
        @Query("token") token: String
    ): UserEntity

    @GET("similar_tracks")
    suspend fun getSimilarTracks(
        @Query("track_id") trackId: String,
    ): SimilarTrackEntity

    @GET("search")
    suspend fun getSearch(
        @Query("search") query: String,
    ): SearchEntity

    @GET("charts")
    suspend fun getTrendingChart(): List<Track>

}