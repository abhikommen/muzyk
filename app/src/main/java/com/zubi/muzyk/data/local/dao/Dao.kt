package com.zubi.muzyk.data.local.dao

import androidx.room.*
import androidx.room.Dao
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.data.local.entity.UserEntity

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTrack(track: Track)

    @Delete
    fun deleteTrack(track: Track)

    @Query("SELECT * FROM track")
    fun getAllTracks(): List<Track>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM USER_ENTITY")
    fun getUser(): UserEntity

}