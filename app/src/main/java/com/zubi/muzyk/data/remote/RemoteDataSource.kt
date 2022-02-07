package com.zubi.muzyk.data.remote

import com.zubi.muzyk.data.local.entity.SearchEntity
import com.zubi.muzyk.data.local.entity.SimilarTrackEntity
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.data.local.entity.UserEntity

interface RemoteDataSource {

    suspend fun getSimilarTracks(
        trackId: String
    ): SimilarTrackEntity


    suspend fun getSearchQuery(
        query: String
    ): SearchEntity


    suspend fun getTrendingChart(): List<Track>

    suspend fun registerUser(token: String): UserEntity
}