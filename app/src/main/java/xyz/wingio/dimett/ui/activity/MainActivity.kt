package xyz.wingio.dimett.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.wingio.dimett.domain.manager.AccountManager
import xyz.wingio.dimett.ui.screens.auth.LoginScreen
import xyz.wingio.dimett.ui.screens.main.MainScreen
import xyz.wingio.dimett.ui.theme.DimettTheme

class MainActivity : ComponentActivity(), KoinComponent {

    private val accountManager: AccountManager by inject()

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
                        }
                    }
                }
            }
        }
    }

}