package com.zubi.muzyk.data.remote

import com.zubi.muzyk.data.local.entity.SearchEntity
import com.zubi.muzyk.data.local.entity.SimilarTrackEntity
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.data.local.entity.UserEntity
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(val apiService: ApiService) : RemoteDataSource {

    override suspend fun getSimilarTracks(trackId: String): SimilarTrackEntity {
        return apiService.getSimilarTracks(trackId = trackId)
    }

    override suspend fun getSearchQuery(query: String): SearchEntity {
        return apiService.getSearch(query = query)
    }

    override suspend fun getTrendingChart(): List<Track> {
        return apiService.getTrendingChart()
    }

    override suspend fun registerUser(token: String): UserEntity {
        return apiService.registerUser(token = token)
    }
}