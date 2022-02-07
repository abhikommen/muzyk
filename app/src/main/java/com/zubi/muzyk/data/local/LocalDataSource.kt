package com.zubi.muzyk.data.local

import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.data.local.entity.UserEntity

interface LocalDataSource {

    suspend fun insertTrack(track: Track)

    suspend fun deleteTrack(track: Track)

    suspend fun getAllTrack(): List<Track>

    suspend fun insertUser(userEntity: UserEntity)

    suspend fun getUser(): UserEntity

}