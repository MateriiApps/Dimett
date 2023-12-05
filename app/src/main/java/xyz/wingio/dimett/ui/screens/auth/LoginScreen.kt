package xyz.wingio.dimett.ui.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.ui.viewmodels.auth.LoginViewModel
import xyz.wingio.dimett.ui.widgets.auth.InstancePreview
import xyz.wingio.dimett.ui.components.IntentHandler
import xyz.wingio.dimett.utils.getString
import kotlin.time.Duration.Companion.seconds

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = getScreenModel()
        val navigator = LocalNavigator.currentOrThrow

        IntentHandler { intent ->
            viewModel.handleIntent(intent, navigator)
        }

        Scaffold { pv ->
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(pv)
                    .fillMaxSize()
            ) {
                Image(painter = painterResource(R.drawable.ic_app), contentDescription = null)

                if(viewModel.loginLoading) {
                    CircularProgressIndicator()
                } else {
                    Login(viewModel)
                }
            }
        }
    }

    /**
     * The main body of this screen
     */
    @Composable
    private fun ColumnScope.Login(
        viewModel: LoginViewModel
    ) {
        val ctx = LocalContext.current
        var lastTyped by remember {
            mutableLongStateOf(0L)
        }

        // Only send request when user is done typing
        LaunchedEffect(Unit) {
            while (true) {
                lastTyped += 1
                delay(1.seconds / 30)
                if (lastTyped == 23L) {
                    viewModel.loadDetails()
                }
            }
        }

        if (viewModel.nodeInfoLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 3.dp
            )
        }

        AnimatedVisibility(
            visible = viewModel.nodeInfo != null
        ) {
            viewModel.nodeInfo?.let {
                InstancePreview(
                    url = viewModel.instance,
                    nodeInfo = it
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = viewModel.instance,
            onValueChange = {
                viewModel.instance = it
                lastTyped = 0L
                viewModel.nodeInfo = null
            },
            label = { Text(getString(R.string.label_instance)) },
            placeholder = { Text("mastodon.online") }, // TODO: Maybe replace with random instance from some list?
            isError = viewModel.didError,
            singleLine = true
        )

        if (viewModel.didError) {
            Text(
                text = getString(R.string.msg_invalid_instance),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 300.dp)
            )
        }

        Button(
            onClick = { viewModel.login(ctx) },
            enabled = viewModel.nodeInfo != null && viewModel.instanceIsMastodon // For now we only support the Mastodon api
        ) {
            Text(getString(R.string.action_login))
        }
    }

}