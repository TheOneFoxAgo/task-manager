package com.trkpo.taskmanager

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface TeamsCategory
sealed interface DoneCategory
sealed interface ServersCategory
sealed interface InProgressCategory

@Serializable
object Init : NavKey, ServersCategory

@Serializable
object LoginByLink : NavKey, ServersCategory

@Serializable
object AdminLogin : NavKey, ServersCategory

@Serializable
object NewTask : NavKey, TeamsCategory

@Serializable
object Teams : NavKey, TeamsCategory

@Serializable
object NewTeam : NavKey, TeamsCategory

@Serializable
data class Tasks(val teamId: Int) : NavKey, TeamsCategory

@Serializable
data class TeamInfo(val teamId: Int) : NavKey, TeamsCategory

@Serializable
object Servers : NavKey, ServersCategory

@Serializable
object Done : NavKey, DoneCategory

@Serializable
object InProgress : NavKey, InProgressCategory