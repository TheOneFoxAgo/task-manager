package com.trkpo.taskmanager.datasources

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

interface ServersDataSource {
    suspend fun add(link: String, isAdmin: Boolean): Int
}

class ServersDataSourceImpl @Inject constructor() : ServersDataSource {
    override suspend fun add(link: String, isAdmin: Boolean): Int = 0
}

@Module
@InstallIn(ActivityComponent::class)
abstract class ServersDataSourceModule {
    @Binds
    abstract fun bindServersDataSource(
        serversDataSourceImpl: ServersDataSourceImpl
    ): ServersDataSource
}