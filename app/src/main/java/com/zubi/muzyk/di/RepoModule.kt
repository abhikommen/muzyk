package com.zubi.muzyk.di

import com.zubi.muzyk.data.local.LocalDataSource
import com.zubi.muzyk.data.remote.RemoteDataSource
import com.zubi.muzyk.data.repo.Repo
import com.zubi.muzyk.data.repo.RepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun providesRepo(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): Repo {
        return RepoImpl(remoteDataSource = remoteDataSource, localDataSource = localDataSource)
    }

}