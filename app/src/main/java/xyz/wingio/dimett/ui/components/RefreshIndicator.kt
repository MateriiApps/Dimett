package xyz.wingio.dimett.ui.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.PullRefreshState

/**
 * Wrapper around [PullRefreshIndicator] that adds Material 3 theming
 *
 * @param refreshing A boolean representing whether a refresh is occurring
 * @param state The [PullRefreshState] which controls where and how the indicator will be drawn.
 */
@Composable
fun BoxScope.RefreshIndicator(
    refreshing: Boolean,
    state: PullRefreshState
) {
    PullRefreshIndicator(
        refreshing = refreshing,
        state = state,
        backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.align(Alignment.TopCenter)
    )
}