package com.trkpo.taskmanager.datasources

import com.trkpo.taskmanager.models.TeamInfo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

interface ApiDataSource {
    suspend fun loginByLink(link: String, password: String): TeamInfo;
}

class ApiDataSourceImpl @Inject constructor() : ApiDataSource {
    override suspend fun loginByLink(
        link: String,
        password: String
    ): TeamInfo = TeamInfo("TestTeam", "TestMember")
}

@Module
@InstallIn(ActivityComponent::class)
abstract class ApiDataSourceModule {
    @Binds
    abstract fun bindApiDataSource(
        apiDataSourceImpl: ApiDataSourceImpl
    ): ApiDataSource
}