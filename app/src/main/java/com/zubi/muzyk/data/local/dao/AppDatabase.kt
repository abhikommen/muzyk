package com.zubi.muzyk.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zubi.muzyk.data.local.converters.ImageConverters
import com.zubi.muzyk.data.local.converters.StringListConverters
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.data.local.entity.UserEntity


@Database(
    entities = [Track::class, UserEntity::class],
    version = 1
)
@TypeConverters(
    ImageConverters::class,
    StringListConverters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): Dao
}