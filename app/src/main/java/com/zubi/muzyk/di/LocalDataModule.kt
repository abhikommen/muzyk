package com.zubi.muzyk.di

import android.content.Context
import androidx.room.Room
import com.zubi.muzyk.data.local.LocalDataSource
import com.zubi.muzyk.data.local.LocalDataSourceImpl
import com.zubi.muzyk.data.local.dao.AppDatabase
import com.zubi.muzyk.data.local.dao.Dao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "mydb").build()
    }


    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): Dao {
        return appDatabase.dao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: Dao): LocalDataSource {
        return LocalDataSourceImpl(dao = dao)
    }

}