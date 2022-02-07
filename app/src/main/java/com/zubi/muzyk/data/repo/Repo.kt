package com.zubi.muzyk.data.repo

import com.zubi.muzyk.data.local.entity.SearchEntity
import com.zubi.muzyk.data.local.entity.SimilarTrackEntity
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.data.local.entity.UserEntity
import com.zubi.muzyk.util.DataState
import kotlinx.coroutines.flow.Flow

interface Repo {

    fun getSimilarTrack(trackId: String): Flow<DataState<SimilarTrackEntity>>
    fun getSearchQuery(query: String): Flow<DataState<SearchEntity>>

    suspend fun insertTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    fun getRecentTracksLocal(): Flow<DataState<List<Track>>>

    fun getTrendingChart(): Flow<DataState<List<Track>>>

    fun registerUser(token: String):  Flow<DataState<UserEntity>>

}