package com.trkpo.taskmanager

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Icon
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.trkpo.taskmanager.PrimaryAppDestinations.DONE
import com.trkpo.taskmanager.PrimaryAppDestinations.IN_PROGRESS
import com.trkpo.taskmanager.PrimaryAppDestinations.SERVERS
import com.trkpo.taskmanager.PrimaryAppDestinations.TEAMS
import com.trkpo.taskmanager.pages.InitPage
import com.trkpo.taskmanager.pages.LoginByLink
import com.trkpo.taskmanager.ui.theme.TaskManagerTheme
import com.trkpo.taskmanager.viewmodels.LoginByLinkViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() // pleasing hilt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                TaskManagerApp()
            }
        }
    }
}

@Composable
fun TaskManagerApp() {
    val backStack = rememberNavBackStack(Init)
    val currentPrimaryDestination = remember { fromDestination(backStack.last()) }
    val loggedIn = false
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            if (loggedIn) {
                PrimaryAppDestinations.entries.forEach {
                    item(
                        icon = {
                            Icon(
                                painterResource(it.icon),
                                contentDescription = stringResource(it.label)
                            )
                        },
                        label = { stringResource(it.label) },
                        selected = it == currentPrimaryDestination,
                        onClick = {
                            backStack.removeLastOrNull()
                            backStack.add(it.destination)
                        }
                    )
                }
            }

        }
    ) {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<Init> {
                    InitPage(onLoginByLinkClick = {
                        backStack.add(LoginByLink)
                    })
                }
                entry<LoginByLink> {
                    val viewmodel = hiltViewModel<LoginByLinkViewModel>()
                    LoginByLink(
                        onSuccessfulLogin = { println("success") },
                        onBack = { backStack.removeLastOrNull() },
                        viewmodel
                    )
                }
            }
        )
    }
}


enum class PrimaryAppDestinations(
    val label: Int,
    val icon: Int,
    val destination: NavKey,
) {
    DONE(
        R.string.done_icon_label,
        R.drawable.ic_star,
        Done,
    ),
    IN_PROGRESS(
        R.string.in_progress_icon_label,
        R.drawable.ic_edit,
        InProgress
    ),
    TEAMS(
        R.string.teams_icon_label,
        R.drawable.ic_folder,
        Teams,
    ),
    SERVERS(
        R.string.servers_icon_label,
        R.drawable.ic_wifi,
        Servers
    );
}

fun fromDestination(key: NavKey?) = when (key) {
    is DoneCategory -> DONE
    is InProgressCategory -> IN_PROGRESS
    is TeamsCategory -> TEAMS
    is ServersCategory -> SERVERS
    else -> null
}