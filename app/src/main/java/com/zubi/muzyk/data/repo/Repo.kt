package com.zubi.muzyk.data.repo

import com.zubi.muzyk.data.local.entity.*
import com.zubi.muzyk.util.DataState
import kotlinx.coroutines.flow.Flow

interface Repo {

    fun getSimilarTrack(trackId: String): Flow<DataState<SimilarTrackEntity>>
    fun getSearchQuery(query: String): Flow<DataState<List<NewTrack>>>

    suspend fun insertTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    fun getRecentTracksLocal(): Flow<DataState<List<Track>>>

    fun getTrendingChart(): Flow<DataState<List<ChartsEntity>>>
    fun registerUser(token: String): Flow<DataState<UserEntity>>
    fun getWeirdSongs(song: String): Flow<DataState<List<WeirdSongEntity>>>
    fun getUser(): Flow<DataState<NewUser>>

    fun getTopTracks(): Flow<DataState<List<NewTrack>>>
    fun getTopArtists(): Flow<DataState<List<ArtistEntity>>>

}