package com.trkpo.taskmanager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trkpo.taskmanager.R
import com.trkpo.taskmanager.ui.theme.TaskManagerTheme

@Composable
fun InitPage(
    onAdminLoginClick: () -> Unit = {},
    onLoginByLinkClick: () -> Unit = {},
) {
    Scaffold(Modifier.fillMaxSize()) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                32.dp, Alignment.CenterVertically
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = stringResource(R.string.greetings),
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = stringResource(R.string.choose_login),
                style = MaterialTheme.typography.headlineLarge,
            )
            FilledTonalButton(
                onClick = onAdminLoginClick
            ) {
                Icon(
                    painterResource(R.drawable.ic_stars),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text(stringResource(R.string.i_am_admin))
            }
            Button(
                onClick = onLoginByLinkClick
            ) {
                Icon(
                    painterResource(R.drawable.ic_account_circle),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text(stringResource(R.string.login_by_link))
            }
        }
    }
}

@Preview
@Composable
fun PreviewInitPage() {
    TaskManagerTheme {
        InitPage()
    }
}