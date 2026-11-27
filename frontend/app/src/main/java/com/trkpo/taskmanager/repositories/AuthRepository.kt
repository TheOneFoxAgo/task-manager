package com.trkpo.taskmanager.repositories

import com.trkpo.taskmanager.datasources.ApiDataSource
import com.trkpo.taskmanager.datasources.ServersDataSource
import com.trkpo.taskmanager.datasources.TeamsDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    val servers: ServersDataSource,
    val teams: TeamsDataSource,
    val api: ApiDataSource,
) {
    suspend fun loginByLink(link: String, password: String) {
        val teamInfo = api.loginByLink(link, password)
        val serverId = servers.add(link, false)
        teams.add(serverId, teamInfo.teamName, teamInfo.memberName)
    }
}