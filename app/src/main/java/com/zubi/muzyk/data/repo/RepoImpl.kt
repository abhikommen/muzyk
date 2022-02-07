package com.zubi.muzyk.data.repo

import com.zubi.muzyk.data.local.LocalDataSource
import com.zubi.muzyk.data.local.entity.SearchEntity
import com.zubi.muzyk.data.local.entity.SimilarTrackEntity
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.data.local.entity.UserEntity
import com.zubi.muzyk.data.remote.RemoteDataSource
import com.zubi.muzyk.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepoImpl @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : Repo {

    override fun getSimilarTrack(trackId: String): Flow<DataState<SimilarTrackEntity>> = flow {
        try {
            emit(DataState.Success(remoteDataSource.getSimilarTracks(trackId = trackId)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override fun getSearchQuery(query: String): Flow<DataState<SearchEntity>> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(remoteDataSource.getSearchQuery(query = query)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun insertTrack(track: Track) {
        localDataSource.insertTrack(track)
    }

    override suspend fun deleteTrack(track: Track) {
        localDataSource.deleteTrack(track = track)
    }

    override fun getRecentTracksLocal(): Flow<DataState<List<Track>>> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(localDataSource.getAllTrack()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.Default)

    override fun getTrendingChart(): Flow<DataState<List<Track>>> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(remoteDataSource.getTrendingChart()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override fun registerUser(token: String): Flow<DataState<UserEntity>> = flow {
        emit(DataState.Loading)
        try {
            val user = remoteDataSource.registerUser(token)
            localDataSource.insertUser(user)
            emit(DataState.Success(localDataSource.getUser()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}