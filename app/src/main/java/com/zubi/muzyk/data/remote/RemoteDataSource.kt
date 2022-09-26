package com.zubi.muzyk.data.remote

import com.zubi.muzyk.data.local.entity.*

interface RemoteDataSource {

    suspend fun getSimilarTracks(
        trackId: String
    ): SimilarTrackEntity


    suspend fun getSearchQuery(
        query: String
    ): List<NewTrack>


    suspend fun getTrendingChart(): List<ChartsEntity>
    suspend fun registerUser(token: String): UserEntity
    suspend fun getWeirdSongs(song : String) : List<WeirdSongEntity>
    suspend fun getUser() : NewUser

    suspend fun getTopArtists() : List<ArtistEntity>
    suspend fun getTopTracks() : List<NewTrack>

}