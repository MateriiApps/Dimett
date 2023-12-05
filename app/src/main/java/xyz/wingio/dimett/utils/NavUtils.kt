package xyz.wingio.dimett.utils

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import xyz.wingio.dimett.ui.screens.explore.ExploreTab
import xyz.wingio.dimett.ui.screens.feed.FeedTab
import xyz.wingio.dimett.ui.screens.profile.ProfileTab
import xyz.wingio.dimett.ui.screens.notifications.NotificationsTab

/**
 * Tabs that appear on the [main screen][xyz.wingio.dimett.ui.screens.main.MainScreen]
 */
enum class RootTab(val tab: Tab) {
    FEED(FeedTab()),
    EXPLORE(ExploreTab()),
    NOTIFICATIONS(NotificationsTab()),
    PROFILE(ProfileTab()),
}

/**
 * Safely navigate to the given [screen] from the root [Navigator]
 *
 * @param screen Where to navigate
 */
tailrec fun Navigator.navigate(screen: Screen) {
    return if (
        parent == null // Is the root navigator
        && items.firstOrNull { it.key == screen.key } == null // Doesn't already have the screen in the navigation stack
    ) try {
        push(screen)
    } catch (_: Throwable) {}
    else parent!!.navigate(screen)
}

/**
 * [CompositionLocal] instance for [PagerState]
 */
@OptIn(ExperimentalFoundationApi::class)
val LocalPagerState = compositionLocalOf<PagerState> { error("No PagerState provided") }

/**
 * Convenience function for providing tab options with alternating selected and unselected icons
 *
 * @param name String resource id for the name of this tab
 * @param icon Icon to be shown when the tab is not selected (Usually outlined)
 * @param iconSelected Icon to be shown when the tab is selected (Usually filled)
 */
@Composable
@SuppressLint("ComposableNaming")
@OptIn(ExperimentalFoundationApi::class)
fun Tab.TabOptions(
    @StringRes name: Int,
    icon: ImageVector,
    iconSelected: ImageVector
): TabOptions {
    val pagerState = LocalPagerState.current
    val selected = RootTab.entries[pagerState.currentPage].tab == this

    return TabOptions(
        0u,
        title = stringResource(name),
        icon = rememberVectorPainter(
            if (selected) iconSelected else icon
        )
    )
}