package com.trkpo.taskmanager.datasources

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

interface TeamsDataSource {
    suspend fun add(serverId: Int, teamName: String, role: String)
}

class TeamsDataSourceImpl @Inject constructor() : TeamsDataSource {
    override suspend fun add(serverId: Int, teamName: String, role: String) {}
}

@Module
@InstallIn(ActivityComponent::class)
abstract class TeamsDataSourceModule {
    @Binds
    abstract fun bindTeamsDataSource(
        teamsDataSourceImpl: TeamsDataSourceImpl
    ): TeamsDataSource
}