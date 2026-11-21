package com.trkpo.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Icon
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.trkpo.taskmanager.pages.InitPage
import com.trkpo.taskmanager.ui.theme.TaskManagerTheme

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

@Preview
@Composable
fun TaskManagerApp() {
    var destination by rememberSaveable { mutableStateOf(AppDestination.INIT) }
    var currentDestination: PrimaryAppDestinations? by rememberSaveable {
        mutableStateOf(null)
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            if (currentDestination != null) {
                PrimaryAppDestinations.entries.forEach {
                    item(
                        icon = {
                            Icon(
                                painterResource(it.icon),
                                contentDescription = stringResource(it.label)
                            )
                        },
                        label = { stringResource(it.label) },
                        selected = it == currentDestination,
                        onClick = { currentDestination = it }
                    )
                }
            }

        }
    ) {
        when (destination) {
            AppDestination.INIT -> InitPage()
            AppDestination.DONE -> TODO()
        }
    }
}


enum class PrimaryAppDestinations(
    val label: Int,
    val icon: Int,
    val default: AppDestination,
) {
    DONE(
        R.string.done_page_label,
        R.drawable.ic_star,
        AppDestination.DONE,
    )
}

enum class AppDestination {
    INIT,
    DONE
}