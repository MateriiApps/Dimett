package xyz.wingio.dimett.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.wingio.dimett.domain.manager.AccountManager
import xyz.wingio.dimett.domain.manager.InstanceManager
import xyz.wingio.dimett.domain.manager.PreferenceManager
import xyz.wingio.dimett.domain.repository.MastodonRepository
import xyz.wingio.dimett.rest.utils.ifSuccessful
import xyz.wingio.dimett.ui.screens.auth.LoginScreen
import xyz.wingio.dimett.ui.screens.main.MainScreen
import xyz.wingio.dimett.ui.theme.DimettTheme
import xyz.wingio.dimett.ui.viewmodels.auth.instanceUrl

class MainActivity : ComponentActivity(), KoinComponent {

    private val accountManager: AccountManager by inject()
    private val instanceManager: InstanceManager by inject()
    private val repo: MastodonRepository by inject()
    private val prefs: PreferenceManager by inject()

    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DimettTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (accountManager.isInitialized) {
                        val isSignedIn = accountManager.current != null
                        val startScreen = if (isSignedIn) MainScreen() else LoginScreen()

                        Navigator(startScreen) {
                            SlideTransition(it)
                            navigator = it
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let {
            if (it.scheme == "dimett") {
                val code = it.getQueryParameter("code")
                if (code != null) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val instance = instanceManager[instanceUrl!!]
                        if (instance != null) {
                            repo.getToken(
                                instanceUrl = instance.url,
                                clientId = instance.clientId,
                                clientSecret = instance.clientSecret,
                                code = code
                            ).ifSuccessful { token ->
                                repo.verifyCredentials(instance.url, token.accessToken)
                                    .ifSuccessful { user ->
                                        accountManager.addAccount(
                                            user,
                                            token.accessToken,
                                            instance.url
                                        )

                                        prefs.currentAccount = user.id
                                        navigator.replaceAll(MainScreen())
                                    }
                            }
                        }
                        instanceUrl = null
                    }
                }
            }
        }
    }

}