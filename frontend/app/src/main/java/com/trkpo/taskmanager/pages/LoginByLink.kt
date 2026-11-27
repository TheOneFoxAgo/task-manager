package com.trkpo.taskmanager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trkpo.taskmanager.R
import com.trkpo.taskmanager.ui.theme.TaskManagerTheme
import com.trkpo.taskmanager.viewmodels.LoginByLinkViewModel
import com.trkpo.taskmanager.viewmodels.UiState

@Composable
fun LoginByLink(
    onSuccessfulLogin: () -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: LoginByLinkViewModel,
) {
    val uiState by viewModel.uiState.collectAsState(UiState.Init)
    if (uiState == UiState.Done) {
        onSuccessfulLogin()
    }
    LoginByLinkRender(uiState, onBack) { link, password ->
        viewModel.login(link, password)
    }
}

@Composable
fun LoginByLinkRender(
    uiState: UiState,
    onBack: () -> Unit = {},
    onLogin: (String, String) -> Unit = { _, _ -> }
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painterResource(R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }

                },
                title = {
                    Text(
                        stringResource(R.string.login_by_link_appbar),
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                32.dp, Alignment.CenterVertically
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val linkState = rememberTextFieldState()
            val passwordState = rememberTextFieldState()
            TextField(
                state = linkState,
                label = { Text(stringResource(R.string.link)) }
            )
            TextField(
                state = passwordState,
                label = { Text(stringResource(R.string.password)) }
            )
            Button(
                enabled = uiState == UiState.Init,
                onClick = {
                    onLogin(
                        linkState.text.toString(),
                        passwordState.text.toString()
                    )
                }) {
                Text(stringResource(R.string.login))
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginByLink() {
    TaskManagerTheme {
        LoginByLinkRender(UiState.Init)
    }
}