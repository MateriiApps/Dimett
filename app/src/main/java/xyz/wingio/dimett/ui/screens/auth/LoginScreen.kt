package xyz.wingio.dimett.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.delay
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.ui.viewmodels.auth.LoginViewModel
import xyz.wingio.dimett.ui.widgets.auth.InstancePreview
import xyz.wingio.dimett.utils.getString
import kotlin.time.Duration.Companion.seconds

class LoginScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Screen(
        viewModel: LoginViewModel = getScreenModel()
    ) {
        val ctx = LocalContext.current
        var lastTyped by remember {
            mutableStateOf(0L)
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

        Scaffold { pv ->
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(pv)
                    .fillMaxSize()
            ) {
                Image(painter = painterResource(R.drawable.ic_app), contentDescription = null)

                if (viewModel.nodeInfoLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 3.dp
                    )
                }

                viewModel.nodeInfo?.let {
                    InstancePreview(
                        url = viewModel.instance,
                        nodeInfo = it
                    )
                }

                OutlinedTextField(
                    value = viewModel.instance,
                    onValueChange = {
                        viewModel.instance = it
                        lastTyped = 0L
                        viewModel.nodeInfo = null
                    },
                    label = { Text(getString(R.string.label_instance)) },
                    placeholder = { Text("mastodon.online") },
                    isError = viewModel.didError
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
                    enabled = viewModel.nodeInfo != null
                ) {
                    Text(getString(R.string.action_login))
                }
            }
        }
    }

}