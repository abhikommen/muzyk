package com.zubi.muzyk.data.local

import com.zubi.muzyk.data.local.dao.Dao
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.data.local.entity.UserEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(val dao: Dao) : LocalDataSource {
    override suspend fun insertTrack(track: Track) {
        dao.insertTrack(track = track)
    }

    override suspend fun deleteTrack(track: Track) {
        dao.deleteTrack(track = track)
    }

    override suspend fun getAllTrack(): List<Track> {
        return dao.getAllTracks()
    }

    override suspend fun insertUser(userEntity: UserEntity) {
        return dao.insertUser(userEntity = userEntity)
    }

    override suspend fun getUser(): UserEntity {
        return dao.getUser()
    }
}