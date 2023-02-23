package xyz.wingio.dimett.ui.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterialApi::class)
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