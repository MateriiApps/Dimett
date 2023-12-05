package xyz.wingio.dimett.ui.components

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.util.Consumer

/**
 * An effect for handling incoming [Intent]s
 *
 * @param enabled Whether or not intents are sent to the callback
 * @param onIntent Callback that gets sent all incoming [Intent]s received by the root [ComponentActivity]
 */
@Composable
fun IntentHandler(enabled: Boolean = true, onIntent: (intent: Intent) -> Unit) {
    val activity = LocalContext.current as? ComponentActivity ?: return // Make sure we are actually in a `ComponentActivity`
    val currentOnIntent by rememberUpdatedState(onIntent) // Handle new `onIntent` lambdas safely

    // Try not to recreate the callback on every composition
    val intentCallback = remember {
        Consumer<Intent> { intent -> if(enabled) currentOnIntent(intent) }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        // Add callback to the current activity
        activity.addOnNewIntentListener(intentCallback)

        // Remove callback when effect leaves the composition
        onDispose {
            activity.removeOnNewIntentListener(intentCallback)
        }
    }
}