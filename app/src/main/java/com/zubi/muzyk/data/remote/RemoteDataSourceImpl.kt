package com.zubi.muzyk.data.remote

import com.zubi.muzyk.data.local.entity.*
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {

    override suspend fun getSimilarTracks(trackId: String): SimilarTrackEntity {
        return apiService.getSimilarTracks(trackId = trackId)
    }

    override suspend fun getSearchQuery(query: String): List<NewTrack> {
        return apiService.getSearch(query = query)
    }

    override suspend fun getTrendingChart(): List<ChartsEntity> {
        return apiService.getTrendingChart()
    }

    override suspend fun registerUser(token: String): UserEntity {
        return apiService.registerUser(token = token)
    }

    override suspend fun getWeirdSongs(song: String): List<WeirdSongEntity> {
        return apiService.getWeirdSongs(song)
    }

    override suspend fun getUser(): NewUser {
        return apiService.getUser()
    }

    override suspend fun getTopArtists(): List<ArtistEntity> {
        return apiService.getTopArtists()
    }

    override suspend fun getTopTracks(): List<NewTrack> {
        return apiService.getTopTracks()
    }
}