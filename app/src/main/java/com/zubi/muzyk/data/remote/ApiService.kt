package com.zubi.muzyk.data.remote

import com.zubi.muzyk.data.local.entity.*
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/registerUser")
    suspend fun registerUser(
        @Query("token") token: String
    ): UserEntity

    @GET("api/similar_tracks")
    suspend fun getSimilarTracks(
        @Query("id") trackId: String,
    ): SimilarTrackEntity

    @GET("api/search")
    suspend fun getSearch(
        @Query("query") query: String,
    ): List<NewTrack>

    @GET("api/charts")
    suspend fun getTrendingChart(): List<ChartsEntity>

    @GET("api/weird")
    suspend fun getWeirdSongs(
        @Query("text") song: String
    ): List<WeirdSongEntity>

    @GET("api/me")
    suspend fun getUser(): NewUser

    @GET("api/top/artists")
    suspend fun getTopArtists(): List<ArtistEntity>

    @GET("api/top/tracks")
    suspend fun getTopTracks(): List<NewTrack>


}